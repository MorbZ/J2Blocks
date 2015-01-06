package net.morbz.minecraft.blocks;

import net.morbz.minecraft.blocks.states.Facing4State;
import net.morbz.minecraft.blocks.states.HalfState;

/**
 * The class for doors of all materials. A door consists of an upper and a lower block. Both can be 
 * can be created via the makeUpper() and makeLower() methods.
 * 
 * @author MorbZ
 */
public class DoorBlock implements IBlock {
	private DoorMaterial material;
	private Facing4State facing;
	private HalfState half;
	private HingeSide hinge;
	private boolean isOpen;
	
	// Private constructor to prevent instantiation.
	private DoorBlock(DoorMaterial material, Facing4State facing, HalfState half, HingeSide hinge, boolean isOpen) {
		this.material = material;
		this.facing = facing;
		this.half = half;
		this.hinge = hinge;
		this.isOpen = isOpen;
	}
	
	/**
	 * Creates the upper part of a door.
	 * 
	 * @param material The material of which the door consists
	 * @param hinge Whether the hinge is on the left or right side
	 * @return A new door block
	 */
	public static DoorBlock makeUpper(DoorMaterial material, HingeSide hinge) {
		return new DoorBlock(material, null, HalfState.UPPER, hinge, false);
	}
	
	/**
	 * Creates the lower part of a door.
	 * 
	 * @param material The material of which the door consists
	 * @param facing The direction in which the door is facing
	 * @param isOpen Whether the door is open
	 * @return A new door block
	 */
	public static DoorBlock makeLower(DoorMaterial material, Facing4State facing, boolean isOpen) {
		return new DoorBlock(material, facing, HalfState.LOWER, null, isOpen);
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
		// Make block data
		byte data = 0;
		if(half == HalfState.UPPER) {
			data |= 1 << 3;
			
			// Hinge side
			if(hinge == HingeSide.RIGHT) {
				data |= 1;
			}
		} else {
			// Facing direction
			int facingData = 0;
			switch(facing) {
			case WEST:
				facingData = 0;
				break;
			case NORTH:
				facingData = 1;
				break;
			case EAST:
				facingData = 2;
				break;
			case SOUTH:
				facingData = 3;
				break;
			}
			data |= facingData & 0x3;
			
			// Is open?
			if(isOpen) {
				data |= 1 << 2;
			}
		}
		return data;
	}
	
	/**
	 * The site on which the hinge is.
	 */
	@SuppressWarnings("javadoc")
	public enum HingeSide {
		LEFT,
		RIGHT
	}
	
	/**
	 * The material of which the door consists.
	 */
	@SuppressWarnings("javadoc")
	public enum DoorMaterial {
		OAK(Material.WOODEN_DOOR),
		IRON(Material.IRON_DOOR),
		SPRUCE(Material.SPRUCE_DOOR),
		BIRCH(Material.BIRCH_DOOR),
		JUNGLE(Material.JUNGLE_DOOR),
		ACACIA(Material.ACACIA_DOOR),
		DARK_OAK(Material.DARK_OAK_DOOR);
		
		private Material material;
		private DoorMaterial(Material material) {
			this.material = material;
		}
		public int getValue() {
			return material.getValue();
		}
	}
}
