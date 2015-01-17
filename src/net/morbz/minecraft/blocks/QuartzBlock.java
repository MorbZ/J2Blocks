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
 * The class for a quartz block. There are two different constructors depending on what variant of 
 * the quartz block is needed.
 * 
 * @author MorbZ
 */
public class QuartzBlock implements IBlock {
	private int value;
	
	/**
	 * Creates a new instance. This constructor is used for the basic variants default and chiseled
	 * that don't have facing data.
	 * 
	 * @param variant The variant of the quartz block
	 */
	public QuartzBlock(QuartzVariant variant) {
		value = variant.getValue();
	}
	
	/**
	 * Creates a new instance. This constructor is used for the pillar variant which is the only
	 * variant that has facing data.
	 * 
	 * @param facing The facing of the pillar quartz block
	 */
	public QuartzBlock(QuartzFacing facing) {
		value = facing.getValue();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public byte getBlockId() {
		return (byte)Material.QUARTZ_BLOCK.getValue();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public byte getBlockData() {
		return (byte)value;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getTransparency() {
		return 0;
	}
	
	/**
	 * The variant of the quartz block. Except pillar which is a special type because it has facing
	 * data.
	 */
	@SuppressWarnings("javadoc")
	public enum QuartzVariant {
		DEFAULT(0),
		CHISELED(1);
		
		private int value;
		private QuartzVariant(int value) {
			this.value = value;
		}
		public int getValue() {
			return value;
		}
	}
	
	/**
	 * The facing of the quartz block. This is only used for the pillar variant.
	 */
	@SuppressWarnings("javadoc")
	public enum QuartzFacing {
		VERTICAL(2),
		NORTH_SOUTH(3),
		EAST_WEST(4);
		
		private int value;
		private QuartzFacing(int value) {
			this.value = value;
		}
		public int getValue() {
			return value;
		}
	}
}
