package net.morbz.minecraft.blocks;

/**
 * The class for sand and red sand.
 * 
 * @author MorbZ
 */
@SuppressWarnings("javadoc")
public enum SandBlock implements IBlock {
	SAND(0),
	RED_SAND(1);
	
	private int value;
	private SandBlock(int value) {
		this.value = value;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public byte getBlockId() {
		return (byte)Material.SAND.getValue();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public byte getBlockData() {
		return (byte)value;
	}
}
