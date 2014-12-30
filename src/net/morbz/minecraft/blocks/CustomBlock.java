package net.morbz.minecraft.blocks;

/**
 * A custom block for testing purposes. The block ID and block data can be set freely.
 * 
 * @author MorbZ
 */
public class CustomBlock implements IBlock {
	private byte blockId, blockData;
	
	/**
	 * Creates a new instance.
	 * 
	 * @param blockId The block ID
	 * @param blockData The block data
	 */
	public CustomBlock(byte blockId, byte blockData) {
		this.blockId = blockId;
		this.blockData = blockData;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public byte getBlockId() {
		return blockId;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public byte getBlockData() {
		return blockData;
	}
}
