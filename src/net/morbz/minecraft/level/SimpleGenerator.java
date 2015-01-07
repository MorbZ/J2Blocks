package net.morbz.minecraft.level;

/**
 * The class for world generators that don't have generator options.
 * 
 * @author MorbZ
 */
public enum SimpleGenerator implements IGenerator {
	/**
	 * Default generator
	 */
	DEFAULT("default"),
	
	/**
	 * Amplified generator
	 */
	AMPLIFIED("amplified"),
	
	/**
	 * Large biomes generator
	 */
	LARGE_BIOMES("largeBiomes");
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getGeneratorName() {
		return value;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getGeneratorOptions() {
		return null;
	}
	
	private String value;
	private SimpleGenerator(String value) {
		this.value = value;
	}
}
