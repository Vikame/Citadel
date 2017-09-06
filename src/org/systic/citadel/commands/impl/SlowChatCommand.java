package org.systic.citadel.commands.impl;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.systic.citadel.ChatSettings;
import org.systic.citadel.commands.CommandBase;
import org.systic.citadel.util.C;

public class SlowChatCommand extends CommandBase {

    public SlowChatCommand() {
        super("slowchat", "systic.slowchat", "slc");
    }

    @Override
    public void run(CommandSender sender, String label, String[] args) {
        if(args.length <= 0){
            sender.sendMessage(C.c("&cUsage: /" + label + " <time>"));
            return;
        }

        int timer = -1;
        try{
            timer = Integer.parseInt(args[0]);
        }catch(NumberFormatException e){
            sender.sendMessage(C.c("&c" + args[0] + " is not a valid time-frame."));
            return;
        }

        ChatSettings.SLOW_TIME = timer;
        if(timer <= 0){
            Bukkit.broadcastMessage(C.c("&aChat is no longer slowed."));
        }else{
            Bukkit.broadcastMessage(C.c("&cChat is now slowed. You may only talk once every " + timer + " seconds."));
        }
    }
}
