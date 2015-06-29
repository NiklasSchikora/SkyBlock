package net.MyCliff.SkyBlock.manager;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class IslandManager {

	
	/*
	 * plugins/SkyBlock/Islands
	 * plugins/SkyBlock/players.yml
	 *
	 * 
	 */




    public void pasteIsland() {

    }


    public static boolean hasIsland(String uuid) {
        File file = new File("plugins/SkyBlock", "players.yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        if(cfg.getString(uuid) != null) {
            return true;
        }
        return false;
    }




}
