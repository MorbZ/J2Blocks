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

import net.morbz.minecraft.blocks.IBlock;
import net.morbz.minecraft.tags.CompoundTagFactory;
import net.morbz.minecraft.tags.ITagProvider;

import org.jnbt.ByteArrayTag;
import org.jnbt.ByteTag;
import org.jnbt.Tag;

/**
 * Defines a section. It consist of 16 blocks in each dimension.
 * 
 * @author MorbZ
 */
public class Section implements ITagProvider, IBlockContainer {
	/**
	 * The height in blocks of a section
	 */
	public static final int SECTION_HEIGHT = 16;
	
	/**
	 * The total number of blocks in a section
	 */
	public static final int BLOCKS_PER_SECTION = 
		Chunk.BLOCKS_PER_CHUNK_SIDE * Chunk.BLOCKS_PER_CHUNK_SIDE * SECTION_HEIGHT;
	
	private byte[] blockIds = new byte[BLOCKS_PER_SECTION];
	private byte[] transparency = new byte[BLOCKS_PER_SECTION];
	private NibbleArray blockData = new NibbleArray(BLOCKS_PER_SECTION);
	private NibbleArray skyLight = new NibbleArray(BLOCKS_PER_SECTION);
	private int blockCount = 0;
	private int y;
	private IBlockContainer parent;
	
	/**
	 * Creates a new instance.
	 * 
	 * @param parent The parent block container
	 * @param y The Y-position within the chunk
	 */
	public Section(IBlockContainer parent, int y) {
		this.parent = parent;
		this.y = y;
	
		// Set default transparency
		for(int i = 0; i < transparency.length; i++) {
			transparency[i] = World.DEFAULT_TRANSPARENCY;
		}
	}
	
	/**
	 * @return The Y-position within the chunk
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * Sets a block at the given position.
	 * 
	 * @param x The X-coordinate within the section
	 * @param y The Y-coordinate within the section
	 * @param z The Z-coordinate within the section
	 * @param block The block
	 */
	public void setBlock(int x, int y, int z, IBlock block) {
		// We ignore it if it's air
		if(block.getBlockId() == 0) {
			block = null;
		}
		
		// Count non-air blocks
		int index = getBlockIndex(x, y, z);
		if(blockIds[index] == 0 && block != null) {
			blockCount++;
		} else if(blockIds[index] != 0 && block == null) {
			blockCount--;
		}
		
		// Set block
		if(block != null) {
			blockIds[index] = block.getBlockId();
			blockData.set(index, block.getBlockData());
			transparency[index] = (byte)block.getTransparency();
		} else {
			blockIds[index] = 0;
			blockData.set(index, (byte)0);
			transparency[index] = World.DEFAULT_TRANSPARENCY;
		}
	}
	
	private byte getTransparency(int x, int y, int z) {
		int index = getBlockIndex(x, y, z);
		return transparency[index];
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public byte getSkyLight(int x, int y, int z) {
		int index = getBlockIndex(x, y, z);
		byte light = skyLight.get(index);
		return light;
	}
	
	/**
	 * Sets the sky light level of the block at given position.
	 * 
	 * @param x The X-coordinate
	 * @param y The Y-coordinate
	 * @param z The Z-coordinate
	 * @param light The sky light level
	 */
	public void setSkyLight(int x, int y, int z, byte light) {
		int index = getBlockIndex(x, y, z);
		skyLight.set(index, light);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public byte getSkyLightFromParent(IBlockContainer child, int x, int y, int z) {
		if(isInBounds(x, y, z)) {
			// Block is within section bounds
			return getSkyLight(x, y, z);
		} else {
			// Pass to parent
			return parent.getSkyLightFromParent(this, x, y, z);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void spreadSkyLight(byte light) {
		// Iterate blocks with 1 offset to all sides to get light from adjacent sections
		for(int x = -1; x <= Chunk.BLOCKS_PER_CHUNK_SIDE; x++) {
			for(int y = -1; y <= SECTION_HEIGHT; y++) {
				for(int z = -1; z <= Chunk.BLOCKS_PER_CHUNK_SIDE; z++) {
					byte currentLight = getSkyLightFromParent(null, x, y, z);
					if(currentLight == light) {
						spreadSkyLightForBlock(x, y, z, light);
					}
				}
			}
		}
	}
	
	/**
	 * Spreads the light from the given block. All adjacent blocks that have a lower light level
	 * than the given will be updated.
	 */
	private void spreadSkyLightForBlock(int x, int y, int z, byte light) {
		increaseSkyLight(x+1, y, z, light);
		increaseSkyLight(x-1, y, z, light);
		increaseSkyLight(x, y+1, z, light);
		increaseSkyLight(x, y-1, z, light);
		increaseSkyLight(x, y, z+1, light);
		increaseSkyLight(x, y, z-1, light);
	}
	
	/**
	 * Updates the sky light if the current light level is lower than the given.
	 */
	private void increaseSkyLight(int x, int y, int z, byte light) {
		// Check if block is within bounds
		if(!isInBounds(x, y, z)) {
			return;
		}
		
		// Calculate new light level
		byte transparency = getTransparency(x, y, z);
		if(transparency == 0) {
			return;
		} else if(transparency > 1) {
			light -= transparency;
		}
		light--;
		if(light < 1) {
			return;
		}
		
		// Update is current light is lower
		if(getSkyLight(x, y, z) < light) {
			setSkyLight(x, y, z, light);
		}
	}
	
	private boolean isInBounds(int x, int y, int z) {
		if(x < 0 || x > Chunk.BLOCKS_PER_CHUNK_SIDE - 1) {
			return false;
		}
		if(y < 0 || y > SECTION_HEIGHT - 1) {
			return false;
		}
		if(z < 0 || z > Chunk.BLOCKS_PER_CHUNK_SIDE - 1) {
			return false;
		}
		return true;
	}
	
	private int getBlockIndex(int x, int y, int z) {
		int index = 0;
		index += y * Chunk.BLOCKS_PER_CHUNK_SIDE * Chunk.BLOCKS_PER_CHUNK_SIDE;
		index += z * Chunk.BLOCKS_PER_CHUNK_SIDE;
		index += x;
		return index;
	}
	
	/**
	 * Returns the number of blocks that are not air.
	 * 
	 * @return Number of blocks
	 */
	public int getBlockCount() {
		return blockCount;
	}
	
	/**
	 * Returns the Y-coordinate of the highest block that is not air or -1 is there are no blocks in
	 * this column.
	 * 
	 * @param x The X-coordinate
	 * @param z The Z-coordinate
	 * @return The Y-coordinate of the highest block or -1
	 */
	public int getHighestBlock(int x, int z) {
		// Iterate column
		for(int y = SECTION_HEIGHT - 1; y >= 0; y--) {
			int index = getBlockIndex(x, y, z);
			if(blockIds[index] != 0 && transparency[index] != 1) {
				return y;
			}
		}
		return -1;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Tag getTag() {
		// Create tag
		CompoundTagFactory factory = new CompoundTagFactory("");
		factory.set(new ByteArrayTag("Blocks", blockIds));
		factory.set(new ByteArrayTag("Data", blockData.getBytes()));
		factory.set(new ByteArrayTag("BlockLight", new NibbleArray(BLOCKS_PER_SECTION).getBytes()));
		factory.set(new ByteArrayTag("SkyLight", skyLight.getBytes()));
		factory.set(new ByteTag("Y", (byte)y));
		return factory.getTag();
	}
}
