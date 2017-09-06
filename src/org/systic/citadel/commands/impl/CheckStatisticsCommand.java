package org.systic.citadel.commands.impl;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.systic.citadel.Common;
import org.systic.citadel.commands.CommandBase;
import org.systic.citadel.statistics.PlayerStatistics;
import org.systic.citadel.util.C;

public class CheckStatisticsCommand extends CommandBase {

    public CheckStatisticsCommand() {
        super("checkstatistics", "systic.checkstats", "checkstats");
    }

    @Override
    public void run(CommandSender sender, String label, String[] args) {
        if(args.length <= 0){
            sender.sendMessage(C.c("&cUsage: /" + label + " <player>"));
            return;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if(target == null){
            sender.sendMessage(C.c("&cPlayer '" + args[0] + "' was not found."));
            return;
        }

        PlayerStatistics stats = PlayerStatistics.get(target);
        sender.sendMessage(C.c("&6Hit Count: &f" + stats.hit_count));
        sender.sendMessage(C.c("&6Miss Count: &f" + stats.miss_count));
        sender.sendMessage(C.c("&6Hit/Miss Ratio: &f" + Common.DECIMAL_FORMAT0x00.format((stats.hit_count == 0 ? 0 : stats.miss_count == 0 ? stats.hit_count : stats.hit_count / stats.miss_count))));
        sender.sendMessage(C.c("&6Kill Count: &f" + stats.kill_count));
        sender.sendMessage(C.c("&6Death Count: &f" + stats.death_count));
    }

}
