package net.MyCliff.SkyBlock.manager;

import java.io.File;
import java.io.IOException;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.data.DataException;
import com.sk89q.worldedit.schematic.MCEditSchematicFormat;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class IslandManager {

	
	/*
	 * plugins/SkyBlock/Islands
	 * plugins/SkyBlock/players.yml
	 *
	 * 
	 */




    public void pasteIsland(Location loc) {
        File shematic = new File("plugins/SkyBlock", "Island.shematic");
        if(!shematic.exists()) {
            WorldEditPlugin wep = (WorldEditPlugin) Bukkit.getPluginManager().getPlugin("WorldEdit");
            EditSession session = wep.getWorldEdit().getEditSessionFactory().getEditSession(new BukkitWorld(loc.getWorld()), 1000000);
            try {
                MCEditSchematicFormat.getFormat(shematic).load(shematic).paste(session, new Vector(0, 200, 0), false);
            } catch (MaxChangedBlocksException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (DataException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("ERROR: shematic does not exist!");
        }
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
