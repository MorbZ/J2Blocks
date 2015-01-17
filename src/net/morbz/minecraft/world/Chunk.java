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
public class Chunk implements ITagProvider, IBlockContainer {
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
	private IBlockContainer parent;
	
	/**
	 * Creates a new instance.
	 * 
	 * @param parent The parent block container
	 * @param xPos The X-coordinate within the region
	 * @param zPos The Z-coordinate within the region
	 * @param layers The default layers. Can be 'null'
	 */
	public Chunk(IBlockContainer parent, int xPos, int zPos, DefaultLayers layers) {
		this.parent = parent;
		this.xPos = xPos;
		this.zPos = zPos;
		
		// Set default blocks
		if(layers != null) {
			// Iterate layers
			for(int y = 0; y < World.MAX_HEIGHT; y++) {
				Material material = layers.getLayer(y);
				if(material != null) {
					// Create block
					CustomBlock block = new CustomBlock(material.getValue(), 0, material.getTransparency());
					
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
	 * @return The X-coordinate within the region
	 */
	public int getX() {
		return xPos;
	}
	
	/**
	 * @return The Z-coordinate within the region
	 */
	public int getZ() {
		return zPos;
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
		// Get section
		Section section = getSection(y, true);
		
		// Set block
		int blockY = y % Section.SECTION_HEIGHT;
		section.setBlock(x, blockY, z, block);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public byte getSkyLight(int x, int y, int z) {
		// Get section
		Section section = getSection(y, false);
		
		if(section != null) {
			int blockY = y % Section.SECTION_HEIGHT;
			byte light = section.getSkyLight(x, blockY, z);
			return light;
		}
		return World.DEFAULT_SKY_LIGHT;
	}
	
	private void setSkyLight(int x, int y, int z, byte light) {
		// Get section
		Section section = getSection(y, false);
		
		if(section != null) {
			int blockY = y % Section.SECTION_HEIGHT;
			section.setSkyLight(x, blockY, z, light);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public byte getSkyLightFromParent(IBlockContainer child, int childX, int childY, int childZ) {
		// Get total Y
		int y = ((Section)child).getY() * Section.SECTION_HEIGHT + childY;
		if(y >= World.MAX_HEIGHT) {
			return World.DEFAULT_SKY_LIGHT;
		}
		if(y < 0) {
			return 0;
		}
		
		// Which chunk?
		if(childX >= 0 && childX < BLOCKS_PER_CHUNK_SIDE && childZ >= 0 && childZ < BLOCKS_PER_CHUNK_SIDE) {
			// Same chunk
			return getSkyLight(childX, y, childZ);
		} else {
			// Different chunk
			return parent.getSkyLightFromParent(this, childX, y, childZ);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void spreadSkyLight(byte light) {
		for(Section section : sections) {
			if(section != null) {
				section.spreadSkyLight(light);
			}
		}
	}
	
	/**
	 * Adds the sky light. Starts from top the top of each column and sets sky light to full, up to 
	 * the first non-transparent block.
	 */
	public void addSkyLight() {
		for(int x = 0; x < BLOCKS_PER_CHUNK_SIDE; x++) {
			for(int z = 0; z < BLOCKS_PER_CHUNK_SIDE; z++) {
				int highestBlock = getHighestBlock(x, z);
				for(int y = World.MAX_HEIGHT - 1; y >= highestBlock; y--) {
					setSkyLight(x, y, z, World.DEFAULT_SKY_LIGHT);
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
		return heightMap[x][z];
	}
	
	private Section getSection(int y, boolean create) {
		// Get section
		int sectionY = y / Section.SECTION_HEIGHT;
		Section section = sections[sectionY];
		
		// Create section
		if(section == null && create) {
			section = new Section(this, sectionY);
			sections[sectionY] = section;
		}
		return section;
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
	 * Calculates the height map.
	 */
	public void calculateHeightMap() {
		// Iterate sections from top to bottom
		for(int y = SECTIONS_PER_CHUNK - 1; y >= 0; y--) {
			Section section = sections[y];
			if(section != null) {
				// Iterate X/Z-columns
				for(int x = 0; x < BLOCKS_PER_CHUNK_SIDE; x++) {
					for(int z = 0; z < BLOCKS_PER_CHUNK_SIDE; z++) {
						// Update height
						if(heightMap[x][z] == 0) {
							int height = section.getHighestBlock(x, z);
							if(height != -1) {
								heightMap[x][z] = y * Section.SECTION_HEIGHT + height + 1;
							}
						}
					}
				}
			}
		}
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
