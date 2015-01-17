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
 * This is an array for nibbles (4-bit values).
 * 
 * @author MorbZ
 */
public class NibbleArray {
	private byte[] bytes;
	private int size;
	
	/**
	 * Creates a new instance.
	 * 
	 * @param size The number elements that the array can hold
	 */
	public NibbleArray(int size) {
		// Round up the size in case it's odd
		this.size = size;
		int num = (int)Math.ceil(size / 2.0);
		bytes = new byte[num];
	}
	
	/**
	 * Sets an element.
	 * 
	 * @param index The index of the element
	 * @param value The value of the element
	 */
	public void set(int index, byte value) {
		byte data = bytes[index / 2];
		if(index % 2 == 0) {
			data |= (byte)(value & 0xF);
		} else {
			data |= (byte)((value & 0xF) << 4);
		}
		bytes[index / 2] = data;
	}
	
	/**
	 * Gets an element.
	 * 
	 * @param index The index of the element
	 * @return The value of the element
	 */
	public byte get(int index) {
		byte data = bytes[index / 2];
		if(index % 2 == 0) {
			return (byte)(data & 0xF);
		} else {
			return (byte)((data >> 4) & 0xF);
		}
	}
	
	/**
	 * @return The number of elements
	 */
	public int size() {
		return size;
	}
	
	/**
	 * Returns the byte array that holds the nibble values. In case the size of the nibble array is
	 * odd the last byte will only hold one nibble.
	 * 
	 * @return The byte array
	 */
	public byte[] getBytes() {
		return bytes;
	}
}
