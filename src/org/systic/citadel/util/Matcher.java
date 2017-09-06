package org.systic.citadel.util;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class Matcher {

    public static Player matchPlayer(String name){
        for(Player player : Bukkit.getOnlinePlayers()) {
            if (player.getName().toLowerCase().startsWith(name.toLowerCase())) return player;
        }
        return null;
    }

    public static Player matchPlayer(UUID uuid){
        for(Player player : Bukkit.getOnlinePlayers())
            if(player.getUniqueId().equals(uuid)) return player;
        return null;
    }

    public static UUID matchUUID(String name){
        Player player = matchPlayer(name);
        if(player != null) return player.getUniqueId();

        return UUIDCache.getInstance().get(name);
    }

    public static String matchName(UUID uuid){
        Player player = matchPlayer(uuid);
        if(player != null) return player.getName();

        return UUIDCache.getInstance().get(uuid);
    }

}