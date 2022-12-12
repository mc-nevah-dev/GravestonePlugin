package com.nevah5.gravestone.configs;

import com.nevah5.gravestone.Gravestone;
import com.nevah5.gravestone.models.GravestoneDeath;
import com.nevah5.gravestone.models.GravestoneDeathFail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GravestoneConfigs {
    private Gravestone plugin;
    private HashMap<String, GravestoneDeath> gravestones = new HashMap<>();
    private List<GravestoneDeathFail> gravestonesFailes = new ArrayList<>();

    public GravestoneConfigs(Gravestone plugin){
        this.plugin = plugin;
    }

    public GravestoneDeath getByKey(String key){
        return gravestones.get(key);
    }

    public void removeByKey(String key){
        gravestones.remove(key);
    }

    public void add(String key, GravestoneDeath gd){
        gravestones.put(key, gd);
    }
}
