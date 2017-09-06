package org.systic.citadel.commands.impl;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.systic.citadel.Citadel;
import org.systic.citadel.Common;
import org.systic.citadel.commands.CommandBase;
import org.systic.citadel.lag.Profiler;

public class ProfilerCommand extends CommandBase {

    public ProfilerCommand() {
        super("profiler", null, true);
    }

    @Override
    public void run(CommandSender sender, String label, String[] args) {
        Player player = (Player)sender;
        if(!player.isOp()){
            sender.sendMessage(Common.NO_PERMISSION);
            return;
        }

        if(args.length <= 0) {
            player.openInventory(Citadel.getInstance().profiler.update());
        }else{
            Profiler profiler = Profiler.getProfiler(args[0]);

            if(profiler == null){
                player.openInventory(Citadel.getInstance().profiler.update());
            }else{
                player.openInventory(profiler.update());
            }
        }
    }

}
