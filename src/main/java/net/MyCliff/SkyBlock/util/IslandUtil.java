package net.MyCliff.SkyBlock.util;

import net.MyCliff.SkyBlock.manager.IslandManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

/**
 * Created by Niklas on 03.12.2015.
 */
public class IslandUtil {




    public static String getBiom(String uuid) {
        File file = new File("plugins/SkyBlock/Islands", IslandManager.getIslandID(uuid) + ".yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        return cfg.getString("Island.Biome");
    }

    public static void setBiom(String uuid, String biomname) {
        File file = new File("plugins/SkyBlock/Islands", IslandManager.getIslandID(uuid) + ".yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        cfg.set("Island.Biom", biomname);
        try {
            cfg.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
