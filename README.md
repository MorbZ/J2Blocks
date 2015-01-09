**A Java library for easy generation of Minecraft worlds**

J2Blocks aims to provide a simple library for custom map generation. In order to use this library it is not necessary to deal with things like regions, chunks and block data. However it aims to implement all map features that Minecraft offers. Please keep in mind that this is still beta.

Example
------
In this example we want to create a Minecraft world that is paved with melon-blocks. We want to create a huge structure of glass and want to set door on top of it. From the Example.java:

	// Create the base layers of the generated world.
	// We set the bottom layer of the world to be bedrock and the 20 layers above to be melon 
	// blocks.
	DefaultLayers layers = new DefaultLayers();
	layers.setLayer(0, Material.BEDROCK);
	layers.setLayers(1, 20, Material.MELON_BLOCK);

	// Create the internal Minecraft world generator.
	// We use a flat generator. We do this to make sure that the whole world will be paved 
	// with melons and not just the part we generated.
	IGenerator generator = new FlatGenerator(layers);

	// Create the level configuration.
	// We set the mode to creative creative mode and name our world. We also set the spawn point
	// in the middle of our glass structure.
	Level level = new Level("MelonWorld", generator);
	level.setGameType(GameType.CREATIVE);
	level.setSpawnPoint(50, 0, 50);

	// Now we create the world. This is where we can set our own blocks.
	World world = new World(level, layers);

	// Create a huge structure of glass that has an area of 100x100 blocks and is 50 blocks 
	// height. On top of the glass structure we put a layer of grass.
	for(int x = 0; x < 100; x++) {
		for(int z = 0; z < 100; z++) {
			// Set glass
			for(int y = 0; y < 50; y++) {
				world.setBlock(x, y, z, SimpleBlock.GLASS);
			}
		
			// Set grass
			world.setBlock(x, 50, z, SimpleBlock.GRASS);
		}
	}

	// Now we create the door. It consists of 2 blocks, that's why we can't use a SimpleBlock 
	// here.
	world.setBlock(50, 51, 50, DoorBlock.makeLower(DoorMaterial.OAK, Facing4State.EAST, false));
	world.setBlock(50, 52, 50, DoorBlock.makeUpper(DoorMaterial.OAK, HingeSide.LEFT));

	// Everything's set up so we're going to save the world.
	world.save();

The world has been saved to the /world/ directory in our execution directory. We can move it to the Minecraft /saves/ directory and enjoy all the melons.

Known Issues
------
- The Y-coordinate of the spawn position doesn't work correctly. The player will spawn in the air and fall at first
- The lightning isn't sophisticated yet. There are sometimes blocks that miss lightning and appear black
- Not all blocks are implemented yet. See in the Material.java the blocks that have a check mark  
**Feel free to help and fix the issues by submitting a pull request.**