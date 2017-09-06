package org.systic.citadel.util;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.systic.citadel.settings.PlayerSettings;

public class Debug {

    public static String FORMAT = C.c("&6&l[Debug] &f");

    public static void sendDebugMessage(String message){
        for(Player p : Bukkit.getServer().getOnlinePlayers()){
            if(PlayerSettings.get(p).get("debug")) p.sendMessage(FORMAT + message);
        }
    }

}
