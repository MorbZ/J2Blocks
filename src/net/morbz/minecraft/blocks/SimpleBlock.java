package net.morbz.minecraft.blocks;

/**
 * The class for all blocks that don't have block data.
 * 
 * @author MorbZ
 */
@SuppressWarnings("javadoc")
public enum SimpleBlock implements IBlock {
	AIR(0),
	GRASS(2),
	COBBLESTONE(4),
	BEDROCK(7),
	WATER(9),
	LAVA(11),
	GRAVEL(13),
	GOLD_ORE(14),
	IRON_ORE(15),
	COAL_ORE(16),
	GLASS(20),
	LAPIS_ORE(21),
	LAPIS_BLOCK(22),
	WEB(30),
	DEADBUSH(32),
	YELLOW_FLOWER(37),
	BROWN_MUSHROOM(39),
	RED_MUSHROOM(40),
	GOLD_BLOCK(41),
	IRON_BLOCK(42),
	BRICK_BLOCK(45),
	BOOKSHELF(47),
	MOSSY_COBBLESTONE(48),
	OBSIDIAN(49),
	DIAMOND_ORE(56),
	DIAMOND_BLOCK(57),
	CRAFTING_TABLE(58),
	REDSTONE_ORE(73),
	LIT_REDSTONE_ORE(74),
	ICE(79),
	SNOW(80),
	CLAY(82),
	FENCE(85),
	NETHERRACK(87),
	SOUL_SAND(88),
	GLOWSTONE(89),
	PORTAL(90),
	IRON_BARS(101),
	GLASS_PANE(102),
	MELON_BLOCK(103),
	MYCELIUM(110),
	WATERLILY(111),
	NETHER_BRICK(112),
	NETHER_BRICK_FENCE(113),
	END_STONE(121),
	DRAGON_EGG(122),
	REDSTONE_LAMP(123),
	LIT_REDSTONE_LAMP(124),
	EMERALD_ORE(129),
	EMERALD_BLOCK(133),
	REDSTONE_BLOCK(152),
	QUARTZ_ORE(153),
	SLIME_BLOCK(165),
	BARRIER(166),
	SEA_LANTERN(169),
	HARDENED_CLAY(172),
	COAL_BLOCK(173),
	PACKED_ICE(174),
	SPRUCE_FENCE(188),
	BIRCH_FENCE(189),
	JUNGLE_FENCE(190),
	DARK_OAK_FENCE(191),
	ACACIA_FENCE(192);
	
	private int value;
	private SimpleBlock(int value) {
		this.value = value;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public byte getBlockId() {
		return (byte)value;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public byte getBlockData() {
		return 0;
	}
}
