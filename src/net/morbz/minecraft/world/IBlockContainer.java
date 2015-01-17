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
	 * Returns the sky light level of the block at given position. If there is no block 
	 * World.DEFAULT_SKY_LIGHT will be returned.
	 * 
	 * @param x The local X-coordinate
	 * @param y The local Y-coordinate
	 * @param z The local Z-coordinate
	 * @return The sky light level
	 */
	public byte getSkyLight(int x, int y, int z);
	
	/**
	 * Returns the sky light level of a block that is out of bounds of the child block container.
	 * 
	 * @param child The child block container
	 * @param childX The local X-coordinate
	 * @param childY The local Y-coordinate
	 * @param childZ The local Z-coordinate
	 * @return The sky light level
	 */
	public byte getSkyLightFromParent(IBlockContainer child, int childX, int childY, int childZ);
	
	/**
	 * Spreads the skylight. For each block that has the given light level it's adjacent blocks will
	 * be lit if their current light level is lower.
	 * 
	 * @param light The light level
	 */
	public void spreadSkyLight(byte light);
}
