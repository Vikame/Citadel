package org.systic.citadel.commands.impl;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.systic.citadel.commands.CommandBase;
import org.systic.citadel.simpleenums.Enchant;
import org.systic.citadel.util.Alerts;
import org.systic.citadel.util.C;

public class EnchantCommand extends CommandBase {

    public EnchantCommand() {
        super("enchant", "systic.enchant", true, "ench");
    }

    @Override
    public void run(CommandSender sender, String label, String[] args) {
        Player p = (Player) sender;

        if (args.length <= 1) {
            p.sendMessage(C.c("&cUsage: /" + label + " <enchantment> <level>"));
            return;
        }

        String en = "";

        for(int i = 0; i < args.length - 1; i++){
            en += args[i] + " ";
        }

        en = en.trim();

        Enchant enchant = Enchant.getEnchantByName(en);
        if(enchant == null){
            p.sendMessage(C.c("&cCould not find the enchantment '" + en + "'."));
            return;
        }

        int level = 0;
        try{
            level = Integer.parseInt(args[args.length-1]);
        }catch(NumberFormatException e){
            p.sendMessage(C.c("&c" + args[1] + " is not a number."));
            return;
        }

        ItemStack i = p.getItemInHand();
        if(i == null || i.getType() == Material.AIR){
            p.sendMessage(C.c("&cThere is nothing in your hand to enchant."));
            return;
        }

        Enchantment ench = enchant.getBukkitVersion();

        boolean alert_level = ench.getMaxLevel() < level;
        EnchantmentTarget target = ench.getItemTarget();
        boolean alert_applied = target != null && !target.includes(i.getType());

        if(level == 0){
            if(i.containsEnchantment(ench)){
                i.removeEnchantment(ench);

                p.sendMessage(C.c("&6Removed &f" + enchant.getSimpleName() + " &6from the item in your hand."));
                Alerts.alert(p, "Removed enchantment " + enchant.getSimpleName());
            }else {
                p.sendMessage(C.c("&cThe item you are holding does not contain that enchantment."));
            }
        }else{
            i.addUnsafeEnchantment(ench, level);
            if(alert_level){
                p.sendMessage(C.c("&c" + enchant.getSimpleName() + "'s max level is " + ench.getMaxLevel() + " but level " + level + " has been applied."));
            }
            if(alert_applied){
                p.sendMessage(C.c("&cThat enchantment would not normally be on that item. It has been applied anyways."));
            }

            p.sendMessage(C.c("&6Applied &f" + enchant.getSimpleName() + " &6level &f" + level + " &6to the item in your hand."));
            Alerts.alert(p, "Applied enchantment " + enchant.getSimpleName() + " level " + level);
        }
    }

}
