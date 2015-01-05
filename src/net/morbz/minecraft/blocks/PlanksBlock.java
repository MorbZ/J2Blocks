package net.morbz.minecraft.blocks;

import net.morbz.minecraft.blocks.states.WoodState;

/**
 * The class for wooden planks.
 * 
 * @author MorbZ
 */
public class PlanksBlock implements IBlock {
	private WoodState wood;
	
	/**
	 * Creates a new instance.
	 * 
	 * @param wood The type of wood
	 */
	public PlanksBlock(WoodState wood) {
		this.wood = wood;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public byte getBlockId() {
		return (byte)Material.PLANKS.getValue();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public byte getBlockData() {
		return (byte)wood.getValue();
	}
}
