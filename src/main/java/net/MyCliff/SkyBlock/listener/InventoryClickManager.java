package net.MyCliff.SkyBlock.listener;

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
        if(e.getInventory().getName().equalsIgnoreCase("§eSkyBlock")) {
            e.setCancelled(true);
            if(e.getCurrentItem().getType().equals(Material.SAPLING)) {

            } else if(e.getCurrentItem().getType().equals(Material.BOOK)) {
                Bukkit.dispatchCommand(p, "tutorial");
            } else if(e.getSlot() == 8) {
                /*
                Message Dialog
                 */
            }
        }


    }





}
