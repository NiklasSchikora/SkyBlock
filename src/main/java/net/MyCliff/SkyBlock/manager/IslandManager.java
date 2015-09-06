package net.MyCliff.SkyBlock.manager;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import com.sk89q.worldedit.BlockVector;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.data.DataException;
import com.sk89q.worldedit.schematic.MCEditSchematicFormat;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.domains.DefaultDomain;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.InvalidFlagFormat;
import com.sk89q.worldguard.protection.managers.storage.StorageException;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class IslandManager {

	
	/*
	 * Created by Niklas Schikora on 29.06.2015
	 */


    public static File config = new File("plugins/SkyBlock/Files", "config.yml");
    public static FileConfiguration configc = YamlConfiguration.loadConfiguration(config);

    public static void pasteIsland(Location loc) {
        File shematic = new File("plugins/SkyBlock", "Island.schematic");
        if(shematic.exists()) {
            WorldEditPlugin wep = (WorldEditPlugin) Bukkit.getPluginManager().getPlugin("WorldEdit");
            EditSession session = wep.getWorldEdit().getEditSessionFactory().getEditSession(new BukkitWorld(loc.getWorld()), 1000000);
            try {
                MCEditSchematicFormat.getFormat(shematic).load(shematic).paste(session, new Vector(getNextIsX(), 40, getNextIsZ()), false);
            } catch (MaxChangedBlocksException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (DataException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("ERROR: shematic does not exist!");
        }
    }

    public static WorldGuardPlugin getWorldGuard() {
        Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("WorldGuard");
        if ((plugin == null) || (!(plugin instanceof WorldGuardPlugin))) {
            return null;
        }
        return (WorldGuardPlugin) plugin;
    }

    public static void protectIsland(Location island, Player p)
            throws ProtectedRegion.CircularInheritanceException, InvalidFlagFormat
    {
        ProtectedRegion region = null;
        DefaultDomain owners = new DefaultDomain();
        region = new ProtectedCuboidRegion(p.getUniqueId().toString() + "Island", getProtectionVectorLeft(island), getProtectionVectorRight(island));
        owners.addPlayer(p.getName());
        region.setOwners(owners);
        region.setParent(getWorldGuard().getRegionManager(getSkyWorld()).getRegion("__Global__"));
        region.setPriority(100);
        region.setFlag(DefaultFlag.GREET_MESSAGE, DefaultFlag.GREET_MESSAGE.parseInput(getWorldGuard(), p, "§9System §7• Du betrittst nun die Insel von §f" + p.getName()));
        region.setFlag(DefaultFlag.FAREWELL_MESSAGE, DefaultFlag.FAREWELL_MESSAGE.parseInput(getWorldGuard(), p, "§9System §7• Du verlässt nun die Insel von §f" + p.getName()));
        region.setFlag(DefaultFlag.PVP, DefaultFlag.PVP.parseInput(getWorldGuard(), p, "deny"));
        region.setFlag(DefaultFlag.CHEST_ACCESS, DefaultFlag.CHEST_ACCESS.parseInput(getWorldGuard(), p, "deny"));
        region.setFlag(DefaultFlag.USE, DefaultFlag.USE.parseInput(getWorldGuard(), p, "deny"));
        region.setFlag(DefaultFlag.DESTROY_VEHICLE, DefaultFlag.DESTROY_VEHICLE.parseInput(getWorldGuard(), p, "deny"));
        region.setFlag(DefaultFlag.ENTITY_ITEM_FRAME_DESTROY, DefaultFlag.ENTITY_ITEM_FRAME_DESTROY.parseInput(getWorldGuard(), p, "deny"));
        region.setFlag(DefaultFlag.ENTITY_PAINTING_DESTROY, DefaultFlag.ENTITY_PAINTING_DESTROY.parseInput(getWorldGuard(), p, "deny"));
        ApplicableRegionSet set = getWorldGuard().getRegionManager(getSkyWorld()).getApplicableRegions(island);
        if (set.size() > 0) {
            for (ProtectedRegion regions : set) {
                if (!regions.getId().equalsIgnoreCase("__global__")) {
                    getWorldGuard().getRegionManager(getSkyWorld()).removeRegion(regions.getId());
                }
            }
        }
        getWorldGuard().getRegionManager(getSkyWorld()).addRegion(region);
        try {
            getWorldGuard().getRegionManager(getSkyWorld()).save();
        } catch (StorageException e) {
            e.printStackTrace();
        }
    }

    public static void addPlayerToRegion(String owner, String member) {
        ProtectedRegion region = getWorldGuard().getRegionManager(getSkyWorld()).getRegion(owner + "Island");
        region.getOwners().addPlayer(member);
    }

    public static void removePlayerFromRegion(String owner, String  member) {
        ProtectedRegion region = getWorldGuard().getRegionManager(getSkyWorld()).getRegion(owner + "Island");
        region.getOwners().addPlayer(member);
    }

    public static void addPlayerToIslandConfig(String owner, String member) {
        File file = new File("plugins/SkyBlock/Islands", owner + "Island.yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        List<String> members = cfg.getStringList("Island.Members");
        members.add(member);
        cfg.set("Island.Members", members);
        try {
            cfg.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addPlayerToPlayerdata(String uuid, String owner) {
        File file = new  File("plugins/SkyBlock", "players.yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        if(!cfg.contains(uuid)) {
            cfg.set(uuid, owner + "Island");
            try {
                cfg.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void removePlayerToRegionConfig(String owner, String member) {
        File file = new File("plugins/SkyBlock/Islands", owner + "Island.yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        List<String> members = cfg.getStringList("Island.Members");
        members.remove(member);
        cfg.set("Island.Members", members);
        try {
            cfg.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static World getSkyWorld() {
        World w = Bukkit.getWorld(configc.getString("Skyworld"));
        return w;
    }

    public static BlockVector getProtectionVectorLeft(Location island) {
        return new BlockVector(island.getX() + 200.0D, 255.0D, island.getZ() + 200.0D);
    }

    public static BlockVector getProtectionVectorRight(Location island) {
        return new BlockVector(island.getX() - 200.0D, 0.0D, island.getZ() - 200.0D);
    }

    public static boolean hasIsland(String uuid) {
        File file = new File("plugins/SkyBlock", "players.yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        if(cfg.getString(uuid) != null) {
            return true;
        }
        return false;
    }


    public static void addConfigDefaults() {
        if(!config.exists()) {
            try {
                config.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        configc.addDefault("Config.Version", "1.0.1");
        configc.addDefault("NextIs.X", 0);
        configc.addDefault("NextIs.Y", 40);
        configc.addDefault("NextIs.Z", 0);
        configc.addDefault("Skyworld", "Skyworld");
        configc.options().copyDefaults(true);
        try {
            configc.save(config);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void updateNextIs() {
        double x = configc.getDouble("NextIs.X");
        double z = configc.getDouble("NextIs.Z");
        configc.set("NextIs.X", x + 200);
        configc.set("NextIs.Z", z + 200);
        try {
            configc.save(config);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Location getNextIsland() {
        Location loc = null;
        double x = configc.getDouble("NextIs.X");
        double y = configc.getDouble("NextIs.Y");
        double z = configc.getDouble("NextIs.Z");
        loc = new Location(getSkyWorld(), x, y, z);
        return loc;
    }

    public static double getNextIsX() {
        return configc.getDouble("NextIs.X");
    }

    public static double getNextIsZ() {
        return configc.getDouble("NextIs.Z");
    }



    public static void setChest(Location loc, Player player) {
        for (int x = -15; x <= 15; x++) {
            for (int y = -15; y <= 15; y++) {
                for (int z = -15; z <= 15; z++) {
                    if (getSkyWorld().getBlockAt(loc.getBlockX() + x, loc.getBlockY() + y, loc.getBlockZ() + z).getTypeId() == 54) {
                        Block blockToChange = getSkyWorld().getBlockAt(loc.getBlockX() + x, loc.getBlockY() + y, loc.getBlockZ() + z);
                        Chest chest = (Chest) blockToChange.getState();
                        Inventory inventory = chest.getInventory();
                        inventory.clear();
                        inventory.setItem(0, new ItemStack(Material.ICE, 2));
                        inventory.setItem(1, new ItemStack(Material.LAVA_BUCKET));
                        inventory.setItem(26, new ItemStack(Material.CACTUS));
                        inventory.setItem(8, new ItemStack(Material.PUMPKIN_SEEDS));
                        inventory.setItem(17, new ItemStack(Material.MELON));
                        inventory.setItem(16, new ItemStack(Material.SUGAR_CANE));
                        inventory.setItem(6, new ItemStack(Material.RED_MUSHROOM));
                        inventory.setItem(7, new ItemStack(Material.BROWN_MUSHROOM));
                        inventory.setItem(18, new ItemStack(Material.WOOD_SWORD));
                        inventory.setItem(19, new ItemStack(Material.WOOD_PICKAXE));
                        inventory.setItem(20, new ItemStack(Material.WOOD_AXE));
                        inventory.setItem(21, new ItemStack(Material.WOOD_SPADE));
                        inventory.setItem(9, new ItemStack(Material.LEATHER_HELMET));
                        inventory.setItem(10, new ItemStack(Material.LEATHER_CHESTPLATE));
                        inventory.setItem(11, new ItemStack(Material.LEATHER_LEGGINGS));
                        inventory.setItem(12, new ItemStack(Material.LEATHER_BOOTS));
                        inventory.setItem(25, new ItemStack(Material.BONE));
                    }
                }
            }
        }
    }


    public static void updatePlayers(String uuid) {
        File file = new File("plugins/SkyBlock", "players.yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        cfg.set(uuid, uuid + "Island");
        try {
            cfg.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addIslandConfiguration(String uuid) {
        File island = new File("plugins/SkyBlock/Islands", uuid + "Island.yml");
        FileConfiguration islandConfig = YamlConfiguration.loadConfiguration(island);
        islandConfig.addDefault("Island.Level", 0);
        islandConfig.addDefault("Island.Owner", uuid);
        islandConfig.addDefault("Island.Members", null);
        islandConfig.addDefault("Island.Spawn.X", Double.valueOf(getNextIsland().getX()));
        islandConfig.addDefault("Island.Spawn.Y", 40);
        islandConfig.addDefault("Island.Spawn.Z", Double.valueOf(getNextIsland().getZ()));
        islandConfig.addDefault("Island.Home.X", Double.valueOf(getNextIsland().getX()));
        islandConfig.addDefault("Island.Home.Y", 40);
        islandConfig.addDefault("Island.Home.Z", Double.valueOf(getNextIsland().getZ()));
        islandConfig.addDefault("Island.Home.Yaw", getNextIsland().getYaw());
        islandConfig.addDefault("Island.Home.Pitch", getNextIsland().getPitch());
        islandConfig.options().copyDefaults(true);
        try {
            islandConfig.save(island);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static Location getIslandLocation(String uuid) {
        Location loc = null;
        File file = new File("plugins/SkyBlock/Islands", uuid + "Island.yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        double x = cfg.getDouble("Island.Spawn.X");
        double y = cfg.getDouble("Island.Spawn.Y");
        double z = cfg.getDouble("Island.Spawn.Z");
        float yaw = cfg.getLong("Island.Spawn.Yaw");
        float pitch = cfg.getLong("Island.Spawn.Pitch");
        loc = new Location(getSkyWorld(),x,y ,z , yaw, pitch);
        return loc;
    }

    public static Location getIslandHome(String uuid) {
        Location loc = null;
        File file = new File("plugins/SkyBlock/Islands", uuid + "Island.yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        double x = cfg.getDouble("Island.Home.X");
        double y = cfg.getDouble("Island.Home.Y");
        double z = cfg.getDouble("Island.Home.Z");
        float yaw = cfg.getLong("Island.Home.Yaw");
        float pitch = cfg.getLong("Island.Home.Pitch");
        loc = new Location(getSkyWorld(),x,y ,z , yaw, pitch);
        return loc;
    }



    public static boolean isInOwnRegion(Player p) {
        String uuid = p.getUniqueId().toString();
        if(p.getWorld().getName().equalsIgnoreCase("Skyworld")) {
            ApplicableRegionSet ar = getWorldGuard().getRegionManager(p.getWorld()).getApplicableRegions(p.getLocation());
            Iterator<ProtectedRegion> prs = ar.iterator();
            while(prs.hasNext()) {
                ProtectedRegion pr = prs.next();
                if(pr.getId().equalsIgnoreCase(uuid + "island")) {
                    return true;
                }
            }
        }
        return false;
    }


    public static String getIsland(String uuid) {
        File players = new File("plugins/SkyBlock", "players.yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(players);
        return cfg.getString(uuid);
    }

    public static boolean isOwner(String uuid) {
        File file = new File("plugins/SkyBlock/Islands", uuid + "Island.yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        String owner = cfg.getString("Island.Owner");
        if(owner.equals(uuid)) {
            return true;
        }
        return false;
    }


}
