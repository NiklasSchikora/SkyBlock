package net.MyCliff.SkyBlock.listener;

import net.MyCliff.SkyBlock.util.Messages;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by Niklas on 19.11.2015.
 */
public class JoinListener implements Listener {


    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        File file = new File("plugins/SkyBlock", "kicked.yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        List<String> kicked = cfg.getStringList("Kicked.Join");
        if(kicked.contains(p.getName())) {
            p.teleport(p.getWorld().getSpawnLocation());
            p.getInventory().clear();
            p.sendMessage(Messages.prefix + " Du wurdest von deiner ehemaligen Insel gekickt.");
            p.sendMessage(Messages.prefix + " Deshalb wurdest du zum Spawn teleportiert.");
            kicked.remove(p.getName());
            cfg.set("Kicked.Join", kicked);
            try {
                cfg.save(file);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } else {
            return;
        }


    }

}
