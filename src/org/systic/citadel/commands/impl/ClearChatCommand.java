package org.systic.citadel.commands.impl;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.systic.citadel.ChatSettings;
import org.systic.citadel.Common;
import org.systic.citadel.commands.CommandBase;
import org.systic.citadel.util.C;

import java.util.concurrent.ThreadLocalRandom;

public class ClearChatCommand extends CommandBase {

    public ClearChatCommand() {
        super("clearchat", "systic.clearchat", "cc");
    }

    public void run(CommandSender sender, String label, String[] args) {
        long last = ChatSettings.LAST_CLEAR-System.currentTimeMillis();
        if(last > 0){
            sender.sendMessage(C.c("&cChat has been cleared recently, please wait " + Common.DECIMAL_FORMAT0x00.format((last / 1000.0D)) + "s before clearing it again."));
            return;
        }

        ChatSettings.LAST_CLEAR = System.currentTimeMillis()+1000;

        for(Player p : Bukkit.getServer().getOnlinePlayers()){
            if(!p.hasPermission("systic.chatbypass")){
                for(int i = 0; i < 100; i++){
                    p.sendMessage(ChatColor.values()[ThreadLocalRandom.current().nextInt(ChatColor.values().length)] + "");
                }
            }
        }

        Bukkit.broadcastMessage(C.c("&aChat has been cleared by " + (sender instanceof Player ? sender.getName() : "Console")));
    }

}
