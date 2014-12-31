package net.morbz.minecraft.blocks;

/**
 * The class for the stone, granite, etc.
 * 
 * @author MorbZ
 */
@SuppressWarnings("javadoc")
public enum StoneBlock implements IBlock {
	STONE(0),
	GRANITE(1),
	SMOOTH_GRANITE(2),
	DIORITE(3),
	SMOOTH_DIORITE(4),
	ANDESITE(5),
	SMOOTH_ANDESITE(6);
	
	private int value;
	private StoneBlock(int value) {
		this.value = value;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public byte getBlockId() {
		return (byte)Material.STONE.getValue();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public byte getBlockData() {
		return (byte)value;
	}
}
