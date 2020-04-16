// 
// Decompiled by Procyon v0.5.30
// 

package pl.best241.ccstones;

import redis.clients.jedis.Jedis;
import org.bukkit.inventory.meta.ItemMeta;
import pl.best241.ccstones.listeners.ExplodeListener;
import pl.best241.ccstones.listeners.BlockPlaceListener;
import org.bukkit.plugin.Plugin;
import org.bukkit.event.Listener;
import pl.best241.ccstones.listeners.BlockBreakListener;
import pl.best241.rdbplugin.JedisFactory;
import org.bukkit.inventory.Recipe;
import java.util.List;
import java.util.ArrayList;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;
import org.bukkit.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;

public class CcStones extends JavaPlugin
{
    private static CcStones plugin;
    public static ShapedRecipe stonesRecipe;
    
    public void onEnable() {
        if (!this.getServer().getPluginManager().isPluginEnabled("rdbPlugin")) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "rdbPlugin not enabled! ccStones not started!");
            this.setEnabled(false);
        }
        ConfigManager.load(CcStones.plugin = this);
        final ItemStack stoneMakerItem = new ItemStack(Material.EMERALD_ORE);
        stoneMakerItem.addUnsafeEnchantment(Enchantment.ARROW_INFINITE, 10);
        final ItemMeta meta = stoneMakerItem.getItemMeta();
        meta.setDisplayName(ChatColor.WHITE + "STONIARKA " + ChatColor.GRAY + "-" + ChatColor.GOLD + " Automatyczny generator kamienia");
        final ArrayList<String> lore = new ArrayList<String>();
        lore.add(ChatColor.DARK_GREEN + "Aby go calkowicie zniszczyc musisz uzyc zlotego kilofa");
        meta.setLore((List)lore);
        stoneMakerItem.setItemMeta(meta);
        final ShapedRecipe stoneMaker = new ShapedRecipe(new ItemStack(stoneMakerItem));
        stoneMaker.shape(new String[] { " s ", "rpr", " e " });
        stoneMaker.setIngredient('s', Material.STONE);
        stoneMaker.setIngredient('r', Material.REDSTONE);
        stoneMaker.setIngredient('p', Material.PISTON_BASE);
        stoneMaker.setIngredient('e', Material.EMERALD);
        Bukkit.addRecipe((Recipe)(CcStones.stonesRecipe = stoneMaker));
        final Jedis jedis = JedisFactory.getInstance().getJedis();
        Redis.loadAllBlocks(jedis);
        JedisFactory.getInstance().returnJedis(jedis);
        this.getServer().getPluginManager().registerEvents((Listener)new BlockBreakListener(), (Plugin)this);
        this.getServer().getPluginManager().registerEvents((Listener)new BlockPlaceListener(), (Plugin)this);
        this.getServer().getPluginManager().registerEvents((Listener)new ExplodeListener(), (Plugin)this);
        ticker();
        BlockData.refreshBlocks();
    }
    
    public void onDisable() {
        final Jedis jedis = JedisFactory.getInstance().getJedis();
        Redis.removeAllBlocksNeededRemoving(jedis);
        Redis.saveAllBlocksNeededSaving(jedis);
        JedisFactory.getInstance().returnJedis(jedis);
    }
    
    public static CcStones getPlugin() {
        return CcStones.plugin;
    }
    
    public static void ticker() {
        final Jedis jedis;
        Bukkit.getScheduler().runTaskTimer((Plugin)getPlugin(), () -> {
            jedis = JedisFactory.getInstance().getJedis();
            Redis.removeAllBlocksNeededRemoving(jedis);
            Redis.saveAllBlocksNeededSaving(jedis);
            JedisFactory.getInstance().returnJedis(jedis);
        }, 600L, 600L);
    }
}
