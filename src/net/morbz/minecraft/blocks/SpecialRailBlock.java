package net.morbz.minecraft.blocks;

import net.morbz.minecraft.blocks.states.Facing2State;
import net.morbz.minecraft.blocks.states.Facing4State;

/**
 * The class for special rails (powered, detector and activator). There are straight and sloped 
 * rails that can be created via the makeStraight() and makeSloped() methods. Special rails cannot 
 * be curved.
 * 
 * @author MorbZ
 */
public class SpecialRailBlock implements IBlock {
	private SpecialRailMaterial material;
	private boolean isActive;
	private int value;
	
	// Private constructor to prevent instantiation.
	private SpecialRailBlock(SpecialRailMaterial material, boolean isActive, int value) {
		this.material = material;
		this.isActive = isActive;
		this.value = value;
	}
	
	/**
	 * Creates straight, flat rails going either in north/south or east/west direction.
	 * 
	 * @param material The type of the special rails
	 * @param facing The direction of the rails
	 * @param isActive Whether the rails are active
	 * @return A new special rails block
	 */
	public static SpecialRailBlock makeStraight(SpecialRailMaterial material, Facing2State facing, boolean isActive) {
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
		return new SpecialRailBlock(material, isActive, value);
	}
	
	/**
	 * Creates straight rails that are sloped.
	 * 
	 * @param material The type of the special rails
	 * @param facing The direction in which the rails are ascending
	 * @param isActive Whether the rails are active
	 * @return A new special rails block
	 */
	public static SpecialRailBlock makeSloped(SpecialRailMaterial material, Facing4State facing, boolean isActive) {
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
		return new SpecialRailBlock(material, isActive, value);
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
		byte data = (byte)value;
		
		// Is active?
		if(isActive) {
			data |= 1 << 3;
		}
		return data;
	}

	/**
	 * The type of the special rail.
	 */
	@SuppressWarnings("javadoc")
	public enum SpecialRailMaterial {
		POWERED(Material.GOLDEN_RAIL),
		DETECTOR(Material.DETECTOR_RAIL),
		ACTIVATOR(Material.ACTIVATOR_RAIL);
		
		private Material material;
		private SpecialRailMaterial(Material material) {
			this.material = material;
		}
		public int getValue() {
			return material.getValue();
		}
	}
}
