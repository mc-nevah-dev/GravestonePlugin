package com.nevah5.gravestone.configs;

import com.nevah5.gravestone.Gravestone;
import com.nevah5.gravestone.models.GravestoneDeath;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerialization;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class GravestoneConfigs {
    private final static String GRAVESTONE_DATA_PATH = "gravestones";

    private final File file;
    private final FileConfiguration config;
    private Map<String, GravestoneDeath> gravestones;

    public GravestoneConfigs(Gravestone plugin){
        ConfigurationSerialization.registerClass(GravestoneDeath.class);

        file = new File(plugin.getDataFolder(), "data.yml");
        config = YamlConfiguration.loadConfiguration(file);

        this.gravestones = Optional.ofNullable(config.getConfigurationSection(GRAVESTONE_DATA_PATH))
                .map(section -> section.getValues(true))
                .map(Map::entrySet).map(GravestoneConfigs::toGravestoneMap)
                .orElseGet(HashMap::new);
    }

    public void save(){
        config.createSection(
                GRAVESTONE_DATA_PATH,
                gravestones
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

    private static Map<String, GravestoneDeath> toGravestoneMap(final Set<Map.Entry<String, Object>> entrySet) {
        return entrySet.stream()
                .map(entry -> Map.entry(entry.getKey(), (GravestoneDeath) entry.getValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
