package net.morbz.minecraft.world;

import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
	
	/**
	 * Sets a block at the given world position.
	 * 
	 * @param x The X-coordinate
	 * @param y The Y-coordinate
	 * @param z The Z-coordinate
	 * @param value The value of the block
	 */
	public void setBlock(int x, int y, int z, byte value) {
		// TODO: Validate Y-coord
		// Get region point
		int regionX = x / Region.BLOCKS_PER_REGION_SIDE;
		int regionZ = z / Region.BLOCKS_PER_REGION_SIDE;
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
		region.setBlock(blockX, y, blockZ, value);
	}
	
	/**
	 * Saves the world at the given directory.
	 * 
	 * @param dir The directory
	 */
	public void saveToDir(String dir) {
		// Iterate regions
		for(Map.Entry<Point, Region> entry : regions.entrySet()) {
			Point point = entry.getKey();
			Region region = entry.getValue();
			
			// Save region
			File file = new File(dir + "/r." + point.x + "." + point.y + ".mca");
			System.out.println("Write region: " + file);
			try {
				region.writeToFile(file);
			} catch(IOException ex) {
				System.out.println("Error while writing file.");
			}
		}
	}
}
