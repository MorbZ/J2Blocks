package net.morbz.minecraft.world;

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
	
	private byte[][][] blocks = 
		new byte[Chunk.BLOCKS_PER_CHUNK_SIDE][Chunk.BLOCKS_PER_CHUNK_SIDE][SECTION_HEIGHT];
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
	 * @param value The value of the block
	 */
	public void setBlock(int x, int y, int z, byte value) {
		// Count non-air blocks
		if(blocks[x][y][z] == 0 && value != 0) {
			blockCount++;
		} else if(blocks[x][y][z] != 0 && value == 0) {
			blockCount--;
		}
		
		// Set block
		blocks[x][y][z] = value;
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
		// Make blocks byte array
		byte[] bytes = new byte[Chunk.BLOCKS_PER_CHUNK_SIDE * Chunk.BLOCKS_PER_CHUNK_SIDE * SECTION_HEIGHT];
		int i = 0;
		for(int y = 0; y < SECTION_HEIGHT; y++) {
			for(int z = 0; z < Chunk.BLOCKS_PER_CHUNK_SIDE; z++) {
				for(int x = 0; x < Chunk.BLOCKS_PER_CHUNK_SIDE; x++) {
					bytes[i] = blocks[x][y][z];
					i++;
				}
			}
		}
		
		// Create tag
		CompoundTagFactory factory = new CompoundTagFactory("");
		factory.set(new ByteArrayTag("Blocks", bytes));
		factory.set(new ByteArrayTag("Data", new byte[2048]));
		factory.set(new ByteArrayTag("BlockLight", new byte[2048]));
		factory.set(new ByteArrayTag("SkyLight", new byte[2048]));
		factory.set(new ByteTag("Y", (byte)y));
		return factory.getTag();
	}
}
