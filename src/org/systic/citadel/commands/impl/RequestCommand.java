package org.systic.citadel.commands.impl;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.systic.citadel.commands.CommandBase;
import org.systic.citadel.util.C;

public class RequestCommand extends CommandBase {

	private Map<String, Integer> delay = new HashMap<>();
	
    public RequestCommand() {
        super("helpop", null, true, "request");
    }

    @Override
    public void run(CommandSender sender, String label, String[] args) {
		if (hasCooldown((Player) sender)) {
			sender.sendMessage(C.c("You are on cooldown, try again in a little bit."));
			return;
		}
		if (args.length <= 0) {
			sender.sendMessage(C.c("&cUsage: /" + label + " <message>"));
			return;
		}
		Player p = (Player) sender;

		String message = args[0];
		if (args.length > 1) {
			for (int i = 1; i < args.length; i++) {
				message += " " + args[i];
			}
		}
		int current = (int) System.currentTimeMillis() / 1000;
		int finish = current + 10;
		delay.put(p.getName(), finish);
		p.sendMessage(C.c("&aYour message has been sent to all online staff and will be dealt with shortly."));
		Bukkit.broadcast(C.c("&eRequest from " + p.getDisplayName() + "&e:"), "common.helpop.alerts");
		Bukkit.broadcast(C.c("&c") + message, "common.helpop.alerts");
		return;
	}

	private boolean hasCooldown(Player p) {
		if (delay.containsKey(p.getName())) {
			if (delay.get(p.getName()) <= (int) System.currentTimeMillis() / 1000) {
				delay.remove(p.getName());
				return false;
			}else{
				return true;
			}
		} else {
			return false;
		}

	}

}
