package org.systic.citadel.statistics;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.systic.citadel.Citadel;

public class StatisticManager implements Listener {

    public StatisticManager(){
        Bukkit.getPluginManager().registerEvents(this, Citadel.getInstance());
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        int count = Bukkit.getServer().getOnlinePlayers().length;
        if(count > Statistics.getInstance().PEAK_PLAYER_COUNT) Statistics.getInstance().PEAK_PLAYER_COUNT = count;
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e){
        PlayerStatistics.get(e.getEntity()).death_count++;
        Statistics.getInstance().DEATH_COUNT++;

        if(e.getEntity().getKiller() != null){
            PlayerStatistics.get(e.getEntity().getKiller()).kill_count++;
            Statistics.getInstance().KILL_COUNT++;
        }
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e){
        if(e.getDamager() instanceof Player && e.getEntity() instanceof Player){
            PlayerStatistics.get((Player)e.getDamager()).hit_count++;
            Statistics.getInstance().HIT_COUNT++;
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        if(e.getAction() == Action.LEFT_CLICK_AIR){
            PlayerStatistics.get(e.getPlayer()).miss_count++;
            Statistics.getInstance().MISS_COUNT++;
        }
    }

}
