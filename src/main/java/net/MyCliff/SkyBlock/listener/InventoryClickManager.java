package net.MyCliff.SkyBlock.listener;

import com.sk89q.worldguard.protection.flags.InvalidFlagFormat;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
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
import org.bukkit.inventory.meta.SkullMeta;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Niklas on 29.06.2015.
 */
public class InventoryClickManager implements Listener {



    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        String uuid = p.getUniqueId().toString();
        if(e.getInventory() != null) {
            if (e.getInventory().getTitle().equalsIgnoreCase("§f§lSkyBlock §7§l»")) {
                if (e.getCurrentItem().getType().equals(Material.GRASS)) {
                    if (!IslandManager.hasIsland(p.getUniqueId().toString())) {
                        IslandManager.pasteIsland(IslandManager.getNextIsland());
                        IslandManager.addIslandConfiguration(p.getUniqueId().toString(), IslandManager.nextIslandID(), p.getName());
                        IslandManager.updatePlayers(p.getUniqueId().toString(), IslandManager.nextIslandID());
                        try {
                            IslandManager.protectIsland(IslandManager.getIslandLocation(uuid), p, p.getUniqueId());
                        } catch (ProtectedRegion.CircularInheritanceException e1) {
                            e1.printStackTrace();
                        } catch (InvalidFlagFormat invalidFlagFormat) {
                            invalidFlagFormat.printStackTrace();
                        }
                        IslandManager.updateNextIs();
                        p.teleport(IslandManager.getIslandLocation(uuid));
                        IslandManager.setChest(IslandManager.getIslandLocation(uuid), p);

                    }
                    e.setCancelled(true);
                } else if (e.getCurrentItem().getType().equals(Material.PAPER)) {
                    Bukkit.dispatchCommand(p, "tutorial");
                    e.setCancelled(true);
                }
                e.setCancelled(true);
            } else if (e.getInventory().getTitle().equalsIgnoreCase("Insel-Menü")) {
                if(e.getInventory() != null) {
                    if (e.getCurrentItem() != null && e.getCurrentItem().getType() != Material.AIR) {
                        if (e.getCurrentItem().getType().equals(Material.BED)) {
                            Bukkit.dispatchCommand(p, "is home");
                        } else if (e.getSlot() == 2) {
                            Inventory inv = Bukkit.createInventory(null, 9, "§cMember deiner Insel:");
                            ItemStack stack = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
                            SkullMeta meta = (SkullMeta) stack.getItemMeta();
                            File isfile = new File("plugins/SkyBlock/Islands", IslandManager.getIslandID(p.getUniqueId().toString()) + ".yml");
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


                        } else if(e.getCurrentItem().getType().equals(Material.MAP)) {
                            IslandManager manager = new IslandManager();
                            if(manager.isOwner(uuid) || manager.isVerwalter(uuid)) {
                                p.openInventory(Inventories.getBiomInv());
                            } else {
                                e.getView().close();
                                p.sendMessage(Messages.prefix + " Du hast keine Berechtigung das Biom zu ändern!");
                            }
                        }
                    } else {
                        e.setCancelled(true);
                    }
                }
                e.setCancelled(true);
            } else if (e.getInventory().getTitle().equalsIgnoreCase("§cMember deiner Insel:")) {
                if(e.getInventory() != null) {
                    if (e.getCurrentItem() != null || e.getCurrentItem().getType() != Material.AIR) {
                        if (e.getCurrentItem().getType().equals(Material.BARRIER)) {
                            p.openInventory(Inventories.islandMenue());
                        } else if (!e.getCurrentItem().equals(Material.BARRIER)) {
                            File isfile = new File("plugins/SkyBlock/Islands", IslandManager.getIslandID(p.getUniqueId().toString()) + ".yml");
                            FileConfiguration iscfg = YamlConfiguration.loadConfiguration(isfile);
                            String owner = iscfg.getString("Island.Ownername");
                            //if(p.getName().equals(owner)) {
                            ItemStack head = e.getCurrentItem();
                            if (head.getItemMeta() != null) {
                                Inventory inv = Bukkit.createInventory(null, 9, "§cSpielerverwaltung");
                                String name = head.getItemMeta().getDisplayName();

                                ItemStack item;
                                ItemMeta imeta;
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
                                Main.editing.put(p.getName(), name);
                                p.openInventory(inv);
                            }
                        }
                        e.setCancelled(true);
                    } else {
                        e.setCancelled(true);
                    }
                } else {
                    e.setCancelled(true);
                }
            }

        }
    }





}
