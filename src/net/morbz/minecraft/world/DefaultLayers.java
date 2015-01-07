package net.morbz.minecraft.world;

import net.morbz.minecraft.blocks.Material;

/**
 * This class can be used to define the default block layers of the part of the world that was 
 * created by J2Blocks. This results in a flat world where the blocks of each Y-coordinate are the 
 * same until they get overwritten. It is recommended to combine the DefaultLayers with the 
 * FlatGenerator to get a consistent world.
 * 
 * @author MorbZ
 */
public class DefaultLayers {
	private Material[] layers = new Material[World.MAX_HEIGHT];
	
	/**
	 * Sets the layer at the given Y-coordinate with the given material.
	 * 
	 * @param y The Y-coordinate
	 * @param material The material
	 */
	public void setLayer(int y, Material material) {
		// Validate layer
		if(!validLayer(y)) {
			// Fail silently
			return;
		}
		
		// Set layer
		layers[y] = material;
	}
	
	/**
	 * Sets the layers of the given range of Y-coordinates (including y1 and y2) with the given 
	 * material.
	 * 
	 * @param y1 The lower Y-coordinate
	 * @param y2 The higher Y-coordinate
	 * @param material The material
	 */
	public void setLayers(int y1, int y2, Material material) {
		// Validate layers
		if(!validLayer(y1) || !validLayer(y2)) {
			// Fail silently
			return;
		}
		
		// Set layers
		for(int y = y1; y <= y2; y++) {
			layers[y] = material;
		}
	}
	
	/**
	 * Get the material at the given Y-coordinate.
	 * 
	 * @param y The Y-coordinate
	 * @return The material. Can be 'null'
	 */
	public Material getLayer(int y) {
		// Validate layer
		if(!validLayer(y)) {
			return null;
		}
		
		return layers[y];
	}
	
	/**
	 * Checks whether the Y-coordinate is valid.
	 */
	private boolean validLayer(int y) {
		if(y > layers.length - 1 || y < 0) {
			return false;
		}
		return true;
	}
}
