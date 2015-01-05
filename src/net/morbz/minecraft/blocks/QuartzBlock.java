package net.morbz.minecraft.blocks;

/**
 * The class for a quartz block. There are two different constructors depending on what variant of 
 * the quartz block is needed.
 * 
 * @author MorbZ
 */
@SuppressWarnings("javadoc")
public class QuartzBlock implements IBlock {
	private byte value;
	
	/**
	 * Creates a new instance. This constructor is used for the basic variants default and chiseled
	 * that don't have facing data.
	 * 
	 * @param variant The variant of the quartz block
	 */
	public QuartzBlock(QuartzVariant variant) {
		value = (byte)variant.getValue();
	}
	
	/**
	 * Creates a new instance. This constructor is used for the pillar variant which is the only
	 * variant that has facing data.
	 * 
	 * @param facing The facing of the pillar quartz block
	 */
	public QuartzBlock(QuartzFacing facing) {
		value = (byte)facing.getValue();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public byte getBlockId() {
		return (byte)Material.QUARTZ_BLOCK.getValue();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public byte getBlockData() {
		return value;
	}
	
	/**
	 * The variant of the quartz block. Except pillar which is a special type because it has facing
	 * data.
	 */
	public enum QuartzVariant {
		DEFAULT(0),
		CHISELED(1);
		
		private int value;
		private QuartzVariant(int value) {
			this.value = value;
		}
		public int getValue() {
			return value;
		}
	}
	
	/**
	 * The facing of the quartz block. This is only used for the pillar variant.
	 */
	public enum QuartzFacing {
		VERTICAL(2),
		NORTH_SOUTH(3),
		EAST_WEST(4);
		
		private int value;
		private QuartzFacing(int value) {
			this.value = value;
		}
		public int getValue() {
			return value;
		}
	}
}
