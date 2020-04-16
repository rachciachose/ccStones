// 
// Decompiled by Procyon v0.5.30
// 

package pl.best241.ccstones.listeners;

import org.bukkit.event.EventHandler;
import java.util.Iterator;
import java.util.List;
import pl.best241.ccstones.BlockData;
import org.bukkit.block.Block;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.Listener;

public class ExplodeListener implements Listener
{
    @EventHandler
    public static void explodeListener(final EntityExplodeEvent event) {
        if (event.isCancelled()) {
            return;
        }
        final List<Block> blockList = (List<Block>)event.blockList();
        for (final Block block : blockList) {
            final BlockData currentBlock = new BlockData(block);
            if (BlockData.containsBlock(currentBlock)) {
                currentBlock.remove();
            }
        }
    }
}
