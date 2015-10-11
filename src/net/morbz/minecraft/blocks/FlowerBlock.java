package net.morbz.minecraft.blocks;

/**
 * @author verbuchselt
 */
public enum FlowerBlock implements IBlock {

    DANDELION( Material.YELLOW_FLOWER, (byte) 0 ),
    POPPY( Material.RED_FLOWER, (byte) 0 ),
    BLUE_ORCHID( Material.RED_FLOWER, (byte) 1 ),
    ALLIUM( Material.RED_FLOWER, (byte) 2 ),
    AZURE_BLUET( Material.RED_FLOWER, (byte) 3 ),
    RED_TULIP( Material.RED_FLOWER, (byte) 4 ),
    ORANGE_TULIP( Material.RED_FLOWER, (byte) 5 ),
    WHITE_TULIP( Material.RED_FLOWER, (byte) 6 ),
    PINK_TULIP( Material.RED_FLOWER, (byte) 7 ),
    OXEYE_DAISY( Material.RED_FLOWER, (byte) 8 );

    private Material material;
    private byte data;

    FlowerBlock( Material material, byte data ) {
        this.material = material;
        this.data = data;
    }

    @Override
    public byte getBlockId() {
        return (byte) this.material.getValue();
    }

    @Override
    public byte getBlockData() {
        return this.data;
    }

    @Override
    public int getTransparency() {
        return (byte) this.material.getTransparency();
    }

}
