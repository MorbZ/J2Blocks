package net.morbz.minecraft.blocks;

/**
 * @author verbuchselt
 */
public enum SaplingBlock implements IBlock {

    OAK_SAPLING( (byte) 0 ),
    SPRUCE_SAPLING( (byte) 1 ),
    BIRCH_SAPLING( (byte) 2 ),
    JUNGLE_SAPLING( (byte) 3 ),
    ACACIA_SAPLING( (byte) 4 ),
    DARK_OAK_SAPLING( (byte) 5 );

    private byte data;

    SaplingBlock( byte data ) {
        this.data = data;
    }

    @Override
    public byte getBlockId() {
        return (byte) Material.SAPLING.getValue();
    }

    @Override
    public byte getBlockData() {
        return this.data;
    }

    @Override
    public int getTransparency() {
        return (byte) Material.SAPLING.getTransparency();
    }

}
