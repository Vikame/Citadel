package org.systic.citadel.settings;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.systic.citadel.Citadel;
import org.systic.citadel.event.CitadelEventHelper;
import org.systic.citadel.util.C;
import org.systic.citadel.util.Ints;
import org.systic.citadel.util.Strings;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerSettings {

    public static final Map<String, Boolean> DEFAULTS = new HashMap<>();
    public static final Map<String, String> PERMISSIONS = new HashMap<>();
    public static final Map<String, Material> ENABLED_MATERIALS = new HashMap<>();
    public static final Map<String, Material> DISABLED_MATERIALS = new HashMap<>();
    public static final Map<String, String> ENABLED_NAMES = new HashMap<>();
    public static final Map<String, String> DISABLED_NAMES = new HashMap<>();
    private static final Map<UUID, PlayerSettings> SETTINGS = new HashMap<>();

    public final UUID uuid;
    public Map<String, Boolean> settings;

    private PlayerSettings(UUID uuid){
        this.uuid = uuid;
        this.settings = new HashMap<>();

        FileConfiguration config = Citadel.getInstance().getConfig();

        if(config.contains("users." + uuid.toString())) {
            ConfigurationSection section = config.getConfigurationSection("users." + uuid.toString());

            for(String s : section.getKeys(false)){
                if(DEFAULTS.containsKey(s)) {
                    settings.put(s, section.getBoolean(s));
                }
            }
        }

        for(Map.Entry<String, Boolean> entry : DEFAULTS.entrySet()){
            if(!settings.containsKey(entry.getKey().toLowerCase())) settings.put(entry.getKey().toLowerCase(), entry.getValue());
        }

        SETTINGS.put(uuid, this);
    }

    public ItemStack[] getContents(){
        Map<Integer, ItemStack> map = new HashMap<>();

        int row = 1;

        int index = 1;
        for(Map.Entry<String, Boolean> entry : settings.entrySet()){

            if(PERMISSIONS.containsKey(entry.getKey())){
                Player p = Bukkit.getPlayer(uuid);
                if(p != null && p.isOnline()){
                    if(!p.hasPermission(PERMISSIONS.get(entry.getKey()))) continue;
                }
            }

            Material material;
            if(entry.getValue()){
                if(ENABLED_MATERIALS.containsKey(entry.getKey())) material = ENABLED_MATERIALS.get(entry.getKey());
                else material = Material.SLIME_BALL;
            }else{
                if(DISABLED_MATERIALS.containsKey(entry.getKey())) material = DISABLED_MATERIALS.get(entry.getKey());
                else material = Material.MAGMA_CREAM;
            }

            ItemStack item = new ItemStack(material);
            ItemMeta meta = item.getItemMeta();

            String name;
            if(entry.getValue()){
                if(ENABLED_NAMES.containsKey(entry.getKey())) name = ENABLED_NAMES.get(entry.getKey());
                else name = "&a" + Strings.upperCamelCase(entry.getKey());
            }else{
                if(DISABLED_NAMES.containsKey(entry.getKey())) name = DISABLED_NAMES.get(entry.getKey());
                else name = "&c" + Strings.upperCamelCase(entry.getKey());
            }

            meta.setDisplayName(C.c(name));

            item.setItemMeta(meta);

            map.put(index+((row * 9) - 9), item);

            index += 2;
            if(index > 9){
                row++;
                if(row % 2 == 0) {
                    index = 2;
                }else index = 1;
            }
        }

        ItemStack[] array = new ItemStack[Ints.roundUpToNearest(row * 9, 9)];

        for(Map.Entry<Integer, ItemStack> item : map.entrySet()){
            array[item.getKey()] = item.getValue();
        }

        return array;
    }

    public Inventory toInventory(){
        ItemStack[] array = getContents();

        Inventory inv = Bukkit.createInventory(null, array.length, C.c("&0Settings"));
        inv.setContents(array);

        return inv;
    }

    public boolean get(String key){
        key = key.toLowerCase();

        if(PERMISSIONS.containsKey(key)) {
            Player p = Bukkit.getPlayer(uuid);
            if (p != null && p.isOnline()) {
                if (!p.hasPermission(PERMISSIONS.get(key))) return false;
            }
        }

        return settings.containsKey(key) ? settings.get(key) : true;
    }

    public boolean get(String key, boolean def){
        key = key.toLowerCase();

        if(PERMISSIONS.containsKey(key)) {
            Player p = Bukkit.getPlayer(uuid);
            if (p != null && p.isOnline()) {
                if (!p.hasPermission(PERMISSIONS.get(key))) return false;
            }
        }

        return settings.containsKey(key) ? settings.get(key) : def;
    }

    public void set(String key, boolean val){
        key = key.toLowerCase();

        settings.put(key, val);

        CitadelEventHelper.callSettingEvent(Bukkit.getPlayer(uuid), key, val);
    }

    public boolean toggle(String key) {
        boolean val = !get(key);

        set(key, val);

        return val;
    }

    public String getNameOf(String name){
        name = name.toLowerCase();

        String noColor = ChatColor.stripColor(name);
        if(DEFAULTS.containsKey(noColor)) return noColor;

        name = name.replace(ChatColor.COLOR_CHAR, '&');

        for(Map.Entry<String, String> entry : ENABLED_NAMES.entrySet()){
            if(entry.getValue().equalsIgnoreCase(name)) return entry.getKey();
        }
        for(Map.Entry<String, String> entry : DISABLED_NAMES.entrySet()){
            if(entry.getValue().equalsIgnoreCase(name)) return entry.getKey();
        }

        return null;
    }

    public static PlayerSettings get(Player player){
        if(SETTINGS.containsKey(player.getUniqueId()))
            return SETTINGS.get(player.getUniqueId());

        return new PlayerSettings(player.getUniqueId());
    }

    public static void save(){
        FileConfiguration config = Citadel.getInstance().getConfig();

        for(PlayerSettings settings : SETTINGS.values()){
            for(Map.Entry<String, Boolean> entry : settings.settings.entrySet()){
                config.set("users." + settings.uuid.toString() + "." + entry.getKey(), entry.getValue());
            }
        }
    }
}
