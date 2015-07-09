package net.MyCliff.SkyBlock.villagershop;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.CreatureType;
import org.bukkit.entity.Villager;

/**
 * Created by Niklas on 09.07.2015.
 */
public class Villagers {


    public static void spawnTestVillager(String name, Location loc) {
        Villager villager = (Villager) loc.getWorld().spawnCreature(loc, CreatureType.VILLAGER);
        villager.setCustomName("Test");
        villager.setCustomNameVisible(true);
        villager.setAdult();
    }



}
