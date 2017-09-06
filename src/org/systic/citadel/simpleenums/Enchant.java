package org.systic.citadel.simpleenums;

import org.bukkit.enchantments.Enchantment;

public enum Enchant {

    PROTECTION(Enchantment.PROTECTION_ENVIRONMENTAL, "Protection", "Prot"),
    FIRE_PROTECTION(Enchantment.PROTECTION_FIRE, "Fire Protection", "Fire Prot"),
    FEATHER_FALLING(Enchantment.PROTECTION_FALL, "Feather Falling"),
    BLAST_PROTECTION(Enchantment.PROTECTION_EXPLOSIONS, "Blast Protection", "Blast Prot"),
    PROJECTILE_PROTECTION(Enchantment.PROTECTION_PROJECTILE, "Projectile Protection", "Projectile Prot", "Proj Prot"),
    RESPIRATION(Enchantment.OXYGEN, "Respiration"),
    AQUA_AFFINITY(Enchantment.WATER_WORKER, "Aqua Affinity"),
    THORNS(Enchantment.THORNS, "Thorns"),
//    DEPTH_STRIDER(Enchantment.DEPTH_STRIDER, "Depth Strider"),
    SHARPNESS(Enchantment.DAMAGE_ALL, "Sharpness", "Sharp"),
    SMITE(Enchantment.DAMAGE_UNDEAD, "Smite"),
    BANE_OF_ARTHROPODS(Enchantment.DAMAGE_ARTHROPODS, "Bane of Arthropods", "Bane"),
    KNOCKBACK(Enchantment.KNOCKBACK, "Knockback", "KB"),
    FIRE_ASPECT(Enchantment.FIRE_ASPECT, "Fire Aspect", "Fire"),
    LOOTING(Enchantment.LOOT_BONUS_MOBS, "Looting"),
    EFFICIENCY(Enchantment.DIG_SPEED, "Efficiency"),
    SILK_TOUCH(Enchantment.SILK_TOUCH, "Silk Touch", "Silk"),
    UNBREAKING(Enchantment.DURABILITY, "Unbreaking"),
    FORTUNE(Enchantment.LOOT_BONUS_BLOCKS, "Fortune"),
    POWER(Enchantment.ARROW_DAMAGE, "Power"),
    PUNCH(Enchantment.ARROW_KNOCKBACK, "Punch"),
    FLAME(Enchantment.ARROW_FIRE, "Flame"),
    INFINITY(Enchantment.ARROW_INFINITE, "Infinity"),
    LUCK_OF_THE_SEA(Enchantment.LUCK, "Luck of the Sea", "Luck"),
    LURE(Enchantment.LURE, "Lure");


    private Enchantment bukkitVersion;
    private String[] simple;

    Enchant(Enchantment bukkitVersion, String... simple){
        this.bukkitVersion = bukkitVersion;
        this.simple = simple;
    }

    public Enchantment getBukkitVersion(){
        return this.bukkitVersion;
    }

    public String getSimpleName(){
        return simple.length <= 0 ? "null" : simple[0];
    }

    public static Enchant getEnchantByName(String name){
        for(Enchant e : Enchant.values()){
            for(String s : e.simple){
                if(s.equalsIgnoreCase(name)) return e;
            }

            if(e.name().equalsIgnoreCase(name)) return e;
            if(e.bukkitVersion.getName().equalsIgnoreCase(name)) return e;
        }
        return null;
    }

}