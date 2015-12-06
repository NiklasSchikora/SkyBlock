package net.MyCliff.SkyBlock.util;

import net.MyCliff.SkyBlock.level.LevelUtil;
import net.MyCliff.SkyBlock.manager.IslandManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Niklas on 03.12.2015.
 */
public class BiomManager {


    public static Inventory biomechange(Player p) {
        Inventory inv = Bukkit.createInventory(null, 27, "§f§lBiome §c§l» §f§lAuswahl");
        ItemStack glass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 7);
        ItemMeta glassItemMeta = glass.getItemMeta();
        int level = LevelUtil.getLevel(IslandManager.getIslandID(p.getUniqueId().toString()));
        glassItemMeta.setDisplayName(" ");
        glass.setItemMeta(glassItemMeta);
        for (int i = 0; i < 27; i++) {
            inv.setItem(i, glass);
            List<String> lore = new ArrayList<String>();
            String selectedBiome = getBiome(p);

            //Item 1
            if(selectedBiome.equalsIgnoreCase(Biome.FOREST.toString())){
                lore.add("§fDieses Biom ist im Moment");
                lore.add("§fvon dir ausgewählt.");
            } else {
            lore.add("§fKlicke hier, um dieses");
            lore.add("§fBiom auszuwählen.");
            }
            inv.setItem(10, getItemStack(Material.SAPLING, 1, (byte) 0, "§2§lStandard Biom", lore));

            lore.clear();

            if(selectedBiome.equalsIgnoreCase(Biome.ICE_MOUNTAINS.toString())){
                lore.add("§fDieses Biom ist im Moment");
                lore.add("§fvon dir ausgewählt.");
            } else {
            lore.add("§fKlicke hier, um dieses");
            lore.add("§fBiom auszuwählen.");
            }
            inv.setItem(11, getItemStack(Material.SAPLING, 1, (byte) 1, "§b§lWinter Biom", lore));

            lore.clear();

            if(selectedBiome.equalsIgnoreCase(Biome.JUNGLE.toString())){
                lore.add("§fDieses Biom ist im Moment");
                lore.add("§fvon dir ausgewählt.");
            } else {
            if(level >= 250) {
                lore.add("§fKlicke hier, um dieses");
                lore.add("§fBiom auszuwählen.");
                lore.add("");
                lore.add("§f§o§lHäufiges Auftreten:");
                lore.add("§7• §fOzelot");
            } else {
                lore.add("§fKlicke hier, um dieses");
                lore.add("§fBiom auszuwählen.");
                lore.add("");
                lore.add("§f§o§lHäufiges Auftreten:");
                lore.add("§7• §fOzelot");
                lore.add("");
                lore.add("§c§o§lBenötigtes Level:");
                lore.add("§7• §f250");
            }
            }
            inv.setItem(13, getItemStack(Material.VINE, 1, (byte) 0, "§a§lDschungel Biom", lore));

            lore.clear();

            if(selectedBiome.equalsIgnoreCase(Biome.TAIGA.toString())){
                lore.add("§fDieses Biom ist im Moment");
                lore.add("§fvon dir ausgewählt.");
            } else {
            if(level >= 750) {
                lore.add("§fKlicke hier, um dieses");
                lore.add("§fBiom auszuwählen.");
                lore.add("");
                lore.add("§f§o§lHäufiges Auftreten:");
                lore.add("§7• §fWölfe");
            } else {
                lore.add("§fKlicke hier, um dieses");
                lore.add("§fBiom auszuwählen.");
                lore.add("");
                lore.add("§f§o§lHäufiges Auftreten:");
                lore.add("§7• §fWölfe");
                lore.add("");
                lore.add("§c§o§lBenötigtes Level:");
                lore.add("§7• §f750");
            }
            }
            inv.setItem(14, getItemStack(Material.DIRT, 1, (byte) 2, "§6§lTaiga Biom", lore));

            lore.clear();

            if(selectedBiome.equalsIgnoreCase(Biome.SAVANNA.toString())){
                lore.add("§fDieses Biom ist im Moment");
                lore.add("§fvon dir ausgewählt.");
            } else {
            if(level >= 1500) {
                lore.add("§fKlicke hier, um dieses");
                lore.add("§fBiom auszuwählen.");
                lore.add("");
                lore.add("§f§o§lHäufiges Auftreten:");
                lore.add("§7• §fPferde");
            } else {
                lore.add("§fKlicke hier, um dieses");
                lore.add("§fBiom auszuwählen.");
                lore.add("");
                lore.add("§f§o§lHäufiges Auftreten:");
                lore.add("§7• §fPferde");
                lore.add("");
                lore.add("§c§o§lBenötigtes Level:");
                lore.add("§7• §f1500");
            }
            }
            inv.setItem(15, getItemStack(Material.SAPLING, 1, (byte) 4, "§6§lSavannen Biom", lore));

            lore.clear();

            if(selectedBiome.equalsIgnoreCase(Biome.SWAMPLAND.toString())){
                lore.add("§fDieses Biom ist im Moment");
                lore.add("§fvon dir ausgewählt.");
            } else {
            if(level >= 2500) {
                lore.add("§fKlicke hier, um dieses");
                lore.add("§fBiom auszuwählen.");
                lore.add("");
                lore.add("§f§o§lHäufiges Auftreten:");
                lore.add("§7• §fSchleim");
                lore.add("§7• §fHexen");
            } else {
                lore.add("§fKlicke hier, um dieses");
                lore.add("§fBiom auszuwählen.");
                lore.add("");
                lore.add("§f§o§lHäufiges Auftreten:");
                lore.add("§7• §fSchleim");
                lore.add("§7• §fHexen");
                lore.add("");
                lore.add("§c§o§lBenötigtes Level:");
                lore.add("§7• §f2500");
            }
            }
            inv.setItem(16, getItemStack(Material.WATER_LILY, 1, (byte) 0, "§2§lSumpf Biom", lore));


        }
        return inv;
    }

    public static ItemStack getItemStack(Material material, int amount, byte damage, String name, List<String> lore) {
        ItemStack item = new ItemStack(material, amount, damage);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    public static void updateBiome(Player p, Biome biome) {
        File file = new File("plugins/SkyBlock/Islands", IslandManager.getIslandID(p.getUniqueId().toString()) + ".yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        cfg.set("Island.Biome", biome.toString());
        try {
            cfg.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getBiome(Player p) {
        File file = new File("plugins/SkyBlock/Islands", IslandManager.getIslandID(p.getUniqueId().toString()) + ".yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        return cfg.getString("Island.Biome");
    }



}
