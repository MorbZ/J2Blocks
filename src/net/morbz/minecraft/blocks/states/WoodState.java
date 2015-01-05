package net.morbz.minecraft.blocks.states;

/**
 * Defines all types of wood.
 * 
 * @author MorbZ
 */
@SuppressWarnings("javadoc")
public enum WoodState {
	OAK(0),
	SPRUCE(1),
	BIRCH(2),
	JUNGLE(3),
	ACACIA(4),
	DARK_OAK(5);
	
	private int value;
	private WoodState(int value) {
		this.value = value;
	}
	public int getValue() {
		return value;
	}
}
