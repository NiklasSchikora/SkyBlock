package net.MyCliff.SkyBlock.util;


import net.MyCliff.SkyBlock.manager.IslandManager;
import org.bukkit.Location;
import org.bukkit.block.Block;

/**
 * Created by Niklas on 19.11.2015.
 */
public class LevelUtil {



    public static void calculateLevel(String islandID) {
        Location loc = IslandManager.getIslandLocation(islandID);
        if(loc != null) {
            int px = loc.getBlockX();
            int py = loc.getBlockY();
            int pz = loc.getBlockZ();
            int levelPoints = 0;
            int radius = 200;
            for (int x = -radius; x <= radius; x++) {
                for (int y = 0; y <= 255; y++) {
                    for (int z = -radius; z <= radius; z++) {
                        Block b = loc.getWorld().getBlockAt(px + x,
                                py + y, pz + z);
                        switch(b.getType()) {
                            case DIAMOND_BLOCK:
                                levelPoints +=300;
                            case ENCHANTMENT_TABLE:
                            default: levelPoints++;
                        }



                    }
                }
            }
        }


    }



}
