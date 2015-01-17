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
 * The class for all blocks that don't have block data.
 * 
 * @author MorbZ
 */
@SuppressWarnings("javadoc")
public enum SimpleBlock implements IBlock {
	AIR(Material.AIR),
	GRASS(Material.GRASS),
	COBBLESTONE(Material.COBBLESTONE),
	BEDROCK(Material.BEDROCK),
	WATER(Material.WATER),
	LAVA(Material.LAVA),
	GRAVEL(Material.GRAVEL),
	GOLD_ORE(Material.GOLD_ORE),
	IRON_ORE(Material.IRON_ORE),
	COAL_ORE(Material.COAL_ORE),
	GLASS(Material.GLASS),
	LAPIS_ORE(Material.LAPIS_ORE),
	LAPIS_BLOCK(Material.LAPIS_BLOCK),
	WEB(Material.WEB),
	DEADBUSH(Material.DEADBUSH),
	YELLOW_FLOWER(Material.YELLOW_FLOWER),
	BROWN_MUSHROOM(Material.BROWN_MUSHROOM),
	RED_MUSHROOM(Material.RED_MUSHROOM),
	GOLD_BLOCK(Material.GOLD_BLOCK),
	IRON_BLOCK(Material.IRON_BLOCK),
	BRICK_BLOCK(Material.BRICK_BLOCK),
	BOOKSHELF(Material.BOOKSHELF),
	MOSSY_COBBLESTONE(Material.MOSSY_COBBLESTONE),
	OBSIDIAN(Material.OBSIDIAN),
	DIAMOND_ORE(Material.DIAMOND_ORE),
	DIAMOND_BLOCK(Material.DIAMOND_BLOCK),
	CRAFTING_TABLE(Material.CRAFTING_TABLE),
	REDSTONE_ORE(Material.REDSTONE_ORE),
	LIT_REDSTONE_ORE(Material.LIT_REDSTONE_ORE),
	ICE(Material.ICE),
	SNOW(Material.SNOW),
	CLAY(Material.CLAY),
	FENCE(Material.FENCE),
	NETHERRACK(Material.NETHERRACK),
	SOUL_SAND(Material.SOUL_SAND),
	GLOWSTONE(Material.GLOWSTONE),
	PORTAL(Material.PORTAL),
	IRON_BARS(Material.IRON_BARS),
	GLASS_PANE(Material.GLASS_PANE),
	MELON_BLOCK(Material.MELON_BLOCK),
	MYCELIUM(Material.MYCELIUM),
	WATERLILY(Material.WATERLILY),
	NETHER_BRICK(Material.NETHER_BRICK),
	NETHER_BRICK_FENCE(Material.NETHER_BRICK_FENCE),
	END_STONE(Material.END_STONE),
	DRAGON_EGG(Material.DRAGON_EGG),
	REDSTONE_LAMP(Material.REDSTONE_LAMP),
	LIT_REDSTONE_LAMP(Material.LIT_REDSTONE_LAMP),
	EMERALD_ORE(Material.EMERALD_ORE),
	EMERALD_BLOCK(Material.EMERALD_BLOCK),
	REDSTONE_BLOCK(Material.REDSTONE_BLOCK),
	QUARTZ_ORE(Material.QUARTZ_ORE),
	SLIME_BLOCK(Material.SLIME_BLOCK),
	BARRIER(Material.BARRIER),
	SEA_LANTERN(Material.SEA_LANTERN),
	HARDENED_CLAY(Material.HARDENED_CLAY),
	COAL_BLOCK(Material.COAL_BLOCK),
	PACKED_ICE(Material.PACKED_ICE),
	SPRUCE_FENCE(Material.SPRUCE_FENCE),
	BIRCH_FENCE(Material.BIRCH_FENCE),
	JUNGLE_FENCE(Material.JUNGLE_FENCE),
	DARK_OAK_FENCE(Material.DARK_OAK_FENCE),
	ACACIA_FENCE(Material.ACACIA_FENCE);
	
	private Material material;
	private SimpleBlock(Material material) {
		this.material = material;
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
		return 0;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getTransparency() {
		return material.getTransparency();
	}
}
