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

import net.morbz.minecraft.blocks.CustomBlock;
import net.morbz.minecraft.blocks.IBlock;
import net.morbz.minecraft.blocks.Material;
import net.morbz.minecraft.tags.CompoundTagFactory;
import net.morbz.minecraft.tags.ITagProvider;
import net.morbz.minecraft.tags.ListTagFactory;

import org.jnbt.ByteTag;
import org.jnbt.CompoundTag;
import org.jnbt.IntArrayTag;
import org.jnbt.IntTag;
import org.jnbt.LongTag;
import org.jnbt.Tag;

/**
 * Defines a chunk. It consists of 16x16 blocks in XZ-dimension and up to 16 sections for the 
 * height.
 * 
 * @author MorbZ
 */
public class Chunk implements ITagProvider {
	/**
	 * Sections per chunk
	 */
	public static final int SECTIONS_PER_CHUNK = 16;
	
	/**
	 * Blocks per chunk side
	 */
	public static final int BLOCKS_PER_CHUNK_SIDE = 16;
	
	private Section[] sections = new Section[SECTIONS_PER_CHUNK];
	private int[][] heightMap = new int[BLOCKS_PER_CHUNK_SIDE][BLOCKS_PER_CHUNK_SIDE];
	private int xPos, zPos;
	
	/**
	 * Creates a new instance.
	 * 
	 * @param xPos The X-coordinate within the chunk
	 * @param zPos The Z-coordinate within the chunk
	 * @param layers The default layers. Can be 'null'
	 */
	public Chunk(int xPos, int zPos, DefaultLayers layers) {
		this.xPos = xPos;
		this.zPos = zPos;
		
		// Set default blocks
		if(layers != null) {
			// Iterate layers
			for(int y = 0; y < World.MAX_HEIGHT; y++) {
				Material material = layers.getLayer(y);
				if(material != null) {
					// Create block
					CustomBlock block = new CustomBlock(material.getValue(), 0);
					
					// Iterate area
					for(int x = 0; x < BLOCKS_PER_CHUNK_SIDE; x++) {
						for(int z = 0; z < BLOCKS_PER_CHUNK_SIDE; z++) {
							// Set block
							setBlock(x, y, z, block);
						}
					}
				}
			}
		}
	}
	
	/**
	 * Sets a block at the given position.
	 * 
	 * @param x The X-coordinate within the chunk
	 * @param y The Y-coordinate
	 * @param z The Z-coordinate within the chunk
	 * @param block The block
	 */
	public void setBlock(int x, int y, int z, IBlock block) {
		// Create section
		int sectionY = y / Section.SECTION_HEIGHT;
		Section section = sections[sectionY];
		if(section == null) {
			section = new Section(sectionY);
			sections[sectionY] = section;
		}
		
		// Update height map
		// TODO: Update map entry when block is air
		if(y > heightMap[x][z] && block.getBlockId() != 0) {
			heightMap[x][z] = y;
		}
		
		// Set block
		int blockY = y % Section.SECTION_HEIGHT;
		section.setBlock(x, blockY, z, block);
	}
	
	/**
	 * Has at least 1 block that is not air.
	 * 
	 * @return True if there is a block
	 */
	public boolean hasBlocks() {
		// Iterate sections
		for(Section section : sections) {
			if(section != null && section.getBlockCount() > 0) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Tag getTag() {
		// Get section tags
		ListTagFactory factory = new ListTagFactory("Sections", CompoundTag.class);
		for(Section section : sections) {
			if(section != null && section.getBlockCount() > 0) {
				factory.add(section.getTag());
			}
		}
		
		// Make level tags
		CompoundTagFactory factory2 = new CompoundTagFactory("Level");
		factory2.set(factory.getTag());
		factory2.set(new IntTag("xPos", xPos));
		factory2.set(new IntTag("zPos", zPos));
		factory2.set(new LongTag("LastUpdate", System.currentTimeMillis()));
		factory2.set(new ByteTag("V", (byte)1));
		factory2.set(new ByteTag("LightPopulated", (byte)1));
		factory2.set(new ByteTag("TerrainPopulated", (byte)1));
		
		// Make height map
		int[] heightMapAry = new int[BLOCKS_PER_CHUNK_SIDE * BLOCKS_PER_CHUNK_SIDE];
		int i = 0;
		for(int z = 0; z < BLOCKS_PER_CHUNK_SIDE; z++) {
			for(int x = 0; x < BLOCKS_PER_CHUNK_SIDE; x++) {
				heightMapAry[i] = heightMap[x][z];
				i++;
			}
		}
		factory2.set(new IntArrayTag("HeightMap", heightMapAry));
		
		// Make chunk tag
		CompoundTagFactory factory3 = new CompoundTagFactory("");
		factory3.set(factory2.getTag());
		return factory3.getTag();
	}
}
