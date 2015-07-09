package net.MyCliff.SkyBlock.util;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Inventories {


    public static Inventory startInv() {
        Inventory inv = Bukkit.createInventory(null, 27, "§eSkyBlock");
        ItemStack glass = new ItemStack(Material.STAINED_GLASS_PANE,1 ,(short) 15);
        ItemMeta glassItemMeta = glass.getItemMeta();
        glassItemMeta.setDisplayName(" ");
        glass.setItemMeta(glassItemMeta);
        for(int i = 0; i< 27; i++) {
            inv.setItem(i, glass);
        }
        ItemStack item;
        ItemMeta meta;
        List<String> lore = new ArrayList<String>();
        item = new ItemStack(Material.BOOK);
        meta = item.getItemMeta();
        meta.setDisplayName("§eWas ist SkyBlock?");
        lore.add("§f§l> §fHier erfährst du, um was es in SkyBlock geht");
        lore.add("§fund was deine Ziele sind.");
        meta.setLore(lore);
        lore.clear();
        item.setItemMeta(meta);
        inv.setItem(10, item);

        item = new ItemStack(Material.SAPLING);
        meta = item.getItemMeta();
        meta.setDisplayName("§eStarte dein eigenes Abenteuer!");
        lore.add("§f§l> §fErstelle deine eigene Insel und");
        lore.add("§fstelle dich dem Abenteuer!");
        meta.setLore(lore);
        lore.clear();
        item.setItemMeta(meta);
        inv.setItem(13, item);

        item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        meta = item.getItemMeta();
        meta.setDisplayName("§eSpiele mit Freunden!");
        lore.add("§f§l> §fKlicke auf dieses Item,");
        lore.add("§fum mehr Informationen zu bekommen.");
        meta.setLore(lore);
        lore.clear();
        item.setItemMeta(meta);
        inv.setItem(16, item);

        return inv;
    }


    public static Inventory islandMenue(Player p) {
        Inventory inv = Bukkit.createInventory(null, 18, p.getName() + "'s Insel");
        ItemStack item;
        ItemMeta meta;
        List<String> lore = new ArrayList<String>();
        item = new ItemStack(Material.BED);
        meta = item.getItemMeta();
        meta.setDisplayName("§eNach Hause");
        lore.add("§f§l> §fWenn du auf diesen Gegenstand klickst,");
        lore.add("§fkommst du zurück auf deine Insel.");
        meta.setLore(lore);
        lore.clear();
        item.setItemMeta(meta);
        inv.setItem(0, item);






        return inv;
    }



}
