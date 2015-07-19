package net.MyCliff.SkyBlock;

import java.io.File;
import java.lang.reflect.Field;

import net.MyCliff.SkyBlock.commands.CommandIsland;
import net.MyCliff.SkyBlock.commands.CommandVersion;
import net.MyCliff.SkyBlock.commands.CommandVillager;
import net.MyCliff.SkyBlock.listener.InventoryClickManager;
import net.MyCliff.SkyBlock.util.Messages;

import net.MyCliff.SkyBlock.villagershop.VillagerHandler;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;


public class Main extends JavaPlugin {

	/*
	 * Created by Niklas Schikora on 28.06.2015
	 * 
	 * Copyright (c) 2015 by Niklas Schikora
	 */

    @Override
    public void onDisable() {
        System.out.println("SkyBlock disabled!");
    }

    @Override
    public void onEnable() {
        System.out.println("SkyBlock version v"
                + this.getDescription().getVersion() + " by "
                + this.getDescription().getAuthors() + " enabled!");
        registerCommands();
        registerListener();
        loadConfig();
        if(Main.shematicLoaded()) {
            System.out.println("Shematic Loaded!");
        } else {
            System.out.println("Shematic missing! Insert at plugins/SkyBlock with name Island.shematic");
        }

    }

    public Main getInstance() {
        return this;
    }

    public void loadConfig() {
        FileConfiguration cfg = getConfig();
        try {
            for (Field field : Messages.class.getDeclaredFields()) {
                if (field.getType() == String.class) {
                    String name = field.getName();
                    String content = (String) field.get(null);
                    cfg.addDefault("Config." + name, content);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        cfg.options().copyDefaults(true);
        saveConfig();

        if (!cfg.isConfigurationSection("Config"))
            return;
        try {
            for (String name : cfg.getConfigurationSection("Config").getKeys(
                    false)) {
                String content = cfg.getString("Config." + name);
                Field field = Messages.class.getDeclaredField(name);
                field.set(null, content);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void registerCommands() {
        this.getCommand("insel").setExecutor(new CommandIsland());
        this.getCommand("villager").setExecutor(new CommandVillager());
        this.getCommand("version").setExecutor(new CommandVersion());
    }

    public void registerListener() {
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new InventoryClickManager(), this);
        pm.registerEvents(new VillagerHandler(), this);
    }


    public static boolean shematicLoaded() {
        File file = new File("plugins/SkyBlock", "players.yml");
        if(file != null) {
            return true;
        }
        return false;
    }


}
