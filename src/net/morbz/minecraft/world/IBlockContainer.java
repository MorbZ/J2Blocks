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

/**
 * The interface for all classes that contain blocks.
 * 
 * @author MorbZ
 */
public interface IBlockContainer {
	/**
	 * Returns the transparency level of the block at given position. If there is no block 
	 * World.DEFAULT_TRANSPARENCY will be returned. 0 means fully opaque, 1 means fully transparent
	 * and values > 1 mean transparent but the light level is decreased by n at this block.
	 * 
	 * @param x The local X-coordinate
	 * @param y The local Y-coordinate
	 * @param z The local Z-coordinate
	 * @return The transparency level
	 */
	public byte getTransparency(int x, int y, int z);
	
	/**
	 * Returns the sky light level of the block at given position. If there is no block 
	 * World.DEFAULT_SKY_LIGHT will be returned.
	 * 
	 * @param x The local X-coordinate
	 * @param y The local Y-coordinate
	 * @param z The local Z-coordinate
	 * @return The transparency level
	 */
	public byte getSkyLight(int x, int y, int z);
	
	/**
	 * Sets the sky light level of the block at given position.
	 * 
	 * @param x The local X-coordinate
	 * @param y The local Y-coordinate
	 * @param z The local Z-coordinate
	 * @param light The sky light level
	 */
	public void setSkyLight(int x, int y, int z, byte light);
}
