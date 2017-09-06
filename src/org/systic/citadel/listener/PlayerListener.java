package org.systic.citadel.listener;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChatTabCompleteEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.systic.citadel.ChatSettings;
import org.systic.citadel.Citadel;
import org.systic.citadel.Common;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerListener implements Listener {

    public PlayerListener(){
        Bukkit.getPluginManager().registerEvents(this, Citadel.getInstance());
    }

    private final Map<UUID, Long> LAST_CHAT = new HashMap<>();

    @EventHandler
    public void onLogin(PlayerLoginEvent e){
        if(e.getPlayer().hasPermission("common.joinfull") && !Bukkit.hasWhitelist()) e.allow();
    }
    
    @EventHandler
    public void onJoin(PlayerJoinEvent e){
    	e.setJoinMessage(null);
    }
    
    @EventHandler
    public void onQuit(PlayerQuitEvent e){
    	e.setQuitMessage(null);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onChat(AsyncPlayerChatEvent e){
        Player p = e.getPlayer();

        if(p.hasPermission("systic.chatbypass")) return;

        if(ChatSettings.MUTED){

            p.sendMessage(ChatColor.RED + "You cannot talk whilst chat is muted.");
            e.setCancelled(true);

        }else if(ChatSettings.SLOW_TIME > 0) {

            if (LAST_CHAT.containsKey(p.getUniqueId())) {

                long time = LAST_CHAT.get(p.getUniqueId()) - System.currentTimeMillis();

                if (time > 0) {
                    p.sendMessage(ChatColor.RED + "You cannot talk for another " + Common.DECIMAL_FORMAT0x00.format((time / 1000.0D)) + "s.");
                    e.setCancelled(true);
                    return;
                }

            }

            LAST_CHAT.put(p.getUniqueId(), System.currentTimeMillis() + (ChatSettings.SLOW_TIME * 1000));

        }
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e){
        Player p = e.getPlayer();
        if(!p.isOp()){
            String cmd = e.getMessage().substring(1);
            if(cmd.contains(" ")) cmd = cmd.split(" ")[0];
            if(cmd.contains(":")) cmd = cmd.split(":")[1];

            if(cmd.equalsIgnoreCase("help") || cmd.equalsIgnoreCase("plugins") || cmd.equalsIgnoreCase("pl") || cmd.equalsIgnoreCase("?") || cmd.equalsIgnoreCase("ver") || cmd.equalsIgnoreCase("version")){
                p.sendMessage(Common.NO_PERMISSION);
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onTabComplete(PlayerChatTabCompleteEvent e){
        Player p = e.getPlayer();
        if(!p.isOp()){
            String cmd = e.getChatMessage();
            if(cmd.contains(" ")) cmd = cmd.split(" ")[0];
            if(cmd.contains(":")) cmd = cmd.split(":")[1];

            if(cmd.equalsIgnoreCase("help") || cmd.equalsIgnoreCase("plugins") || cmd.equalsIgnoreCase("pl") || cmd.equalsIgnoreCase("?") || cmd.equalsIgnoreCase("ver") || cmd.equalsIgnoreCase("version")){
                e.getTabCompletions().clear();
            }
        }
    }
}
