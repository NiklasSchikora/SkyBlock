package net.MyCliff.SkyBlock.villagershop;

import net.MyCliff.SkyBlock.villagershop.shoputil.Merchant;
import net.MyCliff.SkyBlock.villagershop.shoputil.MerchantOffer;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

/**
 * Created by Niklas on 09.07.2015.
 */
public class VillagerHandler implements Listener{

    @EventHandler
    public void onInteract(PlayerInteractAtEntityEvent e) {
        Player p = e.getPlayer();
        if(e.getRightClicked().getType().equals(EntityType.VILLAGER)) {
            e.setCancelled(true);
            Villager v = (Villager) e.getRightClicked();
            if(v.getCustomName().equalsIgnoreCase("Test")) {
                Merchant merchant = new Merchant();
                merchant.setTitle("§cTest Inve");


                MerchantOffer offer = new MerchantOffer(getItemForOffer(Material.APPLE, 1, (byte) 1, "Apppfffeeelll", null), getItemForOffer(Material.DIAMOND, 3, (byte) 0, "Le Diamomd", null));
                merchant.addOffer(offer);

                merchant.setCustomer(p);
                merchant.openTrading(p);




            }
        }
    }


    private ItemStack getItemForOffer(Material mat, int amount,  byte damage, String displayname, List<String> lore) {
        ItemStack item = new ItemStack(mat, amount, (short) damage);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(displayname);
        if(lore != null) {
            meta.setLore(lore);
        }
        item.setItemMeta(meta);
        return item;
    }


}
