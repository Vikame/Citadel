package org.systic.citadel.util;

import org.bukkit.configuration.file.YamlConfiguration;
import org.systic.citadel.annotation.Node;
import org.systic.citadel.generic.CitadelException;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class Configurable {

    protected final File file;
    protected final YamlConfiguration config;

    public Configurable(File file){
        if(file == null) throw new CitadelException("File not set for Configurable.");
        this.file = file;

        config = YamlConfiguration.loadConfiguration(file);

        for(Field field : getClass().getDeclaredFields()){
            if(field.isAnnotationPresent(Node.class)){
                if(Modifier.isFinal(field.getModifiers())) return;
                if(!field.isAccessible()) field.setAccessible(true);

                try {
                    Node node = field.getAnnotationsByType(Node.class)[0];
                    if(!config.contains(node.path())) continue;

                    field.set((Modifier.isStatic(field.getModifiers()) ? null : this), config.get(node.path()));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void save(){
        for(Field field : getClass().getDeclaredFields()){
            if(field.isAnnotationPresent(Node.class)){
                if(Modifier.isFinal(field.getModifiers())) return;
                if(!field.isAccessible()) field.setAccessible(true);

                try {
                    config.set(field.getAnnotationsByType(Node.class)[0].path(), field.get((Modifier.isStatic(field.getModifiers()) ? null : this)));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
