package com.nevah5.gravestone;

import com.nevah5.gravestone.commands.GravestoneCommand;
import com.nevah5.gravestone.configs.GravestonesConfig;
import com.nevah5.gravestone.inventories.GravestoneInventory;
import com.nevah5.gravestone.models.GravestoneDeath;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.Listener;

import java.util.*;

public class Gravestone extends JavaPlugin implements Listener{
    GravestonesConfig gravestoneConfigs = new GravestonesConfig(this);

    @Override
    public void onEnable(){
        Bukkit.getPluginManager().registerEvents(this, this);
        this.getCommand("gravestones").setExecutor(new GravestoneCommand());
    }

    @Override
    public void onDisable() {
        this.gravestoneConfigs.save();
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent playerDeathEvent){
        List<ItemStack> drops = new ArrayList<>(playerDeathEvent.getDrops());
        playerDeathEvent.getDrops().clear();

        int x = playerDeathEvent.getEntity().getLocation().getBlockX();
        int y = playerDeathEvent.getEntity().getLocation().getBlockY();
        int z = playerDeathEvent.getEntity().getLocation().getBlockZ();
        UUID uuid = playerDeathEvent.getEntity().getUniqueId();

        if(playerDeathEvent.getEntity().getInventory().isEmpty()) return;

        // place bedrock at location
        int maxWorldHeight = playerDeathEvent.getEntity().getWorld().getMaxHeight();
        Block gravestoneLocation = playerDeathEvent.getEntity().getWorld().getBlockAt(x, y, z);
        while(gravestoneLocation.getType() != Material.AIR && y <= maxWorldHeight){
            y++;
            gravestoneLocation = playerDeathEvent.getEntity().getWorld().getBlockAt(x, y, z);
        }
        if(y <= maxWorldHeight) {
            gravestoneLocation.setType(Material.BEDROCK);

            // print to user
            playerDeathEvent.getEntity().sendMessage(ChatColor.WHITE + "Your gravestone spawned at: "+ChatColor.AQUA + x + " " + y + " " + z);

            // add gravestone death into store
            GravestoneDeath gravestoneDeath = new GravestoneDeath(drops, x, y, z, uuid);
            gravestoneConfigs.add(gravestoneDeath.getLocationString(), gravestoneDeath);
        } else {
            // TODO: store failed
            // gravestonesFailes.add(new GravestoneDeathFail(drops, x, z, uuid));
            playerDeathEvent.getEntity().sendMessage(ChatColor.RED + "Your gravestone could not spawn. Your items have been stored. Please get in contact with an Administrator to retrieve them.");
        }
    }

    @EventHandler
    public void onInteractEvent(PlayerInteractEvent playerInteractEvent){
        if(!playerInteractEvent.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
        // check if gravestone
        if(Objects.requireNonNull(playerInteractEvent.getClickedBlock()).getType() != Material.BEDROCK) return;
        int x = playerInteractEvent.getClickedBlock().getX();
        int y = playerInteractEvent.getClickedBlock().getY();
        int z = playerInteractEvent.getClickedBlock().getZ();
        String key = x+";"+y+";"+z;

        GravestoneDeath gravestone = gravestoneConfigs.getByKey(key);
        if(gravestone == null) return;
        if(gravestone.uuid != playerInteractEvent.getPlayer().getUniqueId()) return;

        // player is owner of gravestone
        // give items to owner
//        HashMap<Integer, ItemStack> overflowItems = playerInteractEvent.getPlayer().getInventory().addItem(gravestone.items.toArray(new ItemStack[0]));

        // clear old gravestone
//        gravestoneConfigs.removeByKey(key);

        // create a new gravestone if overflow items exist
//        List<ItemStack> overflowItemsList = new ArrayList<>();
        new GravestoneInventory(playerInteractEvent.getPlayer(), playerInteractEvent.getPlayer().getInventory()).openInventory();
//        overflowItems.forEach((integer, itemStack) -> overflowItemsList.add(itemStack));
//        if(overflowItemsList.size() != 0) gravestoneConfigs.add(key, new GravestoneDeath(overflowItemsList, x, y, z, playerInteractEvent.getPlayer().getUniqueId()));
        // if no overflow items, remove
//        if(overflowItemsList.size() == 0) playerInteractEvent.getPlayer().getWorld().getBlockAt(x, y, z).setType(Material.AIR);

        // cancel the event to prevent block placement if holding block
        playerInteractEvent.setCancelled(true);
    }
}
