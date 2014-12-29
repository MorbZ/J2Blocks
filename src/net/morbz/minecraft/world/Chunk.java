package net.morbz.minecraft.world;

import net.morbz.minecraft.blocks.IBlock;
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
	 */
	public Chunk(int xPos, int zPos) {
		this.xPos = xPos;
		this.zPos = zPos;
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
		factory2.set(new ByteTag("LightPopulated", (byte)0));
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
