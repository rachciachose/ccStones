// 
// Decompiled by Procyon v0.5.30
// 

package pl.best241.ccstones;

import java.util.Iterator;
import org.bukkit.Material;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.block.Block;
import java.util.HashSet;

public class BlockData
{
    public static HashSet<Block> blocks;
    protected static HashSet<BlockData> blocksToRemove;
    protected static HashSet<BlockData> blocksNeedSave;
    private World world;
    private int x;
    private int y;
    private int z;
    private Block block;
    
    public BlockData(final World world, final int x, final int y, final int z) {
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        this.block = world.getBlockAt(x, y, z);
    }
    
    public BlockData(final Block block) {
        this.world = block.getWorld();
        this.x = block.getX();
        this.y = block.getY();
        this.z = block.getZ();
        this.block = block;
    }
    
    public boolean needSave() {
        return BlockData.blocksNeedSave.contains(this);
    }
    
    public void remove() {
        BlockData.blocks.remove(this.block);
        BlockData.blocksToRemove.add(this);
        BlockData.blocksNeedSave.remove(this);
    }
    
    public void setNeedSave(final boolean b) {
        if (b) {
            BlockData.blocksNeedSave.add(this);
            if (!BlockData.blocks.contains(this.block)) {
                BlockData.blocks.add(this.block);
            }
        }
        else {
            BlockData.blocksNeedSave.remove(this);
        }
    }
    
    public boolean equals(final BlockData anotherBlock) {
        return this.x == anotherBlock.getX() && this.y == anotherBlock.getY() && this.z == anotherBlock.getZ() && this.world.getName().equals(anotherBlock.getWorld().getName());
    }
    
    public World getWorld() {
        return this.world;
    }
    
    public int getX() {
        return this.x;
    }
    
    public int getY() {
        return this.y;
    }
    
    public int getZ() {
        return this.z;
    }
    
    public Block getBlock() {
        return this.block;
    }
    
    public static BlockData parseBlockData(final String serializedData) {
        final String[] args = serializedData.split(";");
        final World world = Bukkit.getWorld(args[0]);
        final int x = Integer.parseInt(args[1]);
        final int y = Integer.parseInt(args[2]);
        final int z = Integer.parseInt(args[3]);
        return new BlockData(world, x, y, z);
    }
    
    public static String valueOfBlockData(final BlockData data) {
        return data.getWorld().getName() + ";" + data.getX() + ";" + data.getY() + ";" + data.getZ();
    }
    
    public String valueOfBlockData() {
        return valueOfBlockData(this);
    }
    
    public static boolean containsBlock(final BlockData block) {
        return BlockData.blocks.contains(block.block);
    }
    
    public static void refreshBlocks() {
        for (final Block block : BlockData.blocks) {
            block.setType(Material.STONE);
        }
    }
    
    static {
        BlockData.blocks = new HashSet<Block>();
        BlockData.blocksToRemove = new HashSet<BlockData>();
        BlockData.blocksNeedSave = new HashSet<BlockData>();
    }
}
