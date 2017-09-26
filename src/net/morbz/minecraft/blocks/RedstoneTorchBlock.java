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

import net.morbz.minecraft.blocks.states.Facing5State;

/**
 * A redstone torch for both states active and inactive.
 * 
 * @author MorbZ
 */
public class RedstoneTorchBlock extends Facing5Block {
	private boolean isActive;
	
	/**
	 * Creates a new instance.
	 * 
	 * @param isActive Whether the torch active
	 * @param facing The direction in which the torch is facing
	 */
	public RedstoneTorchBlock(boolean isActive, Facing5State facing) {
		super(facing);
		this.isActive = isActive;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Material getMaterial() {
		// Is active?
		return isActive ? Material.REDSTONE_TORCH : Material.UNLIT_REDSTONE_TORCH;
	}
}
