package net.morbz.minecraft.level;

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
