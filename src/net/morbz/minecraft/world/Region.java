package net.morbz.minecraft.world;

/*
* The MIT License (MIT)
* 
* Copyright (c) 2014-2015 Merten Peetz
* 
* Permission is hereby granted, free of charge, to any person obtaining a copy
* of this software and associated documentation files (the "Software"), to deal
* in the Software without restriction, including without limitation the rights
* to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
* copies of the Software, and to permit persons to whom the Software is
* furnished to do so, subject to the following conditions:
* 
* The above copyright notice and this permission notice shall be included in all
* copies or substantial portions of the Software.
* 
* THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
* IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
* FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
* AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
* LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
* OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
* SOFTWARE.
*/

import java.io.File;
import java.io.IOException;

import net.morbz.minecraft.blocks.IBlock;
import net.unknown.RegionFile;

import org.jnbt.NBTOutputStream;

/** 
 * Defines a region. It consists of up to 32x32 chunks in XZ-dimension.
 * 
 * @author MorbZ
 */
public class Region implements IBlockContainer {
	/**
	 * Chunks per region side
	 */
	public static final int CHUNKS_PER_REGION_SIDE = 32;
	
	/**
	 * Blocks per region side
	 */
	public static final int BLOCKS_PER_REGION_SIDE = CHUNKS_PER_REGION_SIDE * Chunk.BLOCKS_PER_CHUNK_SIDE;
	
	private Chunk[][] chunks = new Chunk[CHUNKS_PER_REGION_SIDE][CHUNKS_PER_REGION_SIDE];
	private DefaultLayers layers;
	
	private int xPos, zPos;
	private IBlockContainer parent;
	
	/**
	 * Creates a new instance.
	 * 
	 * @param parent The parent block container
	 * @param layers The default layers. Can be 'null'
	 * @param xPos The X-coordinate within the world
	 * @param zPos The Z-coordinate within the world
	 */
	public Region(IBlockContainer parent, int xPos, int zPos, DefaultLayers layers) {
		this.parent = parent;
		this.xPos = xPos;
		this.zPos = zPos;
		this.layers = layers;
	}
	
	/**
	 * @return The X-coordinate within the world
	 */
	public int getX() {
		return xPos;
	}
	
	/**
	 * @return The Z-coordinate within the world
	 */
	public int getZ() {
		return zPos;
	}
	
	/**
	 * Sets a block at the given position.
	 * 
	 * @param x The X-coordinate within the region
	 * @param y The Y-coordinate
	 * @param z The Z-coordinate within the region
	 * @param block The block
	 */
	public void setBlock(int x, int y, int z, IBlock block) {
		// Get chunk 
		Chunk chunk = getChunk(x, z, true);
		
		// Set block
		int blockX = x % Chunk.BLOCKS_PER_CHUNK_SIDE;
		int blockZ = z % Chunk.BLOCKS_PER_CHUNK_SIDE;
		chunk.setBlock(blockX, y, blockZ, block);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public byte getSkyLight(int x, int y, int z) {
		// Get chunk 
		Chunk chunk = getChunk(x, z, false);
		
		if(chunk != null) {
			int blockX = x % Chunk.BLOCKS_PER_CHUNK_SIDE;
			int blockZ = z % Chunk.BLOCKS_PER_CHUNK_SIDE;
			byte light = chunk.getSkyLight(blockX, y, blockZ);
			return light;
		}
		return World.DEFAULT_SKY_LIGHT;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public byte getSkyLightFromParent(IBlockContainer child, int childX, int childY, int childZ) {
		int x = ((Chunk)child).getX() * Chunk.BLOCKS_PER_CHUNK_SIDE + childX;
		int z = ((Chunk)child).getZ() * Chunk.BLOCKS_PER_CHUNK_SIDE + childZ;
		
		// Same region?
		if(x >= 0 && x < BLOCKS_PER_REGION_SIDE && z >= 0 && z < BLOCKS_PER_REGION_SIDE) {
			return getSkyLight(x, childY, z);
		} else {
			// Pass to parent
			return parent.getSkyLightFromParent(this, x, childY, z);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void spreadSkyLight(byte light) {
		for(int x = 0; x < CHUNKS_PER_REGION_SIDE; x++) {
			for(int z = 0; z < CHUNKS_PER_REGION_SIDE; z++) {
				Chunk chunk = chunks[x][z];
				if(chunk != null) {
					chunk.spreadSkyLight(light);
				}
			}
		}
	}
	
	/**
	 * Adds the sky light. Starts from top the top of each column and sets sky light to full, up to 
	 * the first non-transparent block.
	 */
	public void addSkyLight() {
		for(int x = 0; x < CHUNKS_PER_REGION_SIDE; x++) {
			for(int z = 0; z < CHUNKS_PER_REGION_SIDE; z++) {
				Chunk chunk = chunks[x][z];
				if(chunk != null) {
					chunk.addSkyLight();
				}
			}
		}
	}
	
	/**
	 * Returns the highest non transparent block. calculateHeightMap() has to be invoked before
	 * calling this method to get actual results.
	 * 
	 * @param x The X-coordinate
	 * @param z The Z-coordinate
	 * @return The Y-coordinate of the highest block
	 */
	public int getHighestBlock(int x, int z) {
		// Get chunk 
		Chunk chunk = getChunk(x, z, false);
		if(chunk != null) {
			int blockX = x % Chunk.BLOCKS_PER_CHUNK_SIDE;
			int blockZ = z % Chunk.BLOCKS_PER_CHUNK_SIDE;
			return chunk.getHighestBlock(blockX, blockZ);
		}
		return 0;
	}
	
	private Chunk getChunk(int x, int z, boolean create) {
		// Make chunk coords
		int chunkX = x / Chunk.BLOCKS_PER_CHUNK_SIDE;
		int chunkZ = z / Chunk.BLOCKS_PER_CHUNK_SIDE;
		Chunk chunk = chunks[chunkX][chunkZ];
		
		// Create chunk
		if(chunk == null && create) {
			chunk = new Chunk(this, chunkX, chunkZ, layers);
			chunks[chunkX][chunkZ] = chunk;
		}
		return chunk;
	}
	
	/**
	 * Calculates the height maps for all chunks.
	 */
	public void calculateHeightMap() {
		for(int x = 0; x < CHUNKS_PER_REGION_SIDE; x++) {
			for(int z = 0; z < CHUNKS_PER_REGION_SIDE; z++) {
				Chunk chunk = chunks[x][z];
				if(chunk != null) {
					chunk.calculateHeightMap();
				}
			}
		}
	}
	
	/**
	 * Writes this region to a file.
	 * 
	 * @param path The path to write the file
	 * @throws IOException 
	 */
	public void writeToFile(File path) throws IOException {
		// Write region file
		RegionFile regionFile = new RegionFile(path);
		try {
			 for(int x = 0; x < CHUNKS_PER_REGION_SIDE; x++) {
				for(int z = 0; z < CHUNKS_PER_REGION_SIDE; z++) {
					Chunk chunk = chunks[x][z];
					if(chunk != null && chunk.hasBlocks()) {
						NBTOutputStream out = new NBTOutputStream(regionFile.getChunkDataOutputStream(x, z), false);
						try {
							out.writeTag(chunks[x][z].getTag());
						} finally {
							out.close();
						}
					}
				}
			}
		} finally {
			regionFile.close();
		}
	}
}
