package org.systic.citadel;

import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;
import org.systic.citadel.commands.impl.*;
import org.systic.citadel.generic.MessageManager;
import org.systic.citadel.generic.PlayTimeManager;
import org.systic.citadel.lag.Profile;
import org.systic.citadel.lag.Profiler;
import org.systic.citadel.listener.PlayerListener;
import org.systic.citadel.settings.PlayerSettings;
import org.systic.citadel.settings.SettingHandler;
import org.systic.citadel.statistics.PlayerStatistics;
import org.systic.citadel.statistics.StatisticManager;
import org.systic.citadel.statistics.Statistics;

public class Citadel extends JavaPlugin {

	private static Citadel instance;

	public Profiler profiler;
	public PlayTimeManager playTimeManager;
	public MessageManager messageManager;
	public void onEnable() {
		instance = this;
		profiler = new Profiler(this);

		saveDefaultConfig();

		// Initialize default settings
		PlayerSettings.DEFAULTS.put("messages", true);
		PlayerSettings.DEFAULTS.put("global chat", true);
		PlayerSettings.DEFAULTS.put("debug", false);
		PlayerSettings.PERMISSIONS.put("debug", "systic.setting.debug");
		PlayerSettings.ENABLED_MATERIALS.put("debug", Material.GLOWSTONE_DUST);
		PlayerSettings.DISABLED_MATERIALS.put("debug", Material.REDSTONE);

		PlayerSettings.DEFAULTS.put("vanish", false);
		PlayerSettings.PERMISSIONS.put("vanish", "setting.vanish");

		// Initialize profiles
		profiler.register(new Profile("Mass Scoreboard Update (ASync)", Material.NETHER_STAR));

		// Initialize listeners
		(playTimeManager = new PlayTimeManager()).load();
		new PlayerListener();
		new SettingHandler();
		// new ChatHandler();

		// Generic initialization
		(messageManager = new MessageManager()).load();
		new StatisticManager();

		// Initialize commands
		new BroadcastCommand();
		new BroadcastRawCommand();
		new CheckStatisticsCommand();
		new ClearChatCommand();
		new EnchantCommand();
		new GamemodeCommand();
		new MessageCommand();
		new MuteChatCommand();
		new ProfilerCommand();
		new ReplyCommand();
		new SlowChatCommand();
		new GiveCommand();
		new TogglePMCommand();
		new SettingsCommand();
		new PlayTimeCommand();
		new IgnoreCommand();
		new TeleportCommand();
		new ProcessCommand();
		new SocialSpyCommand();
		new StaffChatCommand();
		new RequestCommand();
	}

	public void onDisable() {
		PlayerSettings.save();
		playTimeManager.save();
		messageManager.save();
		Statistics.getInstance().save();
		PlayerStatistics.saveAll();
		saveConfig();
	}

	public static Citadel getInstance() {
		return instance;
	}

}
