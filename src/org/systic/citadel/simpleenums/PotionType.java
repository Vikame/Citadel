package org.systic.citadel.simpleenums;

import org.bukkit.Material;
import org.bukkit.potion.PotionEffectType;

public enum PotionType {

    SPEED(PotionEffectType.SPEED, true, "Speed"),
    SLOWNESS(PotionEffectType.SLOW, false, "Slowness"),
    HASTE(PotionEffectType.FAST_DIGGING, true, "Haste"),
    MINING_FATIGUE(PotionEffectType.SLOW_DIGGING, false, "Mining Fatigue"),
    STRENGTH(PotionEffectType.INCREASE_DAMAGE, true, "Strength"),
    INSTANT_HEALTH(PotionEffectType.HEAL, true, "Instant Health"),
    INSTANT_DAMAGE(PotionEffectType.HARM, false, "Instant Damage"),
    JUMP_BOOST(PotionEffectType.JUMP, true, "Jump Boost"),
    CONFUSION(PotionEffectType.CONFUSION, false, "Confusion"),
    REGENERATION(PotionEffectType.REGENERATION, true, "Regeneration"),
    RESISTANCE(PotionEffectType.DAMAGE_RESISTANCE, true, "Resistance"),
    FIRE_RESISTANCE(PotionEffectType.FIRE_RESISTANCE, true, "Fire Resistance"),
    WATER_BREATHING(PotionEffectType.WATER_BREATHING, true, "Water Breathing"),
    INVISIBILITY(PotionEffectType.INVISIBILITY, true, "Invisibility"),
    BLINDNESS(PotionEffectType.BLINDNESS, false, "Blindness"),
    NIGHT_VISION(PotionEffectType.NIGHT_VISION, true, "Night Vision"),
    HUNGER(PotionEffectType.HUNGER, false, "Hunger"),
    WEAKNESS(PotionEffectType.WEAKNESS, false, "Weakness"),
    POISON(PotionEffectType.POISON, false, "Poison"),
    WITHER(PotionEffectType.WITHER, false, "Wither"),
    HEALTH_BOOST(PotionEffectType.HEALTH_BOOST, true, "Health Boost"),
    ABSORPTION(PotionEffectType.ABSORPTION, true, "Absorption"),
    SATURATION(PotionEffectType.SATURATION, true, "Saturation");

    private PotionEffectType bukkitVersion;
    private boolean isBuff;
    private String simple;

    PotionType(PotionEffectType bukkitVersion, boolean isBuff, String simple){
        this.bukkitVersion = bukkitVersion;
        this.isBuff = isBuff;
        this.simple = simple;
    }

    public PotionEffectType getBukkitVersion(){
        return this.bukkitVersion;
    }

    public boolean isBuff(){
        return this.isBuff;
    }

    public boolean isDebuff(){
        return !this.isBuff;
    }

    public String getSimpleName(){
        return simple;
    }

    public static PotionType getByPotionEffectType(PotionEffectType type){
        for(PotionType t : values()){
            if(t.getBukkitVersion().equals(type)) return t;
        }
        return null;
    }

    public static PotionType getPotionTypeByName(String name){
        for(PotionType type : PotionType.values()){
            if(type.simple.equalsIgnoreCase(name) || type.name().equalsIgnoreCase(name)) return type;
        }
        return null;
    }

}