package org.systic.citadel.commands.impl;


import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.systic.citadel.Citadel;
import org.systic.citadel.commands.AsyncCommandBase;
import org.systic.citadel.util.C;
import org.systic.citadel.util.UUIDFetcher;

import java.util.UUID;

public class PlayTimeCommand extends AsyncCommandBase {

    public PlayTimeCommand() {
        super("playtime", null, true, "pt");
    }

    @Override
    public void run(CommandSender sender, String label, String[] args) {
        Player player = (Player)sender;

        UUID uuid = player.getUniqueId();
        if(args.length > 0 && player.hasPermission("systic.playtime.staff")){
            try {
                uuid = UUIDFetcher.getUUIDOf(args[0]);
            } catch (Exception e) {
                player.sendMessage(C.c("&cPlayer '" + args[0] + "' was not found."));
                return;
            }

            if(uuid == null){
                player.sendMessage(C.c("&cPlayer '" + args[0] + "' was not found."));
                return;
            }
        }

        int playtime = Citadel.getInstance().playTimeManager.getPlayTime(uuid);

        String formatted = "";

        int months = 0;
        int weeks = 0;
        int days = 0;
        int hours = 0;
        int minutes = 0;
        int seconds = 0;

        while(playtime >= 2592000){
            months++;
            playtime -= 2592000;
        }
        while(playtime >= 604800){
            weeks++;
            playtime -= 604800;
        }
        while(playtime >= 86400){
            days++;
            playtime -= 86400;
        }
        while(playtime >= 3600){
            hours++;
            playtime -= 3600;
        }
        while(playtime >= 60){
            minutes++;
            playtime -= 60;
        }
        seconds = playtime;
        
        // 1 month, 2 weeks, 6 days, 23 hours, 59 minutes and 10 seconds
        
        if(months == 0 && weeks == 0 && days == 0 && hours == 0 && minutes == 0) formatted = seconds + " second" + (seconds == 1 ? "" : "s");
        else{

            formatted = " and " + seconds + " second" + (seconds == 1 ? "" : "s");
            if(minutes != 0) formatted = minutes + " minute" + (minutes == 1 ? "" : "s") + formatted;
            if(hours != 0) formatted = hours + " hour" + (minutes == 1 ? "" : "s") + ", " + formatted;
            if(days != 0) formatted = days + " day" + (minutes == 1 ? "" : "s") + ", " + formatted;
            if(weeks != 0) formatted = weeks + " week" + (minutes == 1 ? "" : "s") + ", " + formatted;
            if(months != 0) formatted = months + " month" + (minutes == 1 ? "" : "s") + ", " + formatted;
        }

        player.sendMessage(C.c("&6" + (args.length == 0 || player.getName().equalsIgnoreCase(args[0]) ? "You have" : args[0] + " has") + " played for &f" + formatted + "&6."));
    }

}
