package org.systic.citadel.statistics;

import org.systic.citadel.Citadel;
import org.systic.citadel.annotation.Node;
import org.systic.citadel.util.Configurable;

import java.io.File;

public class Statistics extends Configurable {

    private static Statistics instance;

    @Node(path = "hit-count")
    public int HIT_COUNT = 0;

    @Node(path = "miss-count")
    public int MISS_COUNT = 0;

    @Node(path = "kill-count")
    public int KILL_COUNT = 0;

    @Node(path = "death-count")
    public int DEATH_COUNT = 0;

    @Node(path = "peak-player-count")
    public int PEAK_PLAYER_COUNT = 0;

    private Statistics() {
        super(new File(Citadel.getInstance().getDataFolder(), "statistics.yml"));
        instance = this;
    }

    public static Statistics getInstance(){
        return (instance == null ? new Statistics() : instance);
    }

}
