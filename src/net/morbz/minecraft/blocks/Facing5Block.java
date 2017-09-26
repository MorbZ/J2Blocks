package net.morbz.minecraft.blocks;

import net.morbz.minecraft.blocks.states.Facing5State;

/**
 * Base class for any block that can face east, west, south, north, or up.
 */
public abstract class Facing5Block implements IBlock {

	private Facing5State facing;

	/**
	 * Creates a new instance.
	 * 
	 * @param facing The direction in which the torch is facing
	 */
	public Facing5Block(Facing5State facing) {
		this.facing = facing;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public byte getBlockData() {
		// Facing direction
		byte data = 0;
		switch(facing) {
		case EAST:
			data = 1;
			break;
		case WEST:
			data = 2;
			break;
		case SOUTH:
			data = 3;
			break;
		case NORTH:
			data = 4;
			break;
		case UP:
			data = 5;
			break;
		}
		return data;
	}
	
	/**
	 * Returns the material from which this block is made
	 * @return the Material
	 */
	protected abstract Material getMaterial();
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public byte getBlockId() {
		return (byte)getMaterial().getValue();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getTransparency() {
		return getMaterial().getTransparency();
	}
}
