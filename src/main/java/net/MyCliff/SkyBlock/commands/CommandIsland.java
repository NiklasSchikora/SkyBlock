package net.MyCliff.SkyBlock.commands;

import net.MyCliff.SkyBlock.manager.IslandManager;
import net.MyCliff.SkyBlock.util.Inventories;
import net.MyCliff.SkyBlock.util.Messages;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class CommandIsland implements CommandExecutor{




    public boolean onCommand(CommandSender cs, Command cmd, String label,
                             String[] args) {
        if(cs instanceof Player) {
            Player p  = (Player) cs;
            String uuid = p.getUniqueId().toString();
            if(p.hasPermission("SkyBlock.use")) {
                if(!IslandManager.hasIsland(uuid)) {
                    p.openInventory(Inventories.startInv());
                } else {
                    p.openInventory(Inventories.islandMenue(p));
                }
            } else {
                p.sendMessage(Messages.prefix + Messages.noperm);
            }



        }
        return false;
    }




}
