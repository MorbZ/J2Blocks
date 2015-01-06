package net.morbz.minecraft.world;

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
public class Region {
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
	
	/**
	 * Creates a new instance.
	 * 
	 * @param layers The default layers. Can be 'null'
	 */
	public Region(DefaultLayers layers) {
		this.layers = layers;
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
		// Get chunk coords
		int chunkX = x / Chunk.BLOCKS_PER_CHUNK_SIDE;
		int chunkZ = z / Chunk.BLOCKS_PER_CHUNK_SIDE;
		
		// Create chunk
		Chunk chunk = chunks[chunkX][chunkZ];
		if(chunk == null) {
			chunk = new Chunk(chunkX, chunkZ, layers);
			chunks[chunkX][chunkZ] = chunk;
		}
		
		// Set block
		int blockX = x % Chunk.BLOCKS_PER_CHUNK_SIDE;
		int blockZ = z % Chunk.BLOCKS_PER_CHUNK_SIDE;
		chunk.setBlock(blockX, y, blockZ, block);
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
