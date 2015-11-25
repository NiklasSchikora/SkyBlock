package net.MyCliff.SkyBlock.listener;

import net.MyCliff.SkyBlock.manager.BiomeManager;
import net.MyCliff.SkyBlock.util.Messages;
import org.bukkit.Material;
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
        if(e.getInventory() != null) {
            if(e.getInventory().getTitle().equalsIgnoreCase("§f§lSkyBlock §7§l» §e§lBiome")) {
                String uuid = p.getUniqueId().toString();
                if (e.getCurrentItem() != null && e.getCurrentItem().getType() != Material.AIR) {
                    if(e.getSlot() == 11) {
                        BiomeManager.changeBiom(uuid, Biome.FOREST);
                        e.getView().close();
                        p.sendMessage(Messages.prefix + " Du hast das Biom deiner Insel auf 'Normal' gesetzt!");
                    } else if(e.getSlot() == 13) {
                        BiomeManager.changeBiom(uuid, Biome.ICE_MOUNTAINS);
                        e.getView().close();
                        p.sendMessage(Messages.prefix + " Du hast das Biom deiner Insel auf 'Schnee' gesetzt!");
                    }



                }
            }
        }
    }

}
