package com.nevah5.gravestone;

import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.UUID;

public record GravestoneDeath(List<ItemStack> items, int x, int y, int z, UUID uuid) {
    public String getLocationString() { return x+"."+y+"."+z; }

    public UUID getUuid() { return uuid; }
    public List<ItemStack> getItems() { return items; }
}
