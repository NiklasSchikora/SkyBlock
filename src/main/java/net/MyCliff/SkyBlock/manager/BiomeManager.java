package net.MyCliff.SkyBlock.manager;

import org.bukkit.Location;
import org.bukkit.block.Biome;

/**
 * Created by Niklas on 25.11.2015.
 */
public class BiomeManager {

    public static void changeBiom(String uuid, Biome bType) {
        Location loc = IslandManager.getIslandLocation(uuid);
        int px = loc.getBlockX();
        int pz = loc.getBlockZ();
        for (int x = -216; x <= 216; x += 16) {
            for (int z = -216; z <= 216; z += 16) {
                IslandManager.getSkyWorld().loadChunk((px + x) / 16,
                        (pz + z) / 16);
            }
        }
        for (int x = -200; x <= 200; x++) {
            for (int z = -200; z <= 200; z++) {
                IslandManager.getSkyWorld().setBiome(px + x, pz + z, bType);
            }
        }
        for (int x = -216; x <= 216; x += 16) {
            for (int z = -216; z <= 216; z += 16) {
                IslandManager.getSkyWorld().refreshChunk((px + x) / 16, (pz + z) / 16);
            }
        }
    }


}
