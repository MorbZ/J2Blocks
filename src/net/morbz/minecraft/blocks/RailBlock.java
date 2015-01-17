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

import net.morbz.minecraft.blocks.states.Facing2State;
import net.morbz.minecraft.blocks.states.Facing4State;

/**
 * The class for basic rails. There are straight, curved and sloped rails that can be created via
 * the makeStraight(), makeCurved() and makeSloped() methods.
 * 
 * @author MorbZ
 */
public class RailBlock implements IBlock {
	private int value;
	
	// Private constructor to prevent instantiation.
	private RailBlock(int value) {
		this.value = value;
	}
	
	/**
	 * Creates straight, flat rails going either in north/south or east/west direction.
	 * 
	 * @param facing The direction of the rails
	 * @return A new rails block
	 */
	public static RailBlock makeStraight(Facing2State facing) {
		// Facing direction
		int value = 0;
		switch(facing) {
		case NORTH_SOUTH:
			value = 0;
			break;
		case EAST_WEST:
			value = 1;
			break;
		}
		return new RailBlock(value);
	}
	
	/**
	 * Creates straight rails that are sloped.
	 * 
	 * @param facing The direction in which the rails are ascending
	 * @return A new rails block
	 */
	public static RailBlock makeSloped(Facing4State facing) {
		// Ascending direction
		int value = 0;
		switch(facing) {
		case EAST:
			value = 2;
			break;
		case WEST:
			value = 3;
			break;
		case NORTH:
			value = 4;
			break;
		case SOUTH:
			value = 5;
			break;
		}
		return new RailBlock(value);
	}
	
	/**
	 * Creates curved, flat rails.
	 * 
	 * @param curve The type of the curve
	 * @return A new rails block
	 */
	public static RailBlock makeCurved(RailCurve curve) {
		return new RailBlock(curve.getValue());
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public byte getBlockId() {
		return (byte)Material.RAIL.getValue();
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
		return 1;
	}
	
	@SuppressWarnings("javadoc")
	public enum RailCurve {
		SOUTH_EAST(6),
		SOUTH_WEST(7),
		NORTH_WEST(8),
		NORTH_EAST(9);
		
		private int value;
		private RailCurve(int value) {
			this.value = value;
		}
		public int getValue() {
			return value;
		}
	}
}
