package net.morbz.minecraft.blocks;

/** 
 * The stone brick block with different structures.
 * 
 * @author MorbZ
 */
@SuppressWarnings("javadoc")
public enum StoneBrickBlock implements IBlock {
	NORMAL(0),
	MOSSY(1),
	CRACKED(2),
	CHISELED(3);
	
	private int value;
	private StoneBrickBlock(int value) {
		this.value = value;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public byte getBlockId() {
		return (byte)Material.STONEBRICK.getValue();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public byte getBlockData() {
		return (byte)value;
	}
}
