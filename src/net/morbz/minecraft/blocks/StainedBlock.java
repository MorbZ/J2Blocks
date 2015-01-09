package net.morbz.minecraft.blocks;

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

/**
 * A class for all blocks that have a stained version (glass, wool, carpet, clay).
 * 
 * @author MorbZ
 */
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
	@SuppressWarnings("javadoc")
	public enum StainedMaterial {
		WOOL(Material.WOOL),
		GLASS(Material.STAINED_GLASS),
		CLAY(Material.STAINED_HARDENED_CLAY),
		GLASS_PANE(Material.STAINED_GLASS_PANE),
		CARPET(Material.CARPET);
		
		private Material material;
		private StainedMaterial(Material material) {
			this.material = material;
		}
		public int getValue() {
			return material.getValue();
		}
	}
	
	/**
	 * The color of the stained block.
	 */
	@SuppressWarnings("javadoc")
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
