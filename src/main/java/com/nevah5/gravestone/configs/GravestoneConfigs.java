package com.nevah5.gravestone.configs;

import com.google.gson.Gson;
import com.nevah5.gravestone.models.GravestoneDeath;
import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.Map;

public class GravestoneConfigs {
    Gson gson = new Gson();
    public void storeGravestones(HashMap<String, GravestoneDeath> gravestoneDeathHashMap){
        Map<String, GravestoneDeath> gravestoneDeaths = new HashMap<>(gravestoneDeathHashMap);
        String json = gson.toJson(gravestoneDeaths);
        Bukkit.broadcastMessage(json);
    }
}
