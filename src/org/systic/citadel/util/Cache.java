package org.systic.citadel.util;

import java.io.File;
import java.util.Map;

public abstract class Cache<K, V> {

    public abstract Map<K, V> getCachedData();

    public abstract File getFile();
    public abstract void setFile(File file);

    public abstract void save();
    public abstract void load();

    public boolean contains(K k){
        return getCachedData().containsKey(k);
    }

    public void add(K k, V v){
        if(getCachedData().containsKey(k)) getCachedData().remove(k);
        getCachedData().put(k, v);
    }

    public void remove(K k){
        if(getCachedData().containsKey(k)) getCachedData().remove(k);
    }

    public V get(K k){
        return getCachedData().get(k);
    }

}