package net.MyCliff.SkyBlock.listener;

import com.sk89q.worldguard.protection.flags.InvalidFlagFormat;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import net.MyCliff.SkyBlock.manager.IslandManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * Created by Niklas on 29.06.2015.
 */
public class InventoryClickManager implements Listener {


    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        if(e.getInventory().getName().equalsIgnoreCase("žeSkyBlock")) {
            if(e.getCurrentItem().getType().equals(Material.SAPLING)) {
                if(!IslandManager.hasIsland(p.getUniqueId().toString())) {
                    IslandManager.pasteIsland(IslandManager.getNextIsland());
                    IslandManager.addIslandConfiguration(p);
                    IslandManager.setChest(IslandManager.getIslandLocation(p.getUniqueId().toString()), p);
                    IslandManager.updateNextIs();
                    try {
                        IslandManager.protectIsland(IslandManager.getIslandLocation(p.getUniqueId().toString()), p);
                    } catch (ProtectedRegion.CircularInheritanceException e1) {
                        e1.printStackTrace();
                    } catch (InvalidFlagFormat invalidFlagFormat) {
                        invalidFlagFormat.printStackTrace();
                    }
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
        }


    }





}
