package net.morbz.minecraft.blocks;

/**
 * @author Zeno
 */
public enum RedSandstoneBlock implements IBlock {

    RED_SANDSTONE( (byte) 0 ),
    CHISELED_RED_SANDSTONE( (byte) 1 ),
    SMOOTH_RED_SANDSTONE( (byte) 2 );

    private byte data;

    RedSandstoneBlock( byte data ) {
        this.data = data;
    }

    @Override
    public byte getBlockId() {
        return (byte) Material.RED_SANDSTONE.getValue();
    }

    @Override
    public byte getBlockData() {
        return this.data;
    }

    @Override
    public int getTransparency() {
        return (byte) Material.RED_SANDSTONE.getTransparency();
    }

}
