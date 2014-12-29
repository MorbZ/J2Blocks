package net.morbz.minecraft.level;

/**
 * Defines a generator type for the level.
 * 
 * @author MorbZ
 */
// TODO: Create a generator class for setting the default block layers
public enum GeneratorType {
	/**
	 * Default generator
	 */
	DEFAULT("default"),
	
	/**
	 * Amplified generator
	 */
	AMPLIFIED("amplified"),
	
	/**
	 * Flat generator
	 */
	FLAT("flat"),
	
	/**
	 * Large biomes generator
	 */
	LARGE_BIOMES("largeBiomes");
	
	private String value;
	private GeneratorType(String value) {
		this.value = value;
	}
	
	/**
	 * Returns the value.
	 * 
	 * @return The value
	 */
	public String getValue() {
		return value;
	}
}
