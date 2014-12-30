package net.morbz.minecraft.blocks;

/**
 * A class for all blocks that have a stained version (glass, wool, carpet, clay).
 * 
 * @author MorbZ
 */
@SuppressWarnings("javadoc")
public class StainedBlock implements IBlock {
	private StainedMaterial material;
	private StainedColor color;
	
	/**
	 * Creates a new instance.
	 * 
	 * @param material The material of the stained block
	 * @param color The color of the block
	 */
	public StainedBlock(StainedMaterial material, StainedColor color) {
		this.material = material;
		this.color = color;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public byte getBlockId() {
		return (byte)material.getValue();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public byte getBlockData() {
		return (byte)color.getValue();
	}
	
	/**
	 * The material of the stained block.
	 */
	public enum StainedMaterial {
		WOOL(35),
		GLASS(95),
		CLAY(159),
		GLASS_PANE(160),
		CARPET(171);
		
		private int value;
		private StainedMaterial(int value) {
			this.value = value;
		}
		public int getValue() {
			return value;
		}
	}
	
	/**
	 * The color of the stained block.
	 */
	public enum StainedColor {
		WHITE(0),
		ORANGE(1),
		MAGENTA(2),
		LIGHT_BLUE(3),
		YELLOW(4),
		LIME(5),
		PINK(6),
		GRAY(7),
		LIGHT_GRAY(8),
		CYAN(9),
		PURPLE(10),
		BLUE(11),
		BROWN(12),
		GREEN(13),
		RED(14),
		BLACK(15);
		
		private int value;
		private StainedColor(int value) {
			this.value = value;
		}
		public int getValue() {
			return value;
		}
	}
}
