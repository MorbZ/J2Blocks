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
public class World implements IBlockContainer {
	/**
	 * Maximal world height
	 */
	public static final int MAX_HEIGHT = 256;
	
	/**
	 * The default transparency level (fully transparent)
	 */
	public static final byte DEFAULT_TRANSPARENCY = 1;
	
	/**
	 * The default sky light level (maximal light)
	 */
	public static final byte DEFAULT_SKY_LIGHT = 0xF;
	
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
		
		// Get region
		Region region = getRegion(x, z, true);
		
		// Set block
		int blockX = getRegionCoord(x);
		int blockZ = getRegionCoord(z);
		region.setBlock(blockX, y, blockZ, block);
	}
	
	private Region getRegion(int x, int z, boolean create) {
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
		if(region == null && create) {
			region = new Region(this, regionX, regionZ, layers);
			regions.put(point, region);
		}
		return region;	
	}
	
	private int getRegionCoord(int coord) {
		int regionCoord = coord % Region.BLOCKS_PER_REGION_SIDE;
		if(regionCoord < 0) {
			regionCoord += Region.BLOCKS_PER_REGION_SIDE;
		}
		return regionCoord;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public byte getSkyLight(int x, int y, int z) {
		// Get region
		Region region = getRegion(x, z, false);
		
		// Get light
		if(region != null) {
			int blockX = getRegionCoord(x);
			int blockZ = getRegionCoord(z);
			return region.getSkyLight(blockX, y, blockZ);
		}
		return DEFAULT_SKY_LIGHT;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public byte getSkyLightFromParent(IBlockContainer child, int childX, int childY, int childZ) {
		int x = Region.BLOCKS_PER_REGION_SIDE * ((Region)child).getX() + childX;
		int z = Region.BLOCKS_PER_REGION_SIDE * ((Region)child).getZ() + childZ;
		return getSkyLight(x, childY, z);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void spreadSkyLight(byte light) {
		for(Region region : regions.values()) {
			region.spreadSkyLight(light);
		}
	}
	
	/**
	 * Saves the world in a new directory within the /worlds/ directory. The name of the directory 
	 * is the level name. When there are multiple worlds with the same name they will be numbered.
	 * 
	 * @return The directory in which the world has been saved
	 * @throws IOException When file writing fails
	 */
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
		
		// Set sky light
		System.out.println("Adding sky light");
		addSkyLight();
		
		// Spread sky light
		System.out.print("Spreading sky light ");
		for(int i = DEFAULT_SKY_LIGHT; i > 1; i--) {
			spreadSkyLight((byte)i);
			System.out.print(".");
		}
		System.out.println();
		
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
	
	/**
	 * Adds the sky light. Starts from top the top of each column and sets sky light to full, up to 
	 * the first non-transparent block.
	 */
	private void addSkyLight() {
		for(Region region : regions.values()) {
			region.addSkyLight();
		}
	}
	
	private boolean dirExists(File f) {
		return(f.exists() && f.isDirectory());
	}
}
