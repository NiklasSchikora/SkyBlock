package net.MyCliff.SkyBlock.level;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

/**
 * Created by Niklas on 03.12.2015.
 */

public class LevelTimes {



    /*
        File file = new File("plugins/SkyBlock", "times.yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
     */


    public static long getTime(String islandID) {
        File file = new File("plugins/SkyBlock", "times.yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        return cfg.getLong(islandID);
    }


}
