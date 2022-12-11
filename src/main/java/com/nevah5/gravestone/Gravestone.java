package com.nevah5.gravestone;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Gravestone extends JavaPlugin implements Listener{
    HashMap<String, GravestoneDeath> gravestones = new HashMap<>();

    @Override
    public void onEnable(){
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent playerDeathEvent){
        List<ItemStack> drops = new ArrayList<>(playerDeathEvent.getDrops());
        playerDeathEvent.getDrops().clear();

        int x = playerDeathEvent.getEntity().getLocation().getBlockX();
        int y = playerDeathEvent.getEntity().getLocation().getBlockY();
        int z = playerDeathEvent.getEntity().getLocation().getBlockZ();
        UUID uuid = playerDeathEvent.getEntity().getUniqueId();

        GravestoneDeath gravestoneDeath = new GravestoneDeath(drops, x, y, z, uuid);
        gravestones.put(gravestoneDeath.getLocationString(), gravestoneDeath);
    }
}
