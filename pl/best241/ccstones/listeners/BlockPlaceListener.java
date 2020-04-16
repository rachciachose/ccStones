// 
// Decompiled by Procyon v0.5.30
// 

package pl.best241.ccstones.listeners;

import org.bukkit.event.EventPriority;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Player;
import org.bukkit.block.Block;
import org.bukkit.Effect;
import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.Material;
import pl.best241.ccstones.BlockData;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.Listener;

public class BlockPlaceListener implements Listener
{
    @EventHandler(priority = EventPriority.HIGHEST)
    public static void blockPlaceListener(final BlockPlaceEvent event) {
        if (event.isCancelled()) {
            return;
        }
        final Block block = event.getBlock();
        final BlockData blockData = new BlockData(block);
        final Player player = event.getPlayer();
        final ItemStack itemInHand = event.getItemInHand();
        if (BlockData.containsBlock(blockData)) {
            event.setCancelled(true);
            return;
        }
        if (itemInHand.getType() == Material.EMERALD_ORE && itemInHand.getEnchantmentLevel(Enchantment.ARROW_INFINITE) == 10) {
            block.setType(Material.STONE);
            blockData.setNeedSave(true);
            player.sendMessage(ChatColor.DARK_GRAY + " »" + ChatColor.DARK_GREEN + "Polozyles generator kamienia!\n" + ChatColor.DARK_GRAY + " »" + ChatColor.DARK_GREEN + "Mozesz go zniszczyc uzywajac zlotego kilofu!");
            for (int i = 0; i < 9; ++i) {
                player.getWorld().playEffect(block.getLocation().add(Math.random(), 1.0 + Math.random(), Math.random()).add(-Math.random(), 0.0, -Math.random()).add(0.5, 0.0, 0.5), Effect.HAPPY_VILLAGER, 30, 30);
            }
        }
    }
}
