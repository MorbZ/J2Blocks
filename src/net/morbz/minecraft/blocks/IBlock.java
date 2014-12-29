package net.morbz.minecraft.blocks;

/**
 * Interface for all blocks.
 * 
 * @author MorbZ
 */
public interface IBlock {
	/**
	 * Returns the block ID. That is the basic ID of the material without additional data.
	 * 
	 * @return The block ID
	 */
	public byte getBlockId();
	
	/**
	 * Returns the block data. It can hold additional information about the block depending on the 
	 * material.
	 * 
	 * @return The block data. Only the 4 rightmost bits are relevant.
	 */
	public byte getBlockData();
}
