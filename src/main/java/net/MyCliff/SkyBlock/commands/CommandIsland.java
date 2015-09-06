package net.MyCliff.SkyBlock.commands;

import net.MyCliff.SkyBlock.manager.IslandManager;
import net.MyCliff.SkyBlock.util.Inventories;
import net.MyCliff.SkyBlock.util.Messages;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;


public class CommandIsland implements CommandExecutor{




    public HashMap<String, String> invites = new HashMap<String, String>();


    public boolean onCommand(CommandSender cs, Command cmd, String label,
                             String[] args) {
        if(cs instanceof Player) {
            Player p  = (Player) cs;
            String uuid = p.getUniqueId().toString();
            if(p.hasPermission("SkyBlock.use")) {
                if(!IslandManager.hasIsland(uuid)) {
                    p.openInventory(Inventories.startInv());
                } else {
                    IslandManager manager = new IslandManager();
                    if(args.length == 0) {
                        p.openInventory(Inventories.islandMenue(p));
                    } else if(args[0].equalsIgnoreCase("home")) {
                        p.teleport(IslandManager.getIslandHome(uuid));
                        p.sendMessage(Messages.prefix + Messages.SkyBlock_Is_Home);
                    } else if(args[0].equalsIgnoreCase("sethome")) {
                        File file = new File("plugins/SkyBlock/Islands", IslandManager.getIsland(uuid) + ".yml");
                        FileConfiguration cfg  = YamlConfiguration.loadConfiguration(file);
                        System.out.println(manager.getIsland(uuid));
                        if(manager.isOwner(uuid)) {
                            if (p.getWorld().getName().equalsIgnoreCase("Skyworld")) {
                                if (manager.isInOwnRegion(p)) {
                                    Location loc = p.getLocation();
                                    cfg.set("Island.Home.X", loc.getX());
                                    cfg.set("Island.Home.Y", loc.getY());
                                    cfg.set("Island.Home.Z", loc.getZ());
                                    cfg.set("Island.Home.Yaw", loc.getYaw());
                                    cfg.set("Island.Home.Pitch", loc.getPitch());
                                    try {
                                        cfg.save(file);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    p.sendMessage(Messages.prefix + Messages.SkyBlock_Home_Set);
                                } else {
                                    p.sendMessage(Messages.prefix + Messages.SkyBlock_Is_InRegion);
                                }
                            } else {
                                p.sendMessage(Messages.prefix + Messages.SkyBlock_Home_InvalidWorld);
                            }
                        } else {
                            p.sendMessage(Messages.prefix + Messages.SkyBlock_Is_Owner);
                        }
                    } else if(args[0].equalsIgnoreCase("invite")) {
                        if(manager.hasIsland(uuid)) {
                            if (args.length == 1) {
                                p.sendMessage(Messages.prefix + Messages.SkyBlock_Invite_Usage);
                            } else {
                                if (manager.isOwner(uuid)) {
                                    File file = new File("plugins/SkyBlock/Islands", IslandManager.getIsland(uuid) + ".yml");
                                    FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
                                    List<String> members = cfg.getStringList("Island.Members");
                                    if (!p.hasPermission("SkyBlock.Premium.Partysize")) {
                                        if (members.size() < 4) {
                                            Player target = Bukkit.getPlayer(args[1]);
                                            if (target != null) {
                                                if (!manager.hasIsland(target.getUniqueId().toString())) {
                                                    if (!members.contains(target.getUniqueId().toString())) {
                                                        if (!invites.containsKey(target.getName())) {
                                                            target.sendMessage(Messages.prefix + " Der Spieler " + p.getName() + " hat dich auf seine Insel eingeladen!");
                                                            target.sendMessage(Messages.prefix + " Mit '/is accept' kannst du die Einladung annehmen. ");
                                                            target.sendMessage(Messages.prefix + " Mit '/is deny' kannst du die Einladung ablehnen. ");
                                                            p.sendMessage(Messages.prefix + " Du hast den Spieler " + target.getName() + " auf deine Insel eingeladen!");
                                                            invites.put(target.getName(), p.getName());
                                                        } else {
                                                            p.sendMessage(Messages.prefix + Messages.SkyBlock_Invite_TargetOpenInvites);
                                                        }
                                                    } else {
                                                        p.sendMessage(Messages.prefix + Messages.SkyBlock_Invite_AlreadyMember);
                                                    }
                                                } else {
                                                    p.sendMessage(Messages.prefix + Messages.SkyBlock_Invite_TargetHasIsland);
                                                }
                                            } else {
                                                p.sendMessage(Messages.prefix + Messages.noplayer);
                                            }
                                        } else {
                                            p.sendMessage(Messages.prefix + Messages.SkyBlock_Invite_MaxSize);
                                        }
                                    } else {
                                        if (members.size() < 6) {
                                            Player target = Bukkit.getPlayer(args[1]);
                                            if (target != null) {
                                                if (!manager.hasIsland(target.getUniqueId().toString())) {
                                                    if (!members.contains(target.getUniqueId().toString())) {
                                                        if (!invites.containsKey(target.getName())) {
                                                            target.sendMessage(Messages.prefix + " Der Spieler " + p.getName() + " hat dich auf seine Insel eingeladen!");
                                                            target.sendMessage(Messages.prefix + " Mit '/is accept' kannst du die Einladung annehmen. ");
                                                            target.sendMessage(Messages.prefix + " Mit '/is deny' kannst du die Einladung ablehnen. ");
                                                            p.sendMessage(Messages.prefix + " Du hast den Spieler " + target.getName() + " auf deine Insel eingeladen!");
                                                            invites.put(target.getName(), p.getName());
                                                        } else {
                                                            p.sendMessage(Messages.prefix + Messages.SkyBlock_Invite_TargetOpenInvites);
                                                        }
                                                    } else {
                                                        p.sendMessage(Messages.prefix + Messages.SkyBlock_Invite_AlreadyMember);
                                                    }
                                                } else {
                                                    p.sendMessage(Messages.prefix + Messages.SkyBlock_Invite_TargetHasIsland);
                                                }
                                            } else {
                                                p.sendMessage(Messages.prefix + Messages.noplayer);
                                            }
                                        } else {
                                            p.sendMessage(Messages.prefix + Messages.SkyBlock_Invite_MaxSize);
                                        }
                                    }
                                } else {
                                    p.sendMessage(Messages.prefix + Messages.SkyBlock_Is_Owner);
                                }
                            }
                        } else {
                            p.sendMessage(Messages.prefix + Messages.SkyBlock_NoIsland);
                        }
                    } else if(args[0].equalsIgnoreCase("accept")) {
                        if(!manager.hasIsland(p.getUniqueId().toString())) {
                            if(invites.containsKey(p.getName())) {
                                String owner = invites.get(p.getName());
                                Player ownerP = Bukkit.getPlayerExact(owner);
                                if(ownerP != null) {
                                    String ownerUUID = ownerP.getUniqueId().toString();
                                    manager.addPlayerToRegion(ownerUUID, uuid);
                                    manager.addPlayerToIslandConfig(ownerUUID, uuid);
                                    manager.addPlayerToPlayerdata(uuid, ownerUUID);
                                } else {
                                    p.sendMessage(Messages.prefix + Messages.noplayer);
                                }
                            } else {
                                p.sendMessage(Messages.prefix + Messages.SkyBlock_No_OpenInvite);
                            }
                        }
                    } else if (args[0].equalsIgnoreCase("deny")) {
                        if(invites.containsKey(p.getName())) {
                            Player target = Bukkit.getPlayerExact(invites.get(p.getName()));
                            p.sendMessage(Messages.prefix + Messages.SkyBlock_Invite_Declined_Sender.replace("%p", target.getName()));
                            target.sendMessage(Messages.prefix + " " +  p.getName() + Messages.SkyBlock_Invite_Declined_Inviter);
                            invites.remove(p.getName());
                        } else {
                            p.sendMessage(Messages.prefix + Messages.SkyBlock_No_OpenInvite);
                        }
                    } else if(args[0].equalsIgnoreCase("setwarp")) {
                        if(manager.hasIsland(uuid)) {
                            if(manager.isOwner(uuid)) {
                                if(manager.isInOwnRegion(p)) {

                                } else {
                                    p.sendMessage(Messages.prefix + Messages.SkyBlock_Is_InRegion);
                                }




                            } else {
                                p.sendMessage(Messages.prefix + Messages.SkyBlock_Is_Owner);
                            }
                        } else {
                            p.sendMessage(Messages.prefix + Messages.SkyBlock_NoIsland);
                        }
                    }
                }
            } else {
                p.sendMessage(Messages.prefix + Messages.noperm);
            }
        }
        return false;
    }




}
