package net.MyCliff.SkyBlock.level;

import net.MyCliff.SkyBlock.Main;
import net.MyCliff.SkyBlock.manager.IslandManager;
import net.MyCliff.SkyBlock.util.Messages;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * Created by Niklas on 01.12.2015.
 */
public class LevelTask {

    public Main main;
    public LevelTask(Main plugin) {
        this.main = plugin;
    }

    private int taskID;

    public void getLevelTask(final String uuid, final Player p) {
        this.taskID = Bukkit.getScheduler().scheduleAsyncDelayedTask(main, new Runnable() {
            public void run() {
                LevelUtil.calculateLevel(uuid);
                if(p != null) {
                    p.sendMessage(Messages.prefix + " Level wurde aktualisiert!");
                    p.sendMessage(Messages.prefix + " Dein Level: §6" + LevelUtil.getLevel(IslandManager.getIslandID(uuid)));
                }
                Bukkit.getScheduler().cancelTask(taskID);
            }
        }, 0);
    }




}
