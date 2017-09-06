package org.systic.citadel.event;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CitadelEventHelper {

    public static boolean callCommandEvent(CommandSender sender, String command, String[] arguments){
        CitadelCommandEvent event = new CitadelCommandEvent(sender, command, arguments);
        Bukkit.getPluginManager().callEvent(event);

        return event.cancelled;
    }

    public static boolean callPermissionCheckEvent(CommandSender sender, String action){
        CitadelPermissionCheckEvent event = new CitadelPermissionCheckEvent(sender, action);
        event.cancelled = true;
        Bukkit.getPluginManager().callEvent(event);

        return event.cancelled;
    }

    public static boolean callTeleportEvent(Player player, Player target, String command){
        CitadelTeleportEvent event = new CitadelTeleportEvent(player, target, command);
        Bukkit.getPluginManager().callEvent(event);

        return event.cancelled;
    }

    public static boolean callSettingEvent(Player player, String setting, boolean state){
        PlayerToggleSettingEvent event = new PlayerToggleSettingEvent(player, setting, state);
        Bukkit.getPluginManager().callEvent(event);

        return event.cancelled;
    }

}
