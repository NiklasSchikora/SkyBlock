package net.MyCliff.SkyBlock.level;


import net.MyCliff.SkyBlock.manager.IslandManager;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

/**
 * Created by Niklas on 19.11.2015.
 */
public class LevelUtil {



    public static void calculateLevel(String uuid) {
        Location loc = IslandManager.getIslandLocation(uuid);
        loc.setY(0);
        String id = IslandManager.getIslandID(uuid);
        if(loc != null) {
            int px = loc.getBlockX();
            int py = loc.getBlockY();
            int pz = loc.getBlockZ();
            int levelPoints = 0;
            int radius = 200;
            for (int x = -radius; x <= radius; x++) {
                for (int y = 0; y <= 255; y++) {
                    for (int z = -radius; z <= radius; z++) {
                        Block b = loc.getWorld().getBlockAt(px + x,
                                py + y, pz + z);
                        switch(b.getType()) {
                            case COBBLESTONE:
                                levelPoints = levelPoints + 2;
                                break;
                            case COBBLESTONE_STAIRS:
                                levelPoints = levelPoints + 3;
                                break;
                            case FURNACE:
                                levelPoints = levelPoints + 16;
                                break;
                            case BRICK:
                                levelPoints = levelPoints + 5;
                                break;
                            case STONE_SLAB2:
                                levelPoints = levelPoints + 2;
                                break;
                            case BRICK_STAIRS:
                                levelPoints = levelPoints + 8;
                                break;
                            case WOOD:
                                levelPoints = levelPoints + 2;
                                break;
                            case WOOD_STEP:
                                levelPoints = levelPoints + 1;
                                break;
                            case WOOD_STAIRS:
                                levelPoints = levelPoints + 3;
                                break;
                            case STONE:
                                levelPoints = levelPoints + 5;
                                break;
                            case STONE_PLATE:
                                levelPoints = levelPoints + 10;
                                break;
                            case WOOD_DOOR:
                                levelPoints = levelPoints + 4;
                                break;
                            case WOOD_PLATE:
                                levelPoints = levelPoints + 4;
                                break;
                            case FENCE:
                                levelPoints = levelPoints + 3;
                                break;
                            case FENCE_GATE:
                                levelPoints = levelPoints + 2;
                                break;
                            case SIGN:
                                levelPoints = levelPoints + 2;
                                break;
                            case CHEST:
                                levelPoints = levelPoints + 2;
                                break;
                            case TRAPPED_CHEST:
                                levelPoints = levelPoints + 20;
                                break;
                            case WORKBENCH:
                                levelPoints = levelPoints + 8;
                                break;
                            case OBSIDIAN:
                                levelPoints = levelPoints + 25;
                                break;
                            case COAL_BLOCK:
                                levelPoints = levelPoints + 20;
                                break;
                            case LAPIS_BLOCK:
                                levelPoints = levelPoints + 50;
                                break;
                            case IRON_BLOCK:
                                levelPoints = levelPoints + 100;
                                break;
                            case IRON_DOOR:
                                levelPoints = levelPoints + 23;
                                break;
                            case IRON_TRAPDOOR:
                                levelPoints = levelPoints + 45;
                                break;
                            case ANVIL:
                                levelPoints = levelPoints + 30;
                                break;
                            case GOLD_BLOCK:
                                levelPoints = levelPoints + 150;
                                break;
                            case DIAMOND_BLOCK:
                                levelPoints = levelPoints + 300;
                                break;
                            case EMERALD_BLOCK:
                                levelPoints = levelPoints + 400;
                                break;
                            case BEACON:
                                levelPoints = levelPoints + 500;
                                break;
                            case REDSTONE_WIRE:
                                levelPoints = levelPoints + 10;
                                break;
                            case REDSTONE_TORCH_ON:
                                levelPoints = levelPoints + 3;
                                break;
                            case REDSTONE_TORCH_OFF:
                                levelPoints = levelPoints + 4;
                                break;
                            case REDSTONE_BLOCK:
                                levelPoints = levelPoints + 20;
                                break;
                            case REDSTONE_LAMP_ON:
                                levelPoints = levelPoints + 18;
                                break;
                            case REDSTONE_LAMP_OFF:
                                levelPoints = levelPoints + 18;
                                break;
                            case GLOWSTONE:
                                levelPoints = levelPoints + 20;
                                break;
                            case HOPPER:
                                levelPoints = levelPoints + 70;
                                break;
                            case DISPENSER:
                                levelPoints = levelPoints + 16;
                                break;
                            case DROPPER:
                                levelPoints = levelPoints + 15;
                                break;
                            case NOTE_BLOCK:
                            levelPoints = levelPoints + 20;
                                break;
                            case JUKEBOX:
                                levelPoints = levelPoints + 50;
                                break;
                            case PISTON_BASE:
                                levelPoints = levelPoints + 27;
                                break;
                            case SLIME_BLOCK:
                                levelPoints = levelPoints + 50;
                                break;
                            case PISTON_STICKY_BASE:
                                levelPoints = levelPoints + 35;
                                break;
                            case RAILS:
                                levelPoints = levelPoints + 5;
                                break;
                            case POWERED_RAIL:
                                levelPoints = levelPoints + 18;
                                break;
                            case DETECTOR_RAIL:
                                levelPoints = levelPoints + 13;
                                break;
                            case ACTIVATOR_RAIL:
                                levelPoints = levelPoints + 13;
                                break;
                            case WOOL:
                                levelPoints = levelPoints + 2;
                                break;
                            case BED:
                                levelPoints = levelPoints + 10;
                                break;
                            case DIRT:
                                levelPoints = levelPoints + 10;
                                break;
                            case MYCEL:
                                levelPoints = levelPoints + 20;
                                break;
                            case SAND:
                                levelPoints = levelPoints + 10;
                                break;
                            case GRASS:
                                levelPoints = levelPoints + 10;
                                break;
                            case SANDSTONE:
                                levelPoints = levelPoints + 40;
                                break;
                            case RED_SANDSTONE:
                                levelPoints = levelPoints + 44;
                                break;
                            case  SANDSTONE_STAIRS:
                                levelPoints = levelPoints + 20;
                                break;
                            case RED_SANDSTONE_STAIRS:
                                levelPoints = levelPoints + 22;
                                break;
                            case GRAVEL:
                                levelPoints = levelPoints + 5;
                                break;
                            case SAPLING:
                                levelPoints = levelPoints + 3;
                                break;
                            case  LAVA :
                                levelPoints = levelPoints + 3;
                                break;
                            case GLASS:
                                levelPoints = levelPoints + 12;
                                break;
                            case STAINED_GLASS:
                                levelPoints = levelPoints + 14;
                                break;
                            case NETHERRACK:
                                levelPoints = levelPoints + 20;
                                break;
                            case SOUL_SAND:
                                levelPoints = levelPoints + 20;
                                break;
                            case MOB_SPAWNER:
                                levelPoints = levelPoints + 50;
                                break;
                            case FLOWER_POT:
                                levelPoints = levelPoints + 2;
                                break;
                            case ICE:
                                levelPoints = levelPoints + 2;
                                break;
                            case PACKED_ICE:
                                levelPoints = levelPoints + 4;
                                break;
                            case SNOW_BLOCK:
                                levelPoints = levelPoints + 5;
                                break;
                            case BREWING_STAND:
                                levelPoints = levelPoints + 50;
                                break;
                            case ENCHANTMENT_TABLE:
                                levelPoints = levelPoints + 115;
                                break;
                            case BOOKSHELF:
                                levelPoints = levelPoints + 15;
                                break;
                            case JACK_O_LANTERN :
                                levelPoints = levelPoints + 5;
                                break;
                            case NETHER_BRICK:
                                levelPoints = levelPoints + 8;
                                break;
                            case NETHER_FENCE:
                                levelPoints = levelPoints + 8;
                                break;
                            case ENDER_CHEST:
                                levelPoints = levelPoints + 150;
                                break;
                            case MOSSY_COBBLESTONE:
                                levelPoints = levelPoints + 5;
                                break;
                            case LADDER:
                                levelPoints = levelPoints + 2;
                                break;
                            case IRON_BARDING:
                                levelPoints = levelPoints + 4;
                                break;
                            case THIN_GLASS:
                                levelPoints = levelPoints + 5;
                                break;
                            case STAINED_GLASS_PANE:
                                levelPoints = levelPoints + 5;
                                break;
                            case QUARTZ_BLOCK:
                                levelPoints = levelPoints + 8;
                                break;
                            case DAYLIGHT_DETECTOR:
                                levelPoints = levelPoints + 20;
                                break;
                            case CLAY:
                                levelPoints = levelPoints + 5;
                                break;
                            case CLAY_BRICK:
                                levelPoints = levelPoints + 6;
                                break;
                            case STONE_BUTTON:
                                levelPoints = levelPoints + 5;
                                break;
                            case WOOD_BUTTON:
                                levelPoints = levelPoints + 2;
                                break;
                            case PRISMARINE:
                                levelPoints = levelPoints + 5;
                                break;
                            case PRISMARINE_SHARD:
                                levelPoints = levelPoints + 10;
                                break;
                            case SEA_LANTERN:
                                levelPoints = levelPoints + 8;
                                break;
                            case HAY_BLOCK:
                                levelPoints = levelPoints + 3;
                                break;
                            case BANNER:
                                levelPoints = levelPoints + 12;
                                break;
                            case SKULL:
                                levelPoints = levelPoints + 10;
                                break;
                            case CARPET:
                                levelPoints = levelPoints + 1;
                                break;
                            case TORCH:
                                levelPoints = levelPoints + 1;
                                break;
                            case REDSTONE_COMPARATOR:
                                levelPoints = levelPoints + 20;
                                break;
                            case CAULDRON :
                                levelPoints = levelPoints + 50;
                                break;
                            case SPONGE :
                                levelPoints = levelPoints + 10;
                                break;
                            case ENDER_STONE:
                                levelPoints = levelPoints + 10;
                                break;
                            case COBBLE_WALL:
                                levelPoints = levelPoints + 6;
                                break;
                            default: break;
                        }
                    }
                }
            }
            int level = 0;
            level = levelPoints/100;
            File file = new File("plugins/SkyBlock/Islands", id + ".yml");
            FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
            cfg.set("Island.Level", level);
            cfg.set("Island.XP", levelPoints);
            try {
                cfg.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static Integer getLevel(String islandID) {
        int level = 0;
        File file = new File("plugins/SkyBlock/Islands", islandID + ".yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        level = cfg.getInt("Island.Level");
        return level;
    }


    public static String getProcess(String islandID) {
        String message = "";
        double xp = getXP(islandID);
        double div = 100;

        double value = xp/div;
        int valueTruncated = (int) value;
        double value2 = valueTruncated;
        double value3 = value - value2;
        double accalc = value3 * 10;
        double fcalc = Math.round(accalc);
        double missing = 10 - fcalc;
        for(int i = 0; i <= fcalc; i++) {
            message = message + "§2█";
        }
        for(int i = 0; i <= missing; i++) {
            message = message + "§c█";
        }
        message = message + "   \n- " + fcalc*10 + "% Abgeschlossen";
        return message;
    }

    public static Integer getXP(String islandID) {
        File file = new File("plugins/SkyBlock/Islands", islandID + ".yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        return cfg.getInt("Island.XP");
    }



}
