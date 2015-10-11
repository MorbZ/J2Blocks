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
 * This class defines all the basic block with their IDs.
 * 
 * @author MorbZ
 */
@SuppressWarnings("javadoc")
public class Material {
	private int value;
	private int transparency;
	
	private Material(int value, int transparency) {
		this.value = value;
		this.transparency = transparency;
	}
	
	/**
	 * Returns the value of this material (block ID)
	 * 
	 * @return The block ID
	 */
	public int getValue() {
		return value;
	}
	
	/**
	 * Returns the transparency level of this material
	 * 
	 * @return The transparency level
	 */
	public int getTransparency() {
		return transparency;
	}
	
	public static final Material AIR = new Material(0, 1); // √
	public static final Material STONE = new Material(1, 0); // √
	public static final Material GRASS = new Material(2, 0); // √
	public static final Material DIRT = new Material(3, 0); // √
	public static final Material COBBLESTONE = new Material(4, 0); // √
	public static final Material PLANKS = new Material(5, 0); // √
	public static final Material SAPLING = new Material(6, 1); // ~ (Growth stage missing)
	public static final Material BEDROCK = new Material(7, 0); // √
	public static final Material FLOWING_WATER = new Material(8, 2);
	public static final Material WATER = new Material(9, 2); // √
	public static final Material FLOWING_LAVA = new Material(10, 1);
	public static final Material LAVA = new Material(11, 1); // √
	public static final Material SAND = new Material(12, 0); // √
	public static final Material GRAVEL = new Material(13, 0); // √
	public static final Material GOLD_ORE = new Material(14, 0); // √
	public static final Material IRON_ORE = new Material(15, 0); // √
	public static final Material COAL_ORE = new Material(16, 0); // √
	public static final Material LOG = new Material(17, 0);
	public static final Material LEAVES = new Material(18, 2); // Diffuses sky light
	public static final Material SPONGE = new Material(19, 0);
	public static final Material GLASS = new Material(20, 1); // √
	public static final Material LAPIS_ORE = new Material(21, 0); // √
	public static final Material LAPIS_BLOCK = new Material(22, 0); // √
	public static final Material DISPENSER = new Material(23, 0);
	public static final Material SANDSTONE = new Material(24, 0); // √
	public static final Material NOTEBLOCK = new Material(25, 0);
	public static final Material BED = new Material(26, 1);
	public static final Material GOLDEN_RAIL = new Material(27, 1); // √
	public static final Material DETECTOR_RAIL = new Material(28, 1); // √
	public static final Material STICKY_PISTON = new Material(29, 1);
	public static final Material WEB = new Material(30, 2); // √, Diffuses sky light
	public static final Material TALLGRASS = new Material(31, 1);
	public static final Material DEADBUSH = new Material(32, 1); // √
	public static final Material PISTON = new Material(33, 1);
	public static final Material PISTON_HEAD = new Material(34, 1);
	public static final Material WOOL = new Material(35, 0); // √
	public static final Material PISTON_EXTENSION = new Material(36, 1);
	public static final Material YELLOW_FLOWER = new Material(37, 1); // √ (Double implemented)
	public static final Material RED_FLOWER = new Material(38, 1); // √
	public static final Material BROWN_MUSHROOM = new Material(39, 1); // √
	public static final Material RED_MUSHROOM = new Material(40, 1); // √
	public static final Material GOLD_BLOCK = new Material(41, 0); // √
	public static final Material IRON_BLOCK = new Material(42, 0); // √
	public static final Material DOUBLE_STONE_SLAB = new Material(43, 0);
	public static final Material STONE_SLAB = new Material(44, 1);
	public static final Material BRICK_BLOCK = new Material(45, 0); // √
	public static final Material TNT = new Material(46, 0);
	public static final Material BOOKSHELF = new Material(47, 0); // √
	public static final Material MOSSY_COBBLESTONE = new Material(48, 0); // √
	public static final Material OBSIDIAN = new Material(49, 0); // √
	public static final Material TORCH = new Material(50, 1);
	public static final Material FIRE = new Material(51, 1);
	public static final Material MOB_SPAWNER = new Material(52, 0); // Only graphical transparency
	public static final Material OAK_STAIRS = new Material(53, 0); // Partial transparency
	public static final Material CHEST = new Material(54, 1);
	public static final Material REDSTONE_WIRE = new Material(55, 1);
	public static final Material DIAMOND_ORE = new Material(56, 0); // √
	public static final Material DIAMOND_BLOCK = new Material(57, 0); // √
	public static final Material CRAFTING_TABLE = new Material(58, 0); // √
	public static final Material WHEAT = new Material(59, 1);
	public static final Material FARMLAND = new Material(60, 0); // Partial transparency
	public static final Material FURNACE = new Material(61, 0);
	public static final Material LIT_FURNACE = new Material(62, 0);
	public static final Material STANDING_SIGN = new Material(63, 1);
	public static final Material WOODEN_DOOR = new Material(64, 1); // √
	public static final Material LADDER = new Material(65, 1);
	public static final Material RAIL = new Material(66, 1); // √
	public static final Material STONE_STAIRS = new Material(67, 0); // Partial transparency
	public static final Material WALL_SIGN = new Material(68, 0);
	public static final Material LEVER = new Material(69, 1);
	public static final Material STONE_PRESSURE_PLATE = new Material(70, 1);
	public static final Material IRON_DOOR = new Material(71, 1); // √
	public static final Material WOODEN_PRESSURE_PLATE = new Material(72, 1);
	public static final Material REDSTONE_ORE = new Material(73, 0); // √
	public static final Material LIT_REDSTONE_ORE = new Material(74, 1); // √
	public static final Material UNLIT_REDSTONE_TORCH = new Material(75, 1); // √
	public static final Material REDSTONE_TORCH = new Material(76, 1); // √
	public static final Material STONE_BUTTON = new Material(77, 1);
	public static final Material SNOW_LAYER = new Material(78, 1);
	public static final Material ICE = new Material(79, 2); // √
	public static final Material SNOW = new Material(80, 1); // √
	public static final Material CACTUS = new Material(81, 1);
	public static final Material CLAY = new Material(82, 0); // √
	public static final Material REEDS = new Material(83, 1);
	public static final Material JUKEBOX = new Material(84, 0);
	public static final Material FENCE = new Material(85, 1); // √
	public static final Material PUMPKIN = new Material(86, 0);
	public static final Material NETHERRACK = new Material(87, 0); // √
	public static final Material SOUL_SAND = new Material(88, 0); // √
	public static final Material GLOWSTONE = new Material(89, 1); // √
	public static final Material PORTAL = new Material(90, 1); // √
	public static final Material LIT_PUMPKIN = new Material(91, 0);
	public static final Material CAKE = new Material(92, 1);
	public static final Material UNPOWERED_REPEATER = new Material(93, 1);
	public static final Material POWERED_REPEATER = new Material(94, 1);
	public static final Material STAINED_GLASS = new Material(95, 1); // √
	public static final Material TRAPDOOR = new Material(96, 1);
	public static final Material MONSTER_EGG = new Material(97, 0);
	public static final Material STONEBRICK = new Material(98, 0); // √
	public static final Material BROWN_MUSHROOM_BLOCK = new Material(99, 0);
	public static final Material RED_MUSHROOM_BLOCK = new Material(100, 0);
	public static final Material IRON_BARS = new Material(101, 1); // √
	public static final Material GLASS_PANE = new Material(102, 1); // √
	public static final Material MELON_BLOCK = new Material(103, 0); // √
	public static final Material PUMPKIN_STEM = new Material(104, 1);
	public static final Material MELON_STEM = new Material(105, 1);
	public static final Material VINE = new Material(106, 1);
	public static final Material FENCE_GATE = new Material(107, 1);
	public static final Material BRICK_STAIRS = new Material(108, 0); // Partial transparency
	public static final Material STONE_BRICK_STAIRS = new Material(109, 0); // Partial transparency
	public static final Material MYCELIUM = new Material(110, 0); // √
	public static final Material WATERLILY = new Material(111, 1); // √
	public static final Material NETHER_BRICK = new Material(112, 0); // √
	public static final Material NETHER_BRICK_FENCE = new Material(113, 1); // √
	public static final Material NETHER_BRICK_STAIRS = new Material(114, 0); // Partial transparency
	public static final Material NETHER_WART = new Material(115, 1);
	public static final Material ENCHANTING_TABLE = new Material(116, 1);
	public static final Material BREWING_STAND = new Material(117, 1);
	public static final Material CAULDRON = new Material(118, 1);
	public static final Material END_PORTAL = new Material(119, 1);
	public static final Material END_PORTAL_FRAME = new Material(120, 0);
	public static final Material END_STONE = new Material(121, 0); // √
	public static final Material DRAGON_EGG = new Material(122, 1); // √
	public static final Material REDSTONE_LAMP = new Material(123, 0); // √
	public static final Material LIT_REDSTONE_LAMP = new Material(124, 1); // √
	public static final Material DOUBLE_WOODEN_SLAB = new Material(125, 0);
	public static final Material WOODEN_SLAB = new Material(126, 1);
	public static final Material COCOA = new Material(127, 1);
	public static final Material SANDSTONE_STAIRS = new Material(128, 0); // Partial transparency
	public static final Material EMERALD_ORE = new Material(129, 0); // √
	public static final Material ENDER_CHEST = new Material(130, 1);
	public static final Material TRIPWIRE_HOOK = new Material(131, 1);
	public static final Material TRIPWIRE = new Material(132, 1);
	public static final Material EMERALD_BLOCK = new Material(133, 0); // √
	public static final Material SPRUCE_STAIRS = new Material(134, 0); // Partial transparency
	public static final Material BIRCH_STAIRS = new Material(135, 0); // Partial transparency
	public static final Material JUNGLE_STAIRS = new Material(136, 0); // Partial transparency
	public static final Material COMMAND_BLOCK = new Material(137, 0);
	public static final Material BEACON = new Material(138, 1);
	public static final Material COBBLESTONE_WALL = new Material(139, 1);
	public static final Material FLOWER_POT = new Material(140, 1);
	public static final Material CARROTS = new Material(141, 1);
	public static final Material POTATOES = new Material(142, 1);
	public static final Material WOODEN_BUTTON = new Material(143, 1);
	public static final Material SKULL = new Material(144, 1);
	public static final Material ANVIL = new Material(145, 1);
	public static final Material TRAPPED_CHEST = new Material(146, 1);
	public static final Material LIGHT_WEIGHTED_PRESSURE_PLATE = new Material(147, 1);
	public static final Material HEAVY_WEIGHTED_PRESSURE_PLATE = new Material(148, 1);
	public static final Material UNPOWERED_COMPARATOR = new Material(149, 1);
	public static final Material POWERED_COMPARATOR = new Material(150, 1);
	public static final Material DAYLIGHT_DETECTOR = new Material(151, 1);
	public static final Material REDSTONE_BLOCK = new Material(152, 0); // √, Partial transparency
	public static final Material QUARTZ_ORE = new Material(153, 0); // √
	public static final Material HOPPER = new Material(154, 1);
	public static final Material QUARTZ_BLOCK = new Material(155, 0); // √
	public static final Material QUARTZ_STAIRS = new Material(156, 0); // Partial transparency
	public static final Material ACTIVATOR_RAIL = new Material(157, 1); // √
	public static final Material DROPPER = new Material(158, 0);
	public static final Material STAINED_HARDENED_CLAY = new Material(159, 0); // √
	public static final Material STAINED_GLASS_PANE = new Material(160, 1); // √
	public static final Material LEAVES2 = new Material(161, 2); // Diffuses sky light
	public static final Material LOG2 = new Material(162, 0);
	public static final Material ACACIA_STAIRS = new Material(163, 0); // Partial transparency
	public static final Material DARK_OAK_STAIRS = new Material(164, 0); // Partial transparency
	public static final Material SLIME_BLOCK = new Material(165, 1); // √
	public static final Material BARRIER = new Material(166, 1); // √
	public static final Material IRON_TRAPDOOR = new Material(167, 1);
	public static final Material PRISMARINE = new Material(168, 0); // √
	public static final Material SEA_LANTERN = new Material(169, 1); // √, Transparency not clear
	public static final Material HAY_BLOCK = new Material(170, 0);
	public static final Material CARPET = new Material(171, 1); // √
	public static final Material HARDENED_CLAY = new Material(172, 0); // √
	public static final Material COAL_BLOCK = new Material(173, 0); // √
	public static final Material PACKED_ICE = new Material(174, 0); // √
	public static final Material DOUBLE_PLANT = new Material(175, 1); // Transparency not clear
	public static final Material STANDING_BANNER = new Material(176, 1);
	public static final Material WALL_BANNER = new Material(177, 1);
	public static final Material DAYLIGHT_DETECTOR_INVERTED = new Material(178, 1);
	public static final Material RED_SANDSTONE = new Material(179, 0); // √
	public static final Material RED_SANDSTONE_STAIRS = new Material(180, 0); // Partial transparency
	public static final Material DOUBLE_STONE_SLAB2 = new Material(181, 0);
	public static final Material STONE_SLAB2 = new Material(182, 1);
	public static final Material SPRUCE_FENCE_GATE = new Material(183, 1);
	public static final Material BIRCH_FENCE_GATE = new Material(184, 1);
	public static final Material JUNGLE_FENCE_GATE = new Material(185, 1);
	public static final Material DARK_OAK_FENCE_GATE = new Material(186, 1);
	public static final Material ACACIA_FENCE_GATE = new Material(187, 1);
	public static final Material SPRUCE_FENCE = new Material(188, 1); // √
	public static final Material BIRCH_FENCE = new Material(189, 1); // √
	public static final Material JUNGLE_FENCE = new Material(190, 1); // √
	public static final Material DARK_OAK_FENCE = new Material(191, 1); // √
	public static final Material ACACIA_FENCE = new Material(192, 1); // √
	public static final Material SPRUCE_DOOR = new Material(193, 1); // √
	public static final Material BIRCH_DOOR = new Material(194, 1); // √
	public static final Material JUNGLE_DOOR = new Material(195, 1); // √
	public static final Material ACACIA_DOOR = new Material(196, 1); // √
	public static final Material DARK_OAK_DOOR = new Material(197, 1); // √
}
