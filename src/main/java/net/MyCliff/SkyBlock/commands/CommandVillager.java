package net.MyCliff.SkyBlock.commands;

import net.MyCliff.SkyBlock.util.Messages;
import net.MyCliff.SkyBlock.villagershop.Villagers;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.CreatureType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;

/**
 * Created by Niklas on 19.07.2015.
 */
public class CommandVillager implements CommandExecutor {




    public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
        if(cs instanceof Player) {
            Player p = (Player) cs;
            if(p.hasPermission("Villagers.Admin")) {
                if(args.length == 0) {
                    p.sendMessage(Messages.prefix + Messages.VILLAGERS_USAGE);
                } else {
                    String type = args[0];
                    if(type != null) {
                        Villagers.spawnVillager(type, p.getLocation());
                        p.sendMessage(Messages.prefix + " Du hast den Villager " + args[0] + " gesetzt!");
                    }
                }
            } else {
                p.sendMessage(Messages.noperm);
            }



        }


        return false;
    }
}
