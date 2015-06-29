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
        Inventory inv = Bukkit.createInventory(null, 9, "");
        ItemStack item;
        ItemMeta meta;
        List<String> lore = new ArrayList<String>();
        item = new ItemStack(Material.BOOK);
        meta = item.getItemMeta();
        meta.setDisplayName("§eWas ist SkyBlock?");
        lore.add("§f§l> §fHier erfährst du, um was es in SkyBlock geht");
        lore.add("und was deine Ziele sind.");
        meta.setLore(lore);
        lore.clear();
        item.setItemMeta(meta);
        inv.setItem(0, item);

        item = new ItemStack(Material.SAPLING);
        meta = item.getItemMeta();
        meta.setDisplayName("§eStarte dein eigenes Abenteuer!");
        lore.add("§f§l> §fErstelle deine eigene Insel und");
        lore.add("stelle dich dem Abenteuer!");
        meta.setLore(lore);
        lore.clear();
        item.setItemMeta(meta);
        inv.setItem(4, item);

        item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        meta = item.getItemMeta();
        meta.setDisplayName("§eSpiele mit Freunden!");
        lore.add("§f§l> §fKlicke auf dieses Item,");
        lore.add("um mehr Informationen zu bekommen.");
        meta.setLore(lore);
        lore.clear();
        item.setItemMeta(meta);
        inv.setItem(8, item);

        return inv;
    }


    public static Inventory islandMenue(Player p) {
        Inventory inv = Bukkit.createInventory(null, 18, p.getName() + "'s Insel");
        ItemStack item;
        ItemMeta meta;
        List<String> lore = new ArrayList<String>();







        return inv;
    }



}
