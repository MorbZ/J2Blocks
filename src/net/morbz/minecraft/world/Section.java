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
public class Section implements ITagProvider {
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
	private NibbleArray blockData = new NibbleArray(BLOCKS_PER_SECTION);
	private int blockCount = 0;
	private int y;
	
	/**
	 * Creates a new instance.
	 * 
	 * @param y The Y-position within the chunk
	 */
	public Section(int y) {
		this.y = y;
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
		} else {
			blockIds[index] = 0;
			blockData.set(index, (byte)0);
		}
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
	 * {@inheritDoc}
	 */
	@Override
	public Tag getTag() {
		// Make all blocks have the same sky light
		NibbleArray skyLight = new NibbleArray(BLOCKS_PER_SECTION);
		for(int i = 0; i < skyLight.size(); i++) {
			skyLight.set(i, (byte)0xF);
		}
		
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
