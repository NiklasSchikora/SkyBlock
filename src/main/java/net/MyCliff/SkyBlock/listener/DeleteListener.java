package net.MyCliff.SkyBlock.listener;

import net.MyCliff.SkyBlock.Main;
import net.MyCliff.SkyBlock.manager.IslandManager;
import net.MyCliff.SkyBlock.util.Messages;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by Niklas on 16.11.2015.
 */
public class DeleteListener implements Listener {

    @EventHandler
    public void onInventory(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        String uuid = p.getUniqueId().toString();
        if(e.getInventory() != null) {
            if(e.getCurrentItem()!= null) {
                if(e.getInventory().getTitle().equalsIgnoreCase("§f§lBist du dir sicher?")) {
                    if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§cNein")) {
                        e.getView().close();
                        Main.editing.remove(p.getName());
                    } else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§2Ja")) {
                        File file = new File("plugins/SkyBlock/Islands", IslandManager.getIslandID(uuid) + ".yml");
                        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
                        File pfile = new File("plugins/SkyBlock", "players.yml");
                        FileConfiguration pcfg = YamlConfiguration.loadConfiguration(pfile);
                        if(IslandManager.isOwner(uuid)) {
                            IslandManager.deleteRegion(IslandManager.getIslandID(uuid));
                            List<String> members = cfg.getStringList("Island.Members");
                            if(members.size() == 0) {
                                p.getInventory().clear();
                                pcfg.set(uuid, null);
                                try {
                                    pcfg.save(pfile);
                                } catch (IOException e1) {
                                    e1.printStackTrace();
                                }
                                del(file);
                                pcfg.set(uuid, null);
                                try {
                                    pcfg.save(pfile);
                                } catch (IOException e1) {
                                    e1.printStackTrace();
                                }
                                p.teleport(p.getWorld().getSpawnLocation());
                                p.sendMessage(Messages.prefix + " Du hast deine Insel gelöscht!");
                            } else {
                                e.getView().close();
                                p.sendMessage(Messages.prefix + " Es befinden sich noch Spieler auf deiner Insel!");
                                p.sendMessage(Messages.prefix + " Kicke diese um deine Insel auflösen zu können.");
                            }
                        } else {
                            p.sendMessage(Messages.prefix + " Du musst der Inselbesitzer sein um deine Insel löschen zu können!");
                        }
                    } else {
                        e.getView().close();
                    }
                    e.setCancelled(true);
                }
            }
        }

    }


    public boolean del(File dir){
        if (dir.isDirectory()){
            File[] files = dir.listFiles();
            for (File aktFile: files){
                del(aktFile);
            }

        }
        return dir.delete();
    }



}
