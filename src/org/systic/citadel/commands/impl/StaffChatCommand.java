package org.systic.citadel.commands.impl;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.systic.citadel.commands.CommandBase;
import org.systic.citadel.commands.impl.chat.ChatMode;
import org.systic.citadel.commands.impl.chat.StaffChat;
import org.systic.citadel.util.C;
import org.systic.citadel.util.Strings;

public class StaffChatCommand extends CommandBase {

	public StaffChatCommand() {
		super("staffchat", "systic.staffchat", true, "sc");
	}

	@Override
	public void run(CommandSender sender, String label, String[] args) {
		Player p = (Player) sender;

		ChatMode mode = StaffChat.get();

		if(args.length == 0) {
			sender.sendMessage(C.c("&cUsage: /" + label + " <message>"));
			return;
		}
		
		if(args.length >= 1) {
			mode.chat(p.getName(), Strings.concat(args));
			return;
		}
	}

}
