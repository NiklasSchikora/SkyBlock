package net.MyCliff.SkyBlock.listener;

import net.MyCliff.SkyBlock.Main;
import net.MyCliff.SkyBlock.manager.IslandManager;
import net.MyCliff.SkyBlock.util.Inventories;
import net.MyCliff.SkyBlock.util.Messages;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Niklas on 16.11.2015.
 */
public class PlayerManagementListener implements Listener {


    @EventHandler
    public void onInventory(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        if(e.getInventory() != null) {
            if(e.getCurrentItem() != null) {
                if (e.getInventory().getTitle().equalsIgnoreCase("§cSpielerverwaltung")) {
                    if (e.getCurrentItem() != null) {
                        if (e.getCurrentItem().getType().equals(Material.BARRIER)) {
                            p.openInventory(Inventories.islandMenue());
                            Main.editing.remove(p.getName());
                        } else if (e.getCurrentItem().getType().equals(Material.SIGN)) {
                            Inventory inv = Bukkit.createInventory(null, 9, "§cGruppenrwaltung");

                            List<String> lore = new ArrayList<String>();

                            ItemStack verwaltung = new ItemStack(Material.IRON_HELMET);
                            ItemMeta verwaltungMeta = verwaltung.getItemMeta();
                            verwaltungMeta.setDisplayName("§fGruppe: §6Verwalter");
                            lore.add("§fDie Gruppe §6Verwalter §fkann:");
                            lore.add("§7• §fMitglieder auf die Insel einladen");
                            lore.add("§7• §fDas Insel-Home verändern");
                            lore.add("§7• §fDen Insel-Warp setzen");
                            verwaltungMeta.setLore(lore);
                            lore.clear();
                            verwaltung.setItemMeta(verwaltungMeta);
                            inv.setItem(1, verwaltung);

                            ItemStack mitglied = new ItemStack(Material.LEATHER_HELMET);
                            ItemMeta mitgliedmeta = mitglied.getItemMeta();
                            mitgliedmeta.setDisplayName("§fGruppe: §9Mitglied");
                            lore.add("§fDies ist die Standart-Gruppe");
                            mitgliedmeta.setLore(lore);
                            lore.clear();
                            mitglied.setItemMeta(mitgliedmeta);
                            inv.setItem(0, mitglied);

                            ItemStack owner = new ItemStack(Material.GOLD_HELMET);
                            ItemMeta ownermeta = owner.getItemMeta();
                            ownermeta.setDisplayName("§fGruppe: §cBesitzer");
                            lore.add("§fDie Gruppe §cBesitzer");
                            lore.add("§fhat alle Rechte.");
                            lore.add("§c§lWICHTIG: §fEs kann nur einen");
                            lore.add("§fBesitzer geben.");
                            ownermeta.setLore(lore);
                            lore.clear();
                            owner.setItemMeta(ownermeta);
                            inv.setItem(2, owner);

                            ItemStack re = new ItemStack(Material.BARRIER);
                            ItemMeta remeta = re.getItemMeta();
                            remeta.setDisplayName("§cZurück zum Menü");
                            re.setItemMeta(remeta);
                            inv.setItem(8, re);

                            p.openInventory(inv);
                        } else if (e.getCurrentItem().getType().equals(Material.IRON_DOOR)) {
                            Inventory choice = Bukkit.createInventory(null, 9, "§cKick");

                            List<String> lore = new ArrayList<String>();

                            ItemStack green = new ItemStack(Material.WOOL, 1, (short) 5);
                            ItemMeta greenMeta = green.getItemMeta();
                            greenMeta.setDisplayName("§2BESTÄTIGEN");
                            green.setItemMeta(greenMeta);
                            choice.setItem(1, green);

                            ItemStack red = new ItemStack(Material.WOOL, 1, (short) 14);
                            ItemMeta redMeta = red.getItemMeta();
                            redMeta.setDisplayName("§cABBRECHEN");
                            red.setItemMeta(redMeta);
                            choice.setItem(7, red);

                            ItemStack description = new ItemStack(Material.PAPER);
                            ItemMeta desmeta = description.getItemMeta();
                            desmeta.setDisplayName("§f§lHinweis:");
                            lore.add("§7§l↳ §fBist du dir sicher");
                            lore.add("§fdas du den Spieler von deiner");
                            lore.add("§fInsel kicken möchtest?");
                            desmeta.setLore(lore);
                            lore.clear();
                            description.setItemMeta(desmeta);
                            choice.setItem(4, description);

                            p.openInventory(choice);
                        } else {
                            return;
                        }
                    }
                    e.setCancelled(true);
                } else if (e.getInventory().getTitle().equalsIgnoreCase("§cKick")) {
                    if (e.getCurrentItem() != null) {
                        if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§cABBRECHEN")) {
                            e.getView().close();
                            Main.editing.remove(p.getName());
                        } else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§2BESTÄTIGEN")) {
                            String name = Main.editing.get(p.getName());
                            if(name != null) {
                                File kicked = new File("plugins/SkyBlock", "kicked.yml");
                                FileConfiguration kickedConf = YamlConfiguration.loadConfiguration(kicked);
                                IslandManager.leaveIsland(IslandManager.getIslandID(p.getUniqueId().toString()), name);
                                Player target = Bukkit.getPlayerExact(name);
                                p.sendMessage(Messages.prefix + " Du hast den Spieler " + name + " von deiner Insel entfernt!");
                                if(target != null) {
                                    target.teleport(target.getWorld().getSpawnLocation());
                                    target.getInventory().clear();
                                    target.getEnderChest().clear();
                                    target.sendMessage(Messages.prefix + " Du wurdest von der Insel gekickt!");
                                } else {
                                    List<String> kickedL = kickedConf.getStringList("Kicked.Join");
                                    kickedL.add(name);
                                    kickedConf.set("Kicked.Join", kickedL);
                                    try {
                                        kickedConf.save(kicked);
                                    } catch (IOException e1) {
                                        e1.printStackTrace();
                                    }
                                }
                            }
                            Main.editing.remove(p.getName());
                            e.getView().close();
                        }
                    }


                    e.setCancelled(true);
                } else if (e.getInventory().getTitle().equalsIgnoreCase("§cGruppenrwaltung")) {
                    File isfile = new File("plugins/SkyBlock/Islands", IslandManager.getIslandID(p.getUniqueId().toString()) + ".yml");
                    FileConfiguration iscfg = YamlConfiguration.loadConfiguration(isfile);
                    List<String> members = iscfg.getStringList("Island.Rank.Members");
                    List<String> verwalter = iscfg.getStringList("Island.Rank.Verwalter");
                    if (e.getCurrentItem().getType().equals(Material.LEATHER_HELMET)) {
                        String name = Main.editing.get(p.getName());
                        if (!members.contains(name)) {
                            members.add(name);
                            verwalter.remove(name);
                            p.sendMessage(Messages.prefix + " Du hast den Spieler " + name + " in die Gruppe Mitglied gesetzt!");
                            Player ifon = Bukkit.getPlayerExact(name);
                            if (ifon != null) {
                                ifon.sendMessage(Messages.prefix + " Du wurdest in die Gruppe Mitglied gesetzt!");
                            }
                            iscfg.set("Island.Rank.Members", members);
                            iscfg.set("Island.Rank.Verwalter", verwalter);
                            try {
                                iscfg.save(isfile);
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        } else {
                            p.sendMessage(Messages.prefix + " Der Spieler " + name + " ist bereits in der Gruppe Mitglied!");
                        }
                        e.getView().close();
                    } else if (e.getCurrentItem().getType().equals(Material.IRON_HELMET)) {
                        String name = Main.editing.get(p.getName());
                        if (!verwalter.contains(name)) {
                            members.remove(name);
                            verwalter.add(name);
                            p.sendMessage(Messages.prefix + " Du hast den Spieler " + name + " in die Gruppe Verwalter gesetzt!");
                            Player ifon = Bukkit.getPlayerExact(name);
                            if (ifon != null) {
                                ifon.sendMessage(Messages.prefix + " Du wurdest in die Gruppe Verwalter gesetzt!");
                            }
                            iscfg.set("Island.Rank.Members", members);
                            iscfg.set("Island.Rank.Verwalter", verwalter);
                            try {
                                iscfg.save(isfile);
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        } else {
                            p.sendMessage(Messages.prefix + " Der Spieler " + name + " ist bereits in der Gruppe Mitglied!");
                        }
                        e.getView().close();
                    } else if (e.getCurrentItem().getType().equals(Material.GOLD_HELMET)) {

                    }
                    e.setCancelled(true);
                }
            }


        }
    }

}
