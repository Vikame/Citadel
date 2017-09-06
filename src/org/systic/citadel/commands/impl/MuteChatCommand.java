package org.systic.citadel.commands.impl;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.systic.citadel.ChatSettings;
import org.systic.citadel.commands.CommandBase;
import org.systic.citadel.util.C;

public class MuteChatCommand extends CommandBase {

    public MuteChatCommand() {
        super("mutechat", "systic.mutechat", "lockchat", "mc", "lc");
    }

    public void run(CommandSender sender, String label, String[] args) {
        ChatSettings.MUTED = !ChatSettings.MUTED;

        if(ChatSettings.MUTED){
            Bukkit.broadcastMessage(C.c("&cGlobal chat has been muted by " + (sender instanceof Player ? sender.getName() : "Console")));
        }else{
            Bukkit.broadcastMessage(C.c("&aGlobal chat has been un-muted by " + (sender instanceof Player ? sender.getName() : "Console")));
        }
    }

}
