// 
// Decompiled by Procyon v0.5.30
// 

package pl.best241.ccstones;

import java.io.File;

public class ConfigManager
{
    private static CcStones plugin;
    public static String sectorPart;
    public static int regenTime;
    
    public static void load(final CcStones ccstones) {
        ConfigManager.plugin = ccstones;
        final File fileConfig = new File(ConfigManager.plugin.getDataFolder(), "config.yml");
        if (!fileConfig.exists()) {
            System.out.println("config.yml not found! Generating!");
            ConfigManager.plugin.getConfig().options().copyDefaults(true);
            ConfigManager.plugin.saveConfig();
        }
        load();
    }
    
    private static void load() {
        ConfigManager.sectorPart = ConfigManager.plugin.getConfig().getString("sectorPart");
        ConfigManager.regenTime = ConfigManager.plugin.getConfig().getInt("regenTime");
    }
}
