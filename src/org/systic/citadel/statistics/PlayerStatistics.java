package org.systic.citadel.statistics;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.systic.citadel.Citadel;
import org.systic.citadel.annotation.Node;
import org.systic.citadel.util.Configurable;

public class PlayerStatistics extends Configurable{

	private static final File folder = new File(Citadel.getInstance().getDataFolder(), "profiles");
    private static final Map<UUID, PlayerStatistics> statistics = new HashMap<>();

    @Node(path = "statistics.hit-count")
    public int hit_count;
    
    @Node(path = "statistics.miss-count")
    public int miss_count;
    
    @Node(path = "statistics.kill-count")
    public int kill_count;
    
    @Node(path = "statistics.death-count")
    public int death_count;

    private PlayerStatistics(UUID uuid){
    	super(new File(folder, uuid.toString() + ".yml"));
        statistics.put(uuid, this);
    }

    public static PlayerStatistics get(Player player){
        if(statistics.containsKey(player.getUniqueId()))
            return statistics.get(player.getUniqueId());

        return new PlayerStatistics(player.getUniqueId());
    }
    
    public static void saveAll() {
    	for(PlayerStatistics stats : statistics.values()) stats.save();
    }

}
