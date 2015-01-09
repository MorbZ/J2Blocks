package net.morbz.minecraft.level;

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

import java.util.ArrayList;

import net.morbz.minecraft.blocks.Material;
import net.morbz.minecraft.utils.StringUtils;
import net.morbz.minecraft.world.DefaultLayers;
import net.morbz.minecraft.world.World;

/**
 * This class can be used to define the default block layers of the part of the world that will be 
 * created by the internal Minecraft generator. This results in a flat world where the blocks of 
 * each Y-coordinate are the same. It is recommended to combine the FlatGenerator with the 
 * DefaultLayers to get a consistent world.
 * 
 * @author MorbZ
 */
public class FlatGenerator implements IGenerator {
	private DefaultLayers layers;
	
	/**
	 * Creates a new instance. The default flat generator will be used without custom layers.
	 */
	public FlatGenerator() {
		
	}
	
	/** 
	 * Creates a new instance. Uses the custom default layers for world generation.
	 * 
	 * @param layers The default layers. Can be 'null'
	 */
	public FlatGenerator(DefaultLayers layers) {
		this.layers = layers;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getGeneratorName() {
		return "flat";
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getGeneratorOptions() {
		// Are there layers?
		if(layers == null) {
			return null;
		}
		
		// Create generator options string
		int lastBlockId = 0;
		int count = 0;
		ArrayList<String> parts = new ArrayList<String>();
		for(int y = 0; y <= World.MAX_HEIGHT; y++) {
			boolean isLast = y == World.MAX_HEIGHT;
			
			// Get block id
			int blockId = 0;
			if(!isLast) {
				Material material = layers.getLayer(y);
				if(material != null) {
					blockId = material.getValue();
				}
			}
			
			if(y == 0 && !isLast) {
				// First pass
				lastBlockId = blockId;
			} else {
				// Further passes
				if(blockId != lastBlockId || isLast) {
					// Different block, add to string
					String part = "";
					if(count != 1) {
						part += count + "*";
					}
					part += lastBlockId;
					
					// Don't add the last part if it's air
					if(!isLast || lastBlockId != 0) {
						parts.add(part);
					}
					
					lastBlockId = blockId;
					count = 0;
				}
			}
			count++;
		}
		
		// Create options string
		String layerOptions = StringUtils.join(parts.toArray(new String[parts.size()]), ",");
		String options = "3;" + layerOptions;
		return options;
	}
}
