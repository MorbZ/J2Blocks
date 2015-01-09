package net.morbz.minecraft.level;

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

import net.morbz.minecraft.tags.CompoundTagFactory;
import net.morbz.minecraft.tags.ITagProvider;

import org.jnbt.ByteTag;
import org.jnbt.IntTag;
import org.jnbt.LongTag;
import org.jnbt.StringTag;
import org.jnbt.Tag;

/**
 * The level defines the settings for the world. Like game mode, spawn point, etc.
 * 
 * @author MorbZ
 */
public class Level implements ITagProvider {
	private String levelName;
	
	/**
	 * @return The name of the world. This value is also used as directory name for the
	 * generated world.
	 */
	public String getLevelName() {
		return levelName;
	}

	private boolean allowCommands = false;
	
	/**
	 * @return If cheats are allowed. The default value is 'false'.
	 */
	public boolean getAllowCommands() {
		return allowCommands;
	}

	/**
	 * @param allowCommands If cheats are allowed. The default value is 'false'.
	 */
	public void setAllowCommands(boolean allowCommands) {
		this.allowCommands = allowCommands;
	}
	
	private boolean mapFeatures = true;

	/**
	 * @return If structures like villages, mineshafts, etc. should be generated. The default value
	 * is 'true'.
	 */
	public boolean getMapFeatures() {
		return mapFeatures;
	}

	/**
	 * @param mapFeatures If structures like villages, mineshafts, etc. should be generated. The 
	 * default value is 'true'.
	 */
	public void setMapFeatures(boolean mapFeatures) {
		this.mapFeatures = mapFeatures;
	}
	
	private GameType gameType = GameType.CREATIVE;

	/**
	 * @return The game mode. The default value is 'GameType.CREATIVE'.
	 */
	public GameType getGameType() {
		return gameType;
	}

	/**
	 * @param gameType The game mode. The default value is 'GameType.CREATIVE'.
	 */
	public void setGameType(GameType gameType) {
		this.gameType = gameType;
	}

	private int spawnX, spawnY, spawnZ;
	
	/**
	 * Sets the default world spawn position. In singleplayer mode the player will be spawned within
	 * 20x20 blocks around this position. The default values are '0'.
	 * 
	 * @param x The X-coordinate
	 * @param y The Y-coordinate
	 * @param z The Z-coordinate
	 */
	// TODO: Y-coordinate doesn't work correctly
	public void setSpawnPoint(int x, int y, int z) {
		spawnX = x;
		spawnY = y;
		spawnZ = z;
	}
	
	private long randomSeed;
	
	/**
	 * @return The random seed that is used for world generation. The default value is a random
	 * positive long.
	 */
	public long getRandomSeed() {
		return randomSeed;
	}

	/**
	 * @param randomSeed The random seed that is used for world generation. The default value is a 
	 * random positive long.
	 */
	public void setRandomSeed(long randomSeed) {
		this.randomSeed = randomSeed;
	}
	
	private IGenerator generator = new FlatGenerator();

	/**
	 * Creates a new instance. The default level generator is 'FlatGenerator'.
	 * 
	 * @param levelName The name of the world. This value is also used as directory name for the
	 * generated world.
	 */
	public Level(String levelName) {
		this.levelName = levelName;
		
		// Generate random seed
		makeRandomSeed();
	}
	
	/**
	 * Creates a new instance.
	 * 
	 * @param levelName  The name of the world. This value is also used as directory name for the
	 * generated world.
	 * @param generator The generator that is used for the world.
	 */
	public Level(String levelName, IGenerator generator) {
		this.levelName = levelName;
		this.generator = generator;
		
		// Generate random seed
		makeRandomSeed();
	}
	
	/**
	 * Generates and sets a random seed.
	 */
	private void makeRandomSeed() {
		// TODO: Add support for negative random values
		randomSeed = (long)(Math.random() * Long.MAX_VALUE);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Tag getTag() {
		// Set level tags
		CompoundTagFactory factory = new CompoundTagFactory("Data");
		factory.set(new ByteTag("allowCommands", allowCommands ? (byte)1 : (byte)0));
		factory.set(new IntTag("GameType", gameType.getValue()));
		factory.set(new StringTag("generatorName", generator.getGeneratorName()));
		factory.set(new LongTag("LastPlayed", System.currentTimeMillis()));
		factory.set(new StringTag("LevelName", levelName));
		factory.set(new ByteTag("MapFeatures", mapFeatures ? (byte)1 : (byte)0));
		factory.set(new LongTag("RandomSeed", randomSeed));
		factory.set(new IntTag("SpawnX", spawnX));
		factory.set(new IntTag("SpawnY", spawnY));
		factory.set(new IntTag("SpawnZ", spawnZ));
		factory.set(new IntTag("version", 19133));
		
		// Generator options
		String options = generator.getGeneratorOptions();
		if(options != null) {
			factory.set(new StringTag("generatorOptions", options));
		}
		
		// Make root tag
		CompoundTagFactory factory2 = new CompoundTagFactory("");
		factory2.set(factory.getTag());
		return factory2.getTag();
	}
}
