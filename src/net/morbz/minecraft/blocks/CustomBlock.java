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
 * A custom block for testing purposes and internal functions. The block ID and block data can be 
 * set freely.
 * 
 * @author MorbZ
 */
public class CustomBlock implements IBlock {
	private int blockId, blockData, transparency;
	
	/**
	 * Creates a new instance.
	 * 
	 * @param blockId The block ID
	 * @param blockData The block data
	 * @param transparency The transparency of the block
	 */
	public CustomBlock(int blockId, int blockData, int transparency) {
		this.blockId = blockId;
		this.blockData = blockData;
		this.transparency = transparency;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public byte getBlockId() {
		return (byte)blockId;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public byte getBlockData() {
		return (byte)blockData;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getTransparency() {
		return transparency;
	}
}
