package org.systic.citadel.generic;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.systic.citadel.Citadel;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayTimeManager implements Listener{

    private final Map<UUID, Long> login;
    private final Map<UUID, Integer> time;

    public PlayTimeManager() {
        Bukkit.getPluginManager().registerEvents(this, Citadel.getInstance());
        login = new HashMap<>();
        time = new HashMap<>();
    }

    public int getPlayTime(UUID uuid){
        int timer = 0;

        if(login.containsKey(uuid)) {
            timer += (int) (System.currentTimeMillis() - login.get(uuid)) / 1000;
        }

        if(time.containsKey(uuid)){
            timer += time.get(uuid);
        }

        return timer;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        login.put(e.getPlayer().getUniqueId(), System.currentTimeMillis());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        UUID uuid = e.getPlayer().getUniqueId();

        int timer = 0;

        if(login.containsKey(uuid)) {
            timer += (int) (System.currentTimeMillis() - login.get(uuid)) / 1000;
        }

        if(time.containsKey(uuid)){
            timer += time.get(uuid);
        }

        time.put(uuid, timer);
    }

    public void load(){
        FileConfiguration config = Citadel.getInstance().getConfig();

        if(config.contains("users")){
            ConfigurationSection section = config.getConfigurationSection("users");

            for(String s : section.getKeys(false)){
                UUID uuid = UUID.fromString(s);

                if(section.contains(s + ".playtime")){
                    time.put(uuid, section.getInt(s + ".playtime"));
                }
            }
        }
    }

    public void save() {
        FileConfiguration config = Citadel.getInstance().getConfig();

        for (Map.Entry<UUID, Long> entry : login.entrySet()) {
            config.set("users." + entry.getKey().toString() + ".playtime", getPlayTime(entry.getKey()));
        }
    }

}
