package net.MyCliff.SkyBlock.listener;

import net.MyCliff.SkyBlock.manager.IslandManager;
import net.MyCliff.SkyBlock.util.BiomManager;
import net.MyCliff.SkyBlock.util.IslandUtil;
import net.MyCliff.SkyBlock.level.LevelUtil;
import org.bukkit.Location;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * Created by Niklas on 25.11.2015.
 */
public class BiomeListener implements Listener{

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        int level = LevelUtil.getLevel(IslandManager.getIslandID(p.getUniqueId().toString()));
            if (e.getInventory().getTitle() != null) {
                if (e.getInventory() != null) {
                if (e.getInventory().getTitle().contains("§f§lBiome §c§l» §f§lAuswahl")) {
                    String alrdyChoosen = "§fDieses Biom ist bereits ausgewählt!";
                    String uuid = p.getUniqueId().toString();
                    if (e.getSlot() == 10) {
                        //Normal-Biom
                        if (IslandUtil.getBiom(uuid).equalsIgnoreCase(Biome.FOREST.toString())) {
                            e.getView().close();
                            p.sendMessage(alrdyChoosen);
                        } else {
                            changeBiom(uuid, Biome.FOREST);
                            BiomManager.updateBiome(p, Biome.FOREST);
                            p.sendMessage("§9§lSkyBlock-System");
                            p.sendMessage("§fDu hast dein Biom erfolgreich geändert.");
                            p.sendMessage("");
                            p.sendMessage("§f§oNeues Biom:");
                            p.sendMessage("§7• §2Standard");
                            e.getView().close();
                        }
                        e.setCancelled(true);
                    } else if (e.getSlot() == 11) {
                        //Schnee-Biom
                        if (IslandUtil.getBiom(uuid).equalsIgnoreCase(Biome.ICE_MOUNTAINS.toString())) {
                            e.getView().close();
                            p.sendMessage(alrdyChoosen);
                        } else {
                            changeBiom(uuid, Biome.ICE_MOUNTAINS);
                            BiomManager.updateBiome(p, Biome.ICE_MOUNTAINS);
                            p.sendMessage("§9§lSkyBlock-System");
                            p.sendMessage("§fDu hast dein Biom erfolgreich geändert.");
                            p.sendMessage("");
                            p.sendMessage("§f§oNeues Biom:");
                            p.sendMessage("§7• §bWinter");
                            e.getView().close();
                        }
                        e.setCancelled(true);
                    } else if (e.getSlot() == 13) {
                        //Dschungel-Biom
                        if (IslandUtil.getBiom(uuid).equalsIgnoreCase(Biome.JUNGLE.toString())) {
                            e.getView().close();
                            p.sendMessage(alrdyChoosen);
                        } else if (level >= 250) {
                            changeBiom(uuid, Biome.JUNGLE);
                            BiomManager.updateBiome(p, Biome.JUNGLE);
                            p.sendMessage("§9§lSkyBlock-System");
                            p.sendMessage("§fDu hast dein Biom erfolgreich geändert.");
                            p.sendMessage("");
                            p.sendMessage("§f§oNeues Biom:");
                            p.sendMessage("§7• §aDschungel");
                            e.getView().close();
                        } else {
                            p.sendMessage("§9§lSkyBlock-System");
                            p.sendMessage("§fDu hast nicht genug Level.");
                            p.sendMessage("");
                            p.sendMessage("§f§oBenötigtes Level:");
                            p.sendMessage("§7• §f250");
                            e.getView().close();
                        }
                        e.setCancelled(true);
                    } else if (e.getSlot() == 14) {
                        //Taiga-Biom
                        if (IslandUtil.getBiom(uuid).equalsIgnoreCase(Biome.TAIGA.toString())) {
                            e.getView().close();
                            p.sendMessage(alrdyChoosen);
                        } else if (level >= 750) {
                            changeBiom(uuid, Biome.TAIGA);
                            BiomManager.updateBiome(p, Biome.TAIGA);
                            p.sendMessage("§9§lSkyBlock-System");
                            p.sendMessage("§fDu hast dein Biom erfolgreich geändert.");
                            p.sendMessage("");
                            p.sendMessage("§f§oNeues Biom:");
                            p.sendMessage("§7• §6Taiga");
                            e.getView().close();
                        } else {
                            e.getView().close();
                            p.sendMessage("§9§lSkyBlock-System");
                            p.sendMessage("§fDu hast nicht genug Level.");
                            p.sendMessage("");
                            p.sendMessage("§f§oBenötigtes Level:");
                            p.sendMessage("§7• §f750");
                        }
                        e.setCancelled(true);
                    } else if (e.getSlot() == 15) {
                        //Savanne-Biom
                        if (IslandUtil.getBiom(uuid).equalsIgnoreCase(Biome.SAVANNA.toString())) {
                            e.getView().close();
                            p.sendMessage(alrdyChoosen);
                        } else if (level >= 1500) {
                            changeBiom(uuid, Biome.SAVANNA);
                            BiomManager.updateBiome(p, Biome.SAVANNA);
                            p.sendMessage("§9§lSkyBlock-System");
                            p.sendMessage("§fDu hast dein Biom erfolgreich geändert.");
                            p.sendMessage("");
                            p.sendMessage("§f§oNeues Biom:");
                            p.sendMessage("§7• §6Savanne");
                            e.getView().close();
                        } else {
                            e.getView().close();
                            p.sendMessage("§9§lSkyBlock-System");
                            p.sendMessage("§fDu hast nicht genug Level.");
                            p.sendMessage("");
                            p.sendMessage("§f§oBenötigtes Level:");
                            p.sendMessage("§7• §f1500");
                        }
                        e.setCancelled(true);
                    } else if (e.getSlot() == 16) {
                        //Sumpf-Biom
                        if (IslandUtil.getBiom(uuid).equalsIgnoreCase(Biome.SWAMPLAND.toString())) {
                            e.getView().close();
                            p.sendMessage(alrdyChoosen);
                        } else if (level >= 2500) {
                            changeBiom(uuid, Biome.SWAMPLAND);
                            BiomManager.updateBiome(p, Biome.SWAMPLAND);
                            p.sendMessage("§9§lSkyBlock-System");
                            p.sendMessage("§fDu hast dein Biom erfolgreich geändert.");
                            p.sendMessage("");
                            p.sendMessage("§f§oNeues Biom:");
                            p.sendMessage("§7• §2Sumpf");
                            e.getView().close();
                        } else {
                            e.getView().close();
                            p.sendMessage("§9§lSkyBlock-System");
                            p.sendMessage("§fDu hast nicht genug Level.");
                            p.sendMessage("");
                            p.sendMessage("§f§oBenötigtes Level:");
                            p.sendMessage("§7• §f2500");
                        }
                        e.setCancelled(true);
                    }
                    e.setCancelled(true);
                }
            }
        }
    }

    public void changeBiom(String uuid, Biome bType) {
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
