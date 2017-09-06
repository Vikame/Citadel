package org.systic.citadel.commands.impl.chat;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.systic.citadel.util.C;

public class StaffChat extends ChatMode {

	private static StaffChat instance;

	public StaffChat() {
		super("Staff Chat");
		instance = this;
	}

	@Override
	public void chat(String player, String message) {
		String format = C.c("&b" + player + ": " + message);
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (p.hasPermission("systic.staffchat")) {
				p.sendMessage(format);
			}
		}
	}

	public static StaffChat get() {
		return (instance != null ? instance : new StaffChat());
	}

}