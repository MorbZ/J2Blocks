package net.morbz.minecraft.blocks;

/**
 * A custom block for testing purposes and internal functions. The block ID and block data can be 
 * set freely.
 * 
 * @author MorbZ
 */
public class CustomBlock implements IBlock {
	private int blockId, blockData;
	
	/**
	 * Creates a new instance.
	 * 
	 * @param blockId The block ID
	 * @param blockData The block data
	 */
	public CustomBlock(int blockId, int blockData) {
		this.blockId = blockId;
		this.blockData = blockData;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public byte getBlockId() {
		return (byte)blockId;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public byte getBlockData() {
		return (byte)blockData;
	}
}
