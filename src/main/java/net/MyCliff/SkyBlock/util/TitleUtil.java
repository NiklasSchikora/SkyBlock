package net.MyCliff.SkyBlock.util;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle.EnumTitleAction;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

/**
 * Created by Niklas on 05.12.2015.
 */
public class TitleUtil {


    public static void sendTitle(String text, Player player, ChatColor color) {
        IChatBaseComponent chatTitle = ChatSerializer.a("{\"text\": \"" + text + "\",color:" + color.name().toLowerCase() + "}");
        PacketPlayOutTitle title = new PacketPlayOutTitle(EnumTitleAction.TITLE, chatTitle);
        PacketPlayOutTitle length = new PacketPlayOutTitle(5, 20, 5);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(title);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(length);
    }

    public static void sendSubTitle(String text, Player player, ChatColor color) {
        IChatBaseComponent chatTitle = ChatSerializer.a("{\"text\": \"" + text + "\",color:" + color.name().toLowerCase() + "}");
        PacketPlayOutTitle title = new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, chatTitle);
        PacketPlayOutTitle length = new PacketPlayOutTitle(5, 20, 5);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(title);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(length);
    }


    public static void sendActionbar(Player player, String text, ChatColor color)
    {
        IChatBaseComponent cbc = ChatSerializer.a("{\"text\": \"" + text + "\",color:" + color.name().toLowerCase() + "}");
        PacketPlayOutChat ppoc = new PacketPlayOutChat(cbc, (byte)2);
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket(ppoc);
    }

}
