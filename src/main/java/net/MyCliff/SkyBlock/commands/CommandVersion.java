package net.MyCliff.SkyBlock.commands;

import net.MyCliff.SkyBlock.util.Messages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Niklas on 19.07.2015.
 */
public class CommandVersion implements CommandExecutor {


    public boolean onCommand(CommandSender cs, Command cmd, String s, String[] args) {
        if(cs instanceof Player) {
            Player p = (Player) cs;
            p.sendMessage(Messages.prefix + " Das SkyBlock-Plugin befindet sich auf der version 1.2");
        }
        return false;
    }
}
