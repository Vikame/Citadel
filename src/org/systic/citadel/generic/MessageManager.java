package org.systic.citadel.generic;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.systic.citadel.Citadel;
import org.systic.citadel.util.C;

import java.util.*;

public class MessageManager {

    private final Map<UUID, UUID> LAST_MESSAGE;
    private final Map<UUID, List<UUID>> IGNORED;
    private final List<UUID> SOCIALSPY;
    
    public MessageManager(){
        LAST_MESSAGE = new HashMap<>();
        IGNORED = new HashMap<>();
        SOCIALSPY = new ArrayList<>();
    }

    public Player getLastMessenger(Player player){
        return LAST_MESSAGE.containsKey(player.getUniqueId()) ? Bukkit.getPlayer(LAST_MESSAGE.get(player.getUniqueId())) : null;
    }

    public void message(Player player, Player target, String message){
        player.sendMessage(C.c("&d(To " + target.getDisplayName() + "&d) ") + message);
        target.sendMessage(C.c("&d(From " + player.getDisplayName() + "&d) ") + message);

        LAST_MESSAGE.put(player.getUniqueId(), target.getUniqueId());
        LAST_MESSAGE.put(target.getUniqueId(), player.getUniqueId());
        
        String msg = C.c("&7[&cSPY&7] &8[&f" + player.getDisplayName() + " &8-> &f" + target.getDisplayName() + "&8]: &7") + message;
        
        for(UUID uuid : SOCIALSPY){
        	Player p = Bukkit.getPlayer(uuid);
        	if(p == null || !p.isOnline() || p.getName().equalsIgnoreCase(target.getName()) || p.getName().equalsIgnoreCase(player.getName())) continue;
        	
        	p.sendMessage(msg);
        }
    }
    
    public boolean toggleSocialSpy(Player player){
    	if(SOCIALSPY.contains(player.getUniqueId())) SOCIALSPY.remove(player.getUniqueId());
    	else SOCIALSPY.add(player.getUniqueId());
    	
    	return SOCIALSPY.contains(player.getUniqueId());
    }

    public boolean isIgnored(Player player, Player target){
        return IGNORED.containsKey(player.getUniqueId()) && IGNORED.get(player.getUniqueId()).contains(target.getUniqueId());
    }

    public void ignore(Player player, Player target){
        if(IGNORED.containsKey(player.getUniqueId())) IGNORED.get(player.getUniqueId()).add(target.getUniqueId());
        else {

            List<UUID> list = new ArrayList<>();
            list.add(target.getUniqueId());

            IGNORED.put(player.getUniqueId(), list);

        }
    }

    public void unignore(Player player, Player target){
        if(IGNORED.containsKey(player.getUniqueId())) IGNORED.get(player.getUniqueId()).remove(target.getUniqueId());
    }

    public void load(){
        FileConfiguration config = Citadel.getInstance().getConfig();

        if(config.contains("users")){
            ConfigurationSection section = config.getConfigurationSection("users");

            for(String s : section.getKeys(false)){
                UUID uuid = UUID.fromString(s);

                if(section.contains(s + ".ignored")){
                    List<UUID> ignored = new ArrayList<>();
                    for(String str : section.getStringList(s + ".ignored")) ignored.add(UUID.fromString(str));

                    IGNORED.put(uuid, ignored);
                }
            }
        }
    }

    public void save(){
        FileConfiguration config = Citadel.getInstance().getConfig();

        for(Map.Entry<UUID, List<UUID>> entry : IGNORED.entrySet()){
            List<String> list = new ArrayList<>();
            for(UUID uuid : entry.getValue()) list.add(uuid.toString());

            config.set("users." + entry.getKey().toString() + ".ignored", list);
        }
    }
}
