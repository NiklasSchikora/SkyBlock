package net.MyCliff.SkyBlock.commands;

import net.MyCliff.SkyBlock.manager.IslandManager;
import net.MyCliff.SkyBlock.util.Messages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Niklas on 06.09.2015.
 */
public class CommandFly implements CommandExecutor{


    private List<String> flying = new ArrayList<String>();


    public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
        if(cs instanceof Player) {
            Player p = (Player) cs;
            if(p.hasPermission("SkyBlock.Fly") || p.isOp()) {
                if(!p.isOp()) {
                    if(IslandManager.isInOwnRegion(p)) {
                        if(!flying.contains(p.getName())) {
                            flying.add(p.getName());
                            p.setAllowFlight(true);
                            p.setAllowFlight(true);
                            p.sendMessage(Messages.prefix + Messages.Fly_On);
                        } else {
                            flying.remove(p.getName());
                            p.setAllowFlight(false);
                            p.setFlying(false);
                            p.sendMessage(Messages.prefix + Messages.Fly_Off);
                        }
                    } else {
                        p.sendMessage(Messages.prefix + Messages.SkyBlock_Is_InRegion);
                    }
                } else {
                    if(!flying.contains(p.getName())) {
                        flying.add(p.getName());
                        p.setAllowFlight(true);
                        p.setAllowFlight(true);
                        p.sendMessage(Messages.prefix + Messages.Fly_On);
                    } else {
                        flying.remove(p.getName());
                        p.setAllowFlight(false);
                        p.setFlying(false);
                        p.sendMessage(Messages.prefix + Messages.Fly_Off);
                    }
                }
            } else {
                p.sendMessage(Messages.prefix + Messages.noperm);
            }
        }
        return false;
    }
}

