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

public class GravestonesConfig {
    private final static String GRAVESTONE_DATA_PATH = "gravestones";

    private final File file;
    private final FileConfiguration config;
    private Map<String, GravestoneDeath> gravestones;

    public GravestonesConfig(Gravestone plugin){
        file = new File(plugin.getDataFolder(), "data.yml");
        config = YamlConfiguration.loadConfiguration(file);

        // TODO: READ
//        gravestones = Optional.ofNullable(config.getConfigurationSection(GRAVESTONE_DATA_PATH))
//                .map(configurationSection -> configurationSection.getValues(false)
//                        .entrySet()
//                        .stream()
//                        .map(GravestonesConfig::mapEntry)
//                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)))
//                .orElseGet(HashMap::new);
//        System.out.println("output:");
//        gravestones.forEach((String key, GravestoneDeath gd) -> System.out.println(gd));
    }

    public void save(){
        config.createSection(
                GRAVESTONE_DATA_PATH,
                gravestones.entrySet().stream()
                        .map(GravestonesConfig::mapEntryReverse)
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))
        );
        try {
            config.save(file);
        } catch (IOException e){
            System.out.println(e.getMessage());
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

    private static Map.Entry<String, GravestoneDeath> mapEntry(final Map.Entry<String, Object> entry) {
        return Map.entry(
                ((GravestoneDeath) entry.getValue()).getLocationString(),
                (GravestoneDeath) entry.getValue()
        );
    }

    private static Map.Entry<String, Object> mapEntryReverse(final Map.Entry<String, GravestoneDeath> entry) {
        return Map.entry(
                entry.getKey(),
                entry.getValue()
        );
    }
}
