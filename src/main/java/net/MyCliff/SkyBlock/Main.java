package net.MyCliff.SkyBlock;

import net.MyCliff.SkyBlock.commands.CommandFly;
import net.MyCliff.SkyBlock.commands.CommandIsland;
import net.MyCliff.SkyBlock.commands.CommandVersion;
import net.MyCliff.SkyBlock.commands.CommandVillager;
import net.MyCliff.SkyBlock.listener.*;
import net.MyCliff.SkyBlock.manager.IslandManager;
import net.MyCliff.SkyBlock.util.Messages;
import net.MyCliff.SkyBlock.villagershop.VillagerHandler;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.util.HashMap;


public class Main extends JavaPlugin {


    public static HashMap<String, String> editing = new HashMap<String, String>();

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
        IslandManager.addConfigDefaults();

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
        this.getCommand("insel").setExecutor(new CommandIsland(this));
        this.getCommand("villager").setExecutor(new CommandVillager());
        this.getCommand("version").setExecutor(new CommandVersion());
        this.getCommand("fly").setExecutor(new CommandFly());
    }

    public void registerListener() {
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new InventoryClickManager(), this);
        pm.registerEvents(new VillagerHandler(), this);
        pm.registerEvents(new PlayerManagementListener(), this);
        pm.registerEvents(new DeleteListener(), this);
        pm.registerEvents(new JoinListener(), this);
        pm.registerEvents(new BiomeListener(), this);
    }




}
