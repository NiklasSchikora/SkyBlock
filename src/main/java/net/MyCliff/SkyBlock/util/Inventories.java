package net.MyCliff.SkyBlock.util;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;

public class Inventories {


    public static Inventory Island(Player p) {
        Inventory inv = Bukkit.createInventory(null, 9, "§f§lSkyBlock §7§l»");
        ItemStack glass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 7);
        ItemMeta glassItemMeta = glass.getItemMeta();
        glassItemMeta.setDisplayName(" ");
        glass.setItemMeta(glassItemMeta);
        for (int i = 0; i < 9; i++) {
            inv.setItem(i, glass);
        }
            //Informationen zum Party-System
            ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
            SkullMeta skullmeta = (SkullMeta) skull.getItemMeta();
            skullmeta.setDisplayName("§9§lSpiele mit Freunden!");
            skullmeta.setOwner(p.getName());
            List<String> skulllore = new ArrayList<String>();
            skulllore.add("§f§l» §fKlicke hier, um mehr Informationen über");
            skulllore.add("§fdas Party-System zu bekommen.");
            skullmeta.setLore(skulllore);
            skull.setItemMeta(skullmeta);
            inv.setItem(1, skull);
            //Mehr Informationen
            ItemStack paper = new ItemStack(Material.PAPER, 1);
            ItemMeta papermeta = paper.getItemMeta();
            papermeta.setDisplayName("§7§lWas ist SkyBlock?");
            List<String> paperlore = new ArrayList<String>();
            paperlore.add("§f§l» §fKlicke hier, um mehr Informationen über");
            paperlore.add("§fden Spielmodus §7§oSkyBlock §fzu bekommen.");
            papermeta.setLore(paperlore);
            paper.setItemMeta(papermeta);
            inv.setItem(7, paper);
            //Eigene Insel
            ItemStack grass = new ItemStack(Material.GRASS, 1);
            ItemMeta grassmeta = grass.getItemMeta();
            grassmeta.setDisplayName("§2§lStarte dein Abenteuer!");
            List<String> grasslore = new ArrayList<String>();
            grasslore.add("§f§l» §fKlicke hier, um deine eigene Insel");
            grasslore.add("§fzu erstellen..");
            grasslore.add(" ");
            grasslore.add("§fDu kannst natürlich auch später noch Spieler");
            grasslore.add("§fauf deine Insel einladen.");
            grassmeta.setLore(grasslore);
            grass.setItemMeta(grassmeta);
            inv.setItem(4, grass);
        return inv;
    }


    public static Inventory islandMenue() {
        Inventory inv = Bukkit.createInventory(null, 18, "Insel-Menü");
        ItemStack item;
        ItemMeta meta;
        List<String> lore = new ArrayList<String>();
        //1 Item - Nach Hause
        item = new ItemStack(Material.BED);
        meta = item.getItemMeta();
        meta.setDisplayName("§eNach Hause");
        lore.add("§f§l> §fWenn du auf diesen Gegenstand klickst,");
        lore.add("§fkommst du zurück auf deine Insel.");
        meta.setLore(lore);
        lore.clear();
        item.setItemMeta(meta);
        inv.setItem(0, item);
        //2 Item - Zum Shop
        item = new ItemStack(Material.CHEST);
        meta = item.getItemMeta();
        meta.setDisplayName("§eZum Shop");
        lore.add("§f§l> §fWenn du auf diesen Gegenstand klickst,");
        lore.add("§fkommst du zum SkyBlock-Shop.");
        meta.setLore(lore);
        lore.clear();
        item.setItemMeta(meta);
        inv.setItem(1, item);
        //3 Item - Mitspieler
        item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        meta = item.getItemMeta();
        meta.setDisplayName("§eMitspieler");
        lore.add("§f§l> §fWenn du auf diesen Gegenstand klickst,");
        lore.add("§fbekommst du all deine Mitspieler angezeigt.");
        meta.setLore(lore);
        lore.clear();
        item.setItemMeta(meta);
        inv.setItem(2, item);
        //4 Item - Challenges
        item = new ItemStack(Material.BOOK);
        meta = item.getItemMeta();
        meta.setDisplayName("§eChallenges");
        lore.add("§f§l> §fWenn du auf diesen Gegenstand klickst,");
        lore.add("§fbekommst du alle möglichen Challenges aufgelistet.");
        meta.setLore(lore);
        lore.clear();
        item.setItemMeta(meta);
        inv.setItem(3, item);
        //5 Item - Biome
        item = new ItemStack(Material.MAP);
        meta = item.getItemMeta();
        meta.setDisplayName("§eBiome");
        lore.add("§f§l> §fWenn du auf diesen Gegenstand klickst,");
        lore.add("§fkommst du zu einer Auswahl von Biomen,");
        lore.add("§fdie du für deine Insel definieren kannst.");
        meta.setLore(lore);
        lore.clear();
        item.setItemMeta(meta);
        inv.setItem(4, item);

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




}
