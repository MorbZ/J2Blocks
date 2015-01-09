package net.morbz.minecraft.world;

/*
* The MIT License (MIT)
* 
* Copyright (c) 2014-2015 Merten Peetz
* 
* Permission is hereby granted, free of charge, to any person obtaining a copy
* of this software and associated documentation files (the "Software"), to deal
* in the Software without restriction, including without limitation the rights
* to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
* copies of the Software, and to permit persons to whom the Software is
* furnished to do so, subject to the following conditions:
* 
* The above copyright notice and this permission notice shall be included in all
* copies or substantial portions of the Software.
* 
* THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
* IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
* FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
* AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
* LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
* OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
* SOFTWARE.
*/

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
