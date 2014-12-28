package net.morbz.minecraft.level;

/**
 * Defines a game type for the level.
 * 
 * @author MorbZ
 */
public enum GameType {
	/**
	 * Survival mode
	 */
	SURVIVAL(0),
	
	/**
	 * Creative mode
	 */
	CREATIVE(1),
	
	/**
	 * Adventure mode
	 */
	ADVENTURE(2),
	
	/**
	 * Spectator mode
	 */
	SPECTATOR(3);
	
	private int value;
	private GameType(int value) {
		this.value = value;
	}
	
	/**
	 * Returns the value.
	 * 
	 * @return The value
	 */
	public int getValue() {
		return value;
	}
}
