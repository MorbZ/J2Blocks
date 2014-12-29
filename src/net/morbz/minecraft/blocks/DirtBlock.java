package net.morbz.minecraft.blocks;

/**
 * The class for the dirt and podzol blocks.
 * 
 * @author MorbZ
 */
@SuppressWarnings("javadoc")
public enum DirtBlock implements IBlock {
	DIRT(0),
	COARSE_DIRT(1),
	PODZOL(2);
	
	private int value;
	private DirtBlock(int value) {
		this.value = value;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public byte getBlockId() {
		return 3;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public byte getBlockData() {
		return (byte)value;
	}
}
