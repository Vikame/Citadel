package org.systic.citadel.commands.impl;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.systic.citadel.Citadel;
import org.systic.citadel.commands.CommandBase;
import org.systic.citadel.util.C;

import java.util.List;

public class SocialSpyCommand extends CommandBase {

    public SocialSpyCommand() {
        super("socialspy", "systic.socialspy", true, "spy");
    }

    @Override
    public void run(CommandSender sender, String label, String[] args) {
        Player player = (Player)sender;

        if(args.length == 0) {
        	boolean enabled = Citadel.getInstance().messageManager.toggleSocialSpy(player);
        	
        	player.sendMessage(C.c((enabled ? "&aYou have enabled socialspy." : "&cYou have disabled socialspy.")));
        }else{
        	Player target = Bukkit.getPlayer(args[0]);
        	
        	if(target == null || !target.isOnline()) {
        		player.sendMessage(C.c("&cyeah that player is gonezo city."));
        		return;
        	}
        	
        	boolean enabled = Citadel.getInstance().messageManager.toggleSocialSpy(target);
        	
        	target.sendMessage(C.c((enabled ? "&aYour socialspy has been enabled by " + player.getDisplayName() + "&a."  : "&cYour socialspy has been disabled by " + player.getDisplayName() + "&c.")));
        	player.sendMessage(C.c((enabled ? "&aYou have enabled " + target.getDisplayName() + "&a'" + (target.getDisplayName().toLowerCase().endsWith("s") ? "" : "s") + " socialspy." : "&cYou have disabled " + target.getDisplayName() + "&c'" + (target.getDisplayName().toLowerCase().endsWith("s") ? "" : "s") + " socialspy." )));
        } 
    }

}
