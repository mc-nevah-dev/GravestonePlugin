package com.nevah5.gravestone;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.Listener;

public class Gravestone extends JavaPlugin implements Listener{
    @Override
    public void onEnable(){
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent deathEvent){
        deathEvent.setDeathMessage(null);
//        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_AQUA + deathEvent.getEntity().getName() + " ist gestorben.");
        Bukkit.broadcastMessage(ChatColor.DARK_AQUA + deathEvent.getEntity().getName() + ChatColor.YELLOW + " isch eifach us de existenz gheit.");
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent joinEvent){
        joinEvent.setJoinMessage(ChatColor.DARK_AQUA+joinEvent.getPlayer().getName() + ChatColor.YELLOW + " het salü gseit.");
    }
}
