package net.morbz.minecraft.level;

/**
 * Interface for world generators.
 * 
 * @author MorbZ
 */
// TODO: Implement the 'custom' generator
public interface IGenerator {
	/**
	 * Returns the generator name as it is used in the level file.
	 * 
	 * @return The generator name
	 */
	public String getGeneratorName();
	
	/**
	 * Returns the generator options as they are used in the level file.
	 * 
	 * @return The generator options. Can be 'null'
	 */
	public String getGeneratorOptions();
}
