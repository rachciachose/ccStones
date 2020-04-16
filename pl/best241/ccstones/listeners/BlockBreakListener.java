// 
// Decompiled by Procyon v0.5.30
// 

package pl.best241.ccstones.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.entity.Player;
import org.bukkit.block.Block;
import org.bukkit.plugin.Plugin;
import pl.best241.ccstones.ConfigManager;
import pl.best241.ccstones.CcStones;
import org.bukkit.Bukkit;
import java.util.List;
import java.util.ArrayList;
import org.bukkit.inventory.ItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.Effect;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import pl.best241.ccstones.BlockData;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.Listener;

public class BlockBreakListener implements Listener
{
    @EventHandler
    public static void blockBreakListener(final BlockBreakEvent event) {
        if (event.isCancelled()) {
            return;
        }
        final Block block = event.getBlock();
        final BlockData blockData = new BlockData(block);
        final Player player = event.getPlayer();
        if (BlockData.containsBlock(blockData)) {
            if (player.getItemInHand().getType() == Material.GOLD_PICKAXE) {
                blockData.remove();
                player.sendMessage(ChatColor.DARK_GRAY + " »" + ChatColor.DARK_GREEN + "Zniszczyles generator kamienia!");
                for (int i = 0; i < 9; ++i) {
                    player.getWorld().playEffect(block.getLocation().add(Math.random(), 1.0 + Math.random(), Math.random()).add(-Math.random(), 0.0, -Math.random()).add(0.5, 0.0, 0.5), Effect.MAGIC_CRIT, 10, 10);
                }
                if (player.getItemInHand().getEnchantmentLevel(Enchantment.SILK_TOUCH) == 1) {
                    final ItemStack stoneMakerItem = new ItemStack(Material.EMERALD_ORE);
                    stoneMakerItem.addUnsafeEnchantment(Enchantment.ARROW_INFINITE, 10);
                    final ItemMeta meta = stoneMakerItem.getItemMeta();
                    final ArrayList<String> lore = new ArrayList<String>();
                    lore.add(ChatColor.GOLD + "Automatyczny generator kamienia");
                    lore.add(ChatColor.DARK_GREEN + "Aby go calkowicie zniszczyc musisz uzyc zlotego kilofa");
                    meta.setLore((List)lore);
                    stoneMakerItem.setItemMeta(meta);
                    player.getInventory().addItem(new ItemStack[] { stoneMakerItem });
                }
            }
            else {
                Bukkit.getScheduler().runTaskLater((Plugin)CcStones.getPlugin(), () -> block.setType(Material.STONE), (long)ConfigManager.regenTime);
            }
        }
    }
}
