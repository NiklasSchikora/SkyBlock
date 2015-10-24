package net.MyCliff.SkyBlock.listener;

import com.sk89q.worldguard.protection.flags.InvalidFlagFormat;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
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
import org.bukkit.inventory.meta.SkullMeta;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Niklas on 29.06.2015.
 */
public class InventoryClickManager implements Listener {

    private HashMap<String, String> editing = new HashMap<String, String>();


    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        if(e.getInventory().getTitle().equalsIgnoreCase("§eSkyBlock")) {
            if(e.getCurrentItem().getType().equals(Material.SAPLING)) {
                if(!IslandManager.hasIsland(p.getUniqueId().toString())) {
                    IslandManager.pasteIsland(IslandManager.getNextIsland());
                    IslandManager.addIslandConfiguration(p.getUniqueId().toString(), p.getName());
                    IslandManager.updatePlayers(p.getUniqueId().toString());
                    IslandManager.setChest(IslandManager.getIslandLocation(p.getUniqueId().toString()), p);
                    try {
                        IslandManager.protectIsland(IslandManager.getIslandLocation(p.getUniqueId().toString()), p);
                    } catch (ProtectedRegion.CircularInheritanceException e1) {
                        e1.printStackTrace();
                    } catch (InvalidFlagFormat invalidFlagFormat) {
                        invalidFlagFormat.printStackTrace();
                    }
                    IslandManager.updateNextIs();
                    p.teleport(IslandManager.getIslandLocation(p.getUniqueId().toString()));
                }
                e.setCancelled(true);
            } else if(e.getCurrentItem().getType().equals(Material.BOOK)) {
                Bukkit.dispatchCommand(p, "tutorial");
                e.setCancelled(true);
            } else if(e.getSlot() == 8) {
                e.setCancelled(true);
                /*
                Message Dialog
                 */
            }
        } else if(e.getInventory().getTitle().equalsIgnoreCase("Insel-Menü")) {
            if (e.getCurrentItem() != null) {
                if (e.getCurrentItem().getType().equals(Material.BED)) {
                    Bukkit.dispatchCommand(p, "is home");
                } else if (e.getSlot() == 2) {
                    Inventory inv = Bukkit.createInventory(null, 9, "§cMember deiner Insel:");
                    ItemStack stack = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
                    SkullMeta meta = (SkullMeta) stack.getItemMeta();
                    File isfile = new File("plugins/SkyBlock/Islands", IslandManager.getIsland(p.getUniqueId().toString()) + ".yml");
                    FileConfiguration iscfg = YamlConfiguration.loadConfiguration(isfile);
                    String owner = iscfg.getString("Island.Ownername");
                    List<String> members = iscfg.getStringList("Island.Membername");
                    members.add(owner);
                    if (members.size() != 0) {
                        for (int i = 0; i < members.size(); i++) {
                            Player Splayer = Bukkit.getPlayerExact(members.get(i));
                            List<String> lore = new ArrayList<String>();
                            List<String> verwalter = iscfg.getStringList("Island.Rank.Verwalter");
                            if (members.get(i).equalsIgnoreCase(iscfg.getString("Island.Ownername"))) {
                                lore.add("§7§l↳ §6Besitzer");
                            } else if (verwalter.contains(members.get(i))) {
                                lore.add("§7§l↳ §5Verwalter");
                            } else {
                                lore.add("§7§l↳ §fMitglied");
                            }
                            if (Splayer != null) {
                                lore.add("§7§l↳ §2Online");
                            } else {
                                lore.add("§7§l↳ §cOffline");
                            }
                            meta.setOwner(members.get(i));
                            meta.setLore(lore);
                            meta.setDisplayName(members.get(i));
                            stack.setItemMeta(meta);
                            inv.addItem(stack);
                        }
                    } else {
                        ItemStack nomembers = new ItemStack(Material.SKULL_ITEM);
                        ItemMeta nometa = nomembers.getItemMeta();
                        nometa.setDisplayName("§9Du hast keine Mitglieder auf deiner Insel.");
                        nomembers.setItemMeta(nometa);
                        inv.addItem(nomembers);
                    }
                    ItemStack re = new ItemStack(Material.BARRIER);
                    ItemMeta remeta = re.getItemMeta();
                    remeta.setDisplayName("§cZurück zum Menü");
                    re.setItemMeta(remeta);
                    inv.setItem(8, re);
                    p.openInventory(inv);


                } else {
                    return;
                }
            }

            e.setCancelled(true);
        } else if(e.getInventory().getTitle().equalsIgnoreCase("§cMember deiner Insel:")) {
            if(e.getCurrentItem().getType().equals(Material.BARRIER)) {
                p.openInventory(Inventories.islandMenue(p));
            } else if(!e.getCurrentItem().equals(Material.BARRIER)) {
                File isfile = new File("plugins/SkyBlock/Islands", IslandManager.getIsland(p.getUniqueId().toString()) + ".yml");
                FileConfiguration iscfg = YamlConfiguration.loadConfiguration(isfile);
                String owner = iscfg.getString("Island.Ownername");
                //if(p.getName().equals(owner)) {
                    ItemStack head = e.getCurrentItem();
                    if (!head.getItemMeta().getDisplayName().equalsIgnoreCase("§9Du hast keine Mitglieder auf deiner Insel.")) {
                        Inventory inv = Bukkit.createInventory(null, 9, "§cSpielerverwaltung");
                        String name = head.getItemMeta().getDisplayName();

                        ItemStack item = null;
                        ItemMeta imeta = null;
                        List<String> lore = new ArrayList<String>();

                        item = new ItemStack(Material.SIGN);
                        imeta = item.getItemMeta();
                        imeta.setDisplayName("§cRangverwaltung");
                        lore.add("§7§l↳ §fVerwalte den Rang von " + name);
                        imeta.setLore(lore);
                        lore.clear();
                        item.setItemMeta(imeta);
                        inv.setItem(1, item);

                        ItemStack door = new ItemStack(Material.IRON_DOOR);
                        ItemMeta doormeta = door.getItemMeta();
                        doormeta.setDisplayName("§cKicke " + name);
                        lore.add("§7§l↳ §fKicke " + name + " von deiner Insel.");
                        lore.add("§fBedenke das er dabei alle seine");
                        lore.add("§fItems im Inventar verliert!");
                        doormeta.setLore(lore);
                        lore.clear();
                        door.setItemMeta(doormeta);
                        inv.setItem(4, door);

                        ItemStack re = new ItemStack(Material.BARRIER);
                        ItemMeta remeta = re.getItemMeta();
                        remeta.setDisplayName("§cZurück zum Menü");
                        re.setItemMeta(remeta);
                        inv.setItem(8, re);
                        editing.put(p.getName(), name);
                        p.openInventory(inv);
                    }
                }
            //}
            e.setCancelled(true);
        } else if(e.getInventory().getTitle().equalsIgnoreCase("§cSpielerverwaltung")) {
            if(e.getCurrentItem() != null) {
                if(e.getCurrentItem().getType().equals(Material.BARRIER)) {
                    p.openInventory(Inventories.islandMenue(p));
                    editing.remove(p.getName());
                } else if(e.getCurrentItem().getType().equals(Material.SIGN)) {
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
                } else if(e.getCurrentItem().getType().equals(Material.IRON_DOOR)) {
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
        } else if(e.getInventory().getTitle().equalsIgnoreCase("§cKick")) {
            if(e.getCurrentItem()!= null) {
                if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§cABBRECHEN")) {
                    e.getView().close();
                    editing.remove(p.getName());
                } else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§2BESTÄTIGEN")) {



                }
            }



            e.setCancelled(true);
        } else if(e.getInventory().getTitle().equalsIgnoreCase("§cGruppenrwaltung")) {
            File isfile = new File("plugins/SkyBlock/Islands", IslandManager.getIsland(p.getUniqueId().toString()) + ".yml");
            FileConfiguration iscfg = YamlConfiguration.loadConfiguration(isfile);
            List<String> members = iscfg.getStringList("Island.Rank.Members");
            List<String> verwalter = iscfg.getStringList("Island.Rank.Verwalter");
            if(e.getCurrentItem().getType().equals(Material.LEATHER_HELMET)) {
                String name = editing.get(p.getName());
                if(!members.contains(name)) {
                    members.add(name);
                    verwalter.remove(name);
                    p.sendMessage(Messages.prefix + " Du hast den Spieler " + name + " in die Gruppe Mitglied gesetzt!");
                    Player ifon = Bukkit.getPlayerExact(name);
                    if(ifon != null) {
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
            } else if(e.getCurrentItem().getType().equals(Material.IRON_HELMET)) {
                String name = editing.get(p.getName());
                if(!verwalter.contains(name)) {
                    members.remove(name);
                    verwalter.add(name);
                    p.sendMessage(Messages.prefix + " Du hast den Spieler " + name + " in die Gruppe Verwalter gesetzt!");
                    Player ifon = Bukkit.getPlayerExact(name);
                    if(ifon != null) {
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
            } else if(e.getCurrentItem().getType().equals(Material.GOLD_HELMET)) {

            }
            e.setCancelled(true);
        }


    }





}
