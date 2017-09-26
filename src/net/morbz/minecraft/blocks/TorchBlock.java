package net.morbz.minecraft.blocks;

import net.morbz.minecraft.blocks.states.Facing5State;

public class TorchBlock extends Facing5Block {

	/**
	 * Creates a new instance.
	 * 
	 * @param facing The direction in which the torch is facing
	 */
	public TorchBlock(Facing5State facing) {
		super(facing);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Material getMaterial() {
		return Material.TORCH;
	}
}
