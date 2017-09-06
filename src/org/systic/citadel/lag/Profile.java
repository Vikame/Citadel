package org.systic.citadel.lag;

import org.bukkit.Material;

public class Profile {

    public final String name;
    public final Material icon;

    public long ns;

    public Profile(String name, Material icon){
        this.name = name;
        this.icon = icon;
    }
}
