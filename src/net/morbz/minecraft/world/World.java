package net.morbz.minecraft.world;

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
	public static final int MAX_HEIGHT = 256;
	
	private Map<Point, Region> regions = new HashMap<Point, Region>();
	private Level level;
	private DefaultLayers layers;
	
	/**
	 * Creates a new instance.
	 * 
	 * @param level The level that is used to define the world settings
	 */
	public World(Level level) {
		this.level = level;
	}
	
	/**
	 * Creates a new instance.
	 * 
	 * @param level The level that is used to define the world settings
	 * @param layers The default layers. Can be 'null'
	 */
	public World(Level level, DefaultLayers layers) {
		this.level = level;
		this.layers = layers;
	}
	
	/**
	 * Sets a block at the given world position.
	 * 
	 * @param x The X-coordinate
	 * @param y The Y-coordinate (Height, Must be between 0 and 255)
	 * @param z The Z-coordinate
	 * @param block The block
	 */
	public void setBlock(int x, int y, int z, IBlock block) {
		// Check for valid height
		if(y > MAX_HEIGHT - 1 || y < 0) {
			// Fail silently
			return;
		}
		
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
			region = new Region(layers);
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
		
		// Calculate height maps
		System.out.println("Calculate height maps");
		for(Region region : regions.values()) {
			region.calculateHeightMap();
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
