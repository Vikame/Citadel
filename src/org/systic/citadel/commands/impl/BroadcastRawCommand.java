package org.systic.citadel.commands.impl;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.systic.citadel.commands.CommandBase;
import org.systic.citadel.util.C;

public class BroadcastRawCommand extends CommandBase {

    public BroadcastRawCommand() {
        super("broadcastraw", "systic.broadcastraw", false, "bcraw");
    }

    @Override
    public void run(CommandSender sender, String label, String[] args) {
        if(args.length <= 0){
            sender.sendMessage(C.c("&cUsage: /" + label + " <message>"));
            return;
        }

        String message = "";
        for(String s : args) message += s + " ";

        Bukkit.broadcastMessage(C.c(message));
    }

}
