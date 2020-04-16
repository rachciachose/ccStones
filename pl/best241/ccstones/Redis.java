// 
// Decompiled by Procyon v0.5.30
// 

package pl.best241.ccstones;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import redis.clients.jedis.Jedis;

public class Redis
{
    public static void loadAllBlocks(final Jedis jedis) {
        final Set<String> lrange = (Set<String>)jedis.smembers("ccStones." + ConfigManager.sectorPart);
        for (final String serializedData : lrange) {
            BlockData.blocks.add(BlockData.parseBlockData(serializedData).getBlock());
        }
    }
    
    public static void saveAllBlocksNeededSaving(final Jedis jedis) {
        final ArrayList<BlockData> toRemove = new ArrayList<BlockData>();
        for (final BlockData block : BlockData.blocksNeedSave) {
            if (block.needSave()) {
                jedis.sadd("ccStones." + ConfigManager.sectorPart, new String[] { block.valueOfBlockData() });
                toRemove.add(block);
            }
        }
        for (final BlockData block : toRemove) {
            block.setNeedSave(false);
        }
    }
    
    public static void removeAllBlocksNeededRemoving(final Jedis jedis) {
        for (final BlockData block : BlockData.blocksToRemove) {
            jedis.srem("ccStones." + ConfigManager.sectorPart, new String[] { block.valueOfBlockData() });
        }
    }
}
