package org.systic.citadel;

import org.bukkit.ChatColor;
import org.systic.citadel.util.Time;

import java.text.DecimalFormat;

public class Common {

    public static String NO_PERMISSION = ChatColor.RED + "You do not have permission to execute this command.";
    public static String PLAYER_ONLY = ChatColor.RED + "Only a player can execute this command.";

    public static DecimalFormat DECIMAL_FORMAT00x00 = new DecimalFormat("00.00");
    public static DecimalFormat DECIMAL_FORMAT0x00 = new DecimalFormat("0.00");
    public static DecimalFormat DECIMAL_FORMAT0x0 = new DecimalFormat("0.0");

    public static Time LONG_TIME = new Time().includeWeeks(true).includeDays(true).includeHours(true)
            .includeMinutes(true).useFullNames(true);
    public static Time HOUR_TIME = new Time().includeHours(true).includeMinutes(true).useColons(true).showDecimal(true);

}
