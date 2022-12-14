package com.nevah5.gravestone.inventories;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GravestoneInventory implements Listener {
    private final List<Integer> SKIP_SLOTS = Arrays.asList(5, 6, 7, 8);

    private final Inventory inv;
    private Player player;
    private PlayerInventory playerInventory;

    public GravestoneInventory(Player player, PlayerInventory playerInventory){
        this.player = player;
        this.playerInventory = playerInventory;
        inv = Bukkit.createInventory(null, 45, String.format("%s's Inventory", player.getName()));

        initializeItems();
    }

    public void initializeItems() {
        // set fixed slots
        for (int i = 0; i < SKIP_SLOTS.size(); i++){
            inv.setItem(SKIP_SLOTS.get(i), placeholderItem());
        }

        for (int i = 0; i < playerInventory.getContents().length; i++){
            if(!SKIP_SLOTS.contains(i)) {
                inv.setItem(i, playerInventory.getItem(i));
            }
        }
    }

    protected ItemStack placeholderItem(){
        ItemStack item = new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.DARK_GRAY + "");
        return item;
    }

    protected ItemStack createGuiItem(final Material material, final String name, final String... lore) {
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();

        // Set the name of the item
        meta.setDisplayName(name);

        // Set the lore of the item
        meta.setLore(Arrays.asList(lore));

        item.setItemMeta(meta);

        return item;
    }

    public void openInventory() {
        player.openInventory(inv);
    }

    @EventHandler
    public void onInventoryClick(final InventoryClickEvent e) {
        if (!e.getInventory().equals(inv)) return;

        e.setCancelled(true);

        final ItemStack clickedItem = e.getCurrentItem();

        // verify current item is not null
        if (clickedItem == null || clickedItem.getType().isAir()) return;

        final Player p = (Player) e.getWhoClicked();

        // Using slots click is a best option for your inventory click's
        p.sendMessage("You clicked at slot " + e.getRawSlot());
    }

    // Cancel dragging in our inventory
    @EventHandler
    public void onInventoryClick(final InventoryDragEvent e) {
        if (e.getInventory().equals(inv)) {
            e.setCancelled(true);
        }
    }
}
