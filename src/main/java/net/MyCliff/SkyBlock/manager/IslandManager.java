package net.MyCliff.SkyBlock.manager;

import java.io.File;
import java.io.IOException;

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
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class IslandManager {

	
	/*
	 * plugins/SkyBlock/Islands
	 * plugins/SkyBlock/players.yml
	 *
	 * 
	 */

    public static void pasteIsland(Location loc) {
        File shematic = new File("plugins/SkyBlock", "Island.shematic");
        if(!shematic.exists()) {
            WorldEditPlugin wep = (WorldEditPlugin) Bukkit.getPluginManager().getPlugin("WorldEdit");
            EditSession session = wep.getWorldEdit().getEditSessionFactory().getEditSession(new BukkitWorld(loc.getWorld()), 1000000);
            try {
                MCEditSchematicFormat.getFormat(shematic).load(shematic).paste(session, new Vector(0, 200, 0), false);
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
        region = new ProtectedCuboidRegion(p.getName() + "Island", getProtectionVectorLeft(island), getProtectionVectorRight(island));
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

    public static World getSkyWorld() {
        File config = new File("plugins/SkyBlock", "config.yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(config);
        World w = Bukkit.getWorld(cfg.getString("Skyworld"));
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




}
