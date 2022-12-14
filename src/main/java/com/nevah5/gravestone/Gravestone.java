package com.nevah5.gravestone;

import com.nevah5.gravestone.configs.GravestoneConfigs;
import com.nevah5.gravestone.models.GravestoneDeath;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.Listener;

import java.util.*;

public class Gravestone extends JavaPlugin implements Listener {
    GravestoneConfigs gravestoneConfigs = null;

    @Override
    public void onEnable(){
        this.gravestoneConfigs = new GravestoneConfigs(this);
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        this.gravestoneConfigs.save();
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent playerDeathEvent){
        List<ItemStack> drops = new ArrayList<>(playerDeathEvent.getDrops());

        int x = playerDeathEvent.getEntity().getLocation().getBlockX();
        int y = playerDeathEvent.getEntity().getLocation().getBlockY();
        int initY = playerDeathEvent.getEntity().getLocation().getBlockY();
        int z = playerDeathEvent.getEntity().getLocation().getBlockZ();

        if(playerDeathEvent.getEntity().getInventory().isEmpty()) return;

        // check for possible grave positions
        int maxWorldHeight = playerDeathEvent.getEntity().getWorld().getMaxHeight();
        int minWorldHeight = playerDeathEvent.getEntity().getWorld().getMinHeight();
        Block gravestoneLocation = playerDeathEvent.getEntity().getWorld().getBlockAt(x, y, z);
        while(gravestoneLocation.getType() != Material.AIR && y <= maxWorldHeight){
            y++;
            gravestoneLocation = playerDeathEvent.getEntity().getWorld().getBlockAt(x, y, z);
        }
        if(y <= maxWorldHeight) {
            spawnGravestone(gravestoneLocation, drops, Objects.requireNonNull(playerDeathEvent.getEntity().getPlayer()));

            // prevent drops from dropping where player died
            playerDeathEvent.getDrops().clear();
            return;
        }

        // test if spawn possible below player
        y = playerDeathEvent.getEntity().getLocation().getBlockY(); // reset y
        y--; // test block below death location
        gravestoneLocation = playerDeathEvent.getEntity().getWorld().getBlockAt(x, y, z);
        gravestoneLocation.getType();

        while(gravestoneLocation.getType() != Material.AIR && y >= minWorldHeight){
            y--;
            gravestoneLocation = playerDeathEvent.getEntity().getWorld().getBlockAt(x, y, z);
        }
        if(y >= minWorldHeight){
            spawnGravestone(gravestoneLocation, drops, playerDeathEvent.getEntity());

            playerDeathEvent.getDrops().clear();
            return;
        }
        playerDeathEvent.getEntity().sendMessage(ChatColor.RED + "Your Gravestone couldn't spawn. Your items have dropped where you died.");
    }

    @EventHandler
    public void onInteractEvent(PlayerInteractEvent playerInteractEvent){
        if(playerInteractEvent.getHand() != EquipmentSlot.HAND) return;
        if(!playerInteractEvent.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
        // check if gravestone
        if(Objects.requireNonNull(playerInteractEvent.getClickedBlock()).getType() != Material.BEDROCK) return;
        int x = playerInteractEvent.getClickedBlock().getX();
        int y = playerInteractEvent.getClickedBlock().getY();
        int z = playerInteractEvent.getClickedBlock().getZ();
        String key = x+";"+y+";"+z;

        GravestoneDeath gravestone = gravestoneConfigs.getByKey(key);
        if(gravestone == null) return;
        if(!gravestone.getUuid().equals(playerInteractEvent.getPlayer().getUniqueId())) return;

        // player is owner of gravestone
        // give items to owner
        HashMap<Integer, ItemStack> overflowItems = playerInteractEvent.getPlayer().getInventory().addItem(gravestone.getItems().toArray(new ItemStack[0]));

        // clear old gravestone
        gravestoneConfigs.removeByKey(key);

        // create a new gravestone if overflow items exist
        List<ItemStack> overflowItemsList = new ArrayList<>();
        overflowItems.forEach((integer, itemStack) -> overflowItemsList.add(itemStack));
        if(overflowItemsList.size() != 0) gravestoneConfigs.add(key, new GravestoneDeath(overflowItemsList, x, y, z, playerInteractEvent.getPlayer().getUniqueId()));
        // if no overflow items, remove
        if(overflowItemsList.size() == 0) playerInteractEvent.getPlayer().getWorld().getBlockAt(x, y, z).setType(Material.AIR);

        // cancel the event to prevent block placement if holding block
        playerInteractEvent.setCancelled(true);
    }

    private void spawnGravestone(Block gravestoneLocation, List<ItemStack> drops, Player player){
        int x = gravestoneLocation.getX();
        int y = gravestoneLocation.getY();
        int z = gravestoneLocation.getZ();

        gravestoneLocation.setType(Material.BEDROCK);

        // print to user
        player.sendMessage(ChatColor.WHITE + "Your gravestone spawned at: "+ChatColor.AQUA + x + " " + y + " " + z);

        // add gravestone death into store
        GravestoneDeath gravestoneDeath = new GravestoneDeath(drops, x, y, z, player.getUniqueId());
        gravestoneConfigs.add(gravestoneDeath.getLocationString(), gravestoneDeath);
    }
}
