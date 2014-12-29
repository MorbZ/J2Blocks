package net.morbz.minecraft.world;

import java.awt.Point;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jnbt.NBTOutputStream;

import net.morbz.minecraft.blocks.IBlock;
import net.morbz.minecraft.level.Level;

/**
 * The main class for generating a Minecraft map.
 * 
 * @author MorbZ
 */
public class World {
	/**
	 * Maximal world height
	 */
	public final int MAX_HEIGHT = 256;
	
	private Map<Point, Region> regions = new HashMap<Point, Region>();
	private Level level;
	
	/**
	 * Creates a new instance.
	 * 
	 * @param level The level that is used to define the world settings
	 */
	public World(Level level) {
		this.level = level;
	}
	
	/**
	 * Sets a block at the given world position.
	 * 
	 * @param x The X-coordinate
	 * @param y The Y-coordinate
	 * @param z The Z-coordinate
	 * @param block The block
	 */
	public void setBlock(int x, int y, int z, IBlock block) {
		// TODO: Validate Y-coord
		// Get region point
		int regionX = x / Region.BLOCKS_PER_REGION_SIDE;
		if(x < 0) {
			regionX--;
		}
		int regionZ = z / Region.BLOCKS_PER_REGION_SIDE;
		if(z < 0) {
			regionZ--;
		}
		Point point = new Point(regionX, regionZ);
		
		// Create region
		Region region = regions.get(point);
		if(region == null) {
			region = new Region();
			regions.put(point, region);
		}
		
		// Set block
		int blockX = x % Region.BLOCKS_PER_REGION_SIDE;
		if(blockX < 0) {
			blockX += Region.BLOCKS_PER_REGION_SIDE;
		}
		int blockZ = z % Region.BLOCKS_PER_REGION_SIDE;
		if(blockZ < 0) {
			blockZ += Region.BLOCKS_PER_REGION_SIDE;
		}
		region.setBlock(blockX, y, blockZ, block);
	}
	
	/**
	 * Saves the world in a new directory within the /worlds/ directory. The name of the directory 
	 * is the level name. When there are multiple worlds with the same name they will be numbered.
	 * 
	 * @return The directory in which the world has been saved
	 * @throws IOException When file writing fails
	 */
	// TODO: Set spawn position to the center of all set blocks
	public File save() throws IOException {
		// Create worlds directory
		File worldDir = new File("worlds");
		if(!dirExists(worldDir)) {
			worldDir.mkdir();
		}
		
		// Get level directory
		String levelName = level.getLevelName();
		File levelDir = new File(worldDir, levelName);
		if(dirExists(levelDir)) {
			int dirPostfix = 0;
			do {
				dirPostfix++;
				levelDir = new File(worldDir, levelName + dirPostfix);
			} while(dirExists(levelDir));
		}
		
		// Create directories
		levelDir.mkdir();
		
		File regionDir = new File(levelDir, "region");
		regionDir.mkdir();
		
		// Write session.lock
        File sessionLockFile = new File(levelDir, "session.lock");
        System.out.println("Writing file: " + sessionLockFile);
        DataOutputStream dos = new DataOutputStream(new FileOutputStream(sessionLockFile));
        try {
        	dos.writeLong(System.currentTimeMillis());
        } finally {
        	dos.close();
        }
		
		// Write level.dat
		File levelFile = new File(levelDir, "level.dat");
		System.out.println("Writing file: " + levelFile);
		FileOutputStream fos = new FileOutputStream(levelFile);
		NBTOutputStream nbtOut = new NBTOutputStream(fos, true);
		try {
			nbtOut.writeTag(level.getTag());
		} finally {
			nbtOut.close();
		}
		
		// Iterate regions
		for(Map.Entry<Point, Region> entry : regions.entrySet()) {
			Point point = entry.getKey();
			Region region = entry.getValue();
			
			// Save region
			File regionFile = new File(regionDir, "r." + point.x + "." + point.y + ".mca");
			System.out.println("Writing file: " + regionFile);
			region.writeToFile(regionFile);
		}
		
		System.out.println("Done");
		return levelDir;
	}
	
	private boolean dirExists(File f) {
		return(f.exists() && f.isDirectory());
	}
}
