package net.morbz.minecraft.blocks;

import net.morbz.minecraft.blocks.states.Facing5State;

/**
 * A redstone torch for both states active and inactive.
 * 
 * @author MorbZ
 */
public class RedstoneTorchBlock implements IBlock {
	private boolean isActive;
	private Facing5State facing;
	
	/**
	 * Creates a new instance.
	 * 
	 * @param isActive Whether the torch active
	 * @param facing The direction in which the torch is facing
	 */
	public RedstoneTorchBlock(boolean isActive, Facing5State facing) {
		this.isActive = isActive;
		this.facing = facing;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public byte getBlockId() {
		// Is active?
		Material material = isActive ? Material.REDSTONE_TORCH : Material.UNLIT_REDSTONE_TORCH;
		return (byte)material.getValue();
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
}
