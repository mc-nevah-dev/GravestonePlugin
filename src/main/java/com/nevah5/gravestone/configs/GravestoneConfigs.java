package com.nevah5.gravestone.configs;

import com.nevah5.gravestone.Gravestone;
import com.nevah5.gravestone.models.GravestoneDeath;
import com.nevah5.gravestone.models.GravestoneDeathFail;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class GravestoneConfigs {
    private File file;
    private FileConfiguration cfg;
    private HashMap<String, GravestoneDeath> gravestones = new HashMap<>();
    private List<GravestoneDeathFail> gravestonesFailes = new ArrayList<>();

    public GravestoneConfigs(Gravestone plugin){
        this.file = new File(plugin.getDataFolder(), "gravestones.yml");
        this.cfg = YamlConfiguration.loadConfiguration(this.file);
        //this.players = (Map)Optional.ofNullable(this.cfg.getConfigurationSection("data")).map((configurationSection) -> {
        //    return (Map)configurationSection.getValues(false).entrySet().stream().map(MaxHealthTools::mapEntry).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        //}).orElseGet(HashMap::new);
    }

    public void save(){
        this.cfg.createSection("gravestones", gravestones.entrySet().stream().map(GravestoneConfigs::mapEntryReverse).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
        try{
            this.cfg.save(this.file);
        } catch (IOException e){
            return;
        }
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

    public static Map.Entry<String, Object> mapEntryReverse(Map.Entry<String, GravestoneDeath> entry){
        return Map.entry(entry.getKey(), entry.getValue().getLocationString());
    }
}
