package com.nevah5.gravestone;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.Listener;

import java.util.*;

public class Gravestone extends JavaPlugin implements Listener{
    private HashMap<String, GravestoneDeath> gravestones = new HashMap<>();

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

    @EventHandler
    public void onInteractEvent(PlayerInteractEvent playerInteractEvent){
        if(!playerInteractEvent.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
        // check if gravestone
        if(Objects.requireNonNull(playerInteractEvent.getClickedBlock()).getType() != Material.BEDROCK) return;
        int x = playerInteractEvent.getClickedBlock().getX();
        int y = playerInteractEvent.getClickedBlock().getY();
        int z = playerInteractEvent.getClickedBlock().getZ();
        String key = x+"."+y+"."+z;

        GravestoneDeath gravestone = gravestones.get(key);
        if(gravestone == null) return;
        if(gravestone.getUuid() != playerInteractEvent.getPlayer().getUniqueId()) return;

        // player is owner of gravestone
        //give items to owner
        Bukkit.broadcastMessage(gravestone.getItems().toString());
    }
}
