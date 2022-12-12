package com.nevah5.gravestone.models;

import lombok.Getter;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.UUID;

/**
 * Gravestones that spawned
 * @param items
 * @param x
 * @param z
 * @param uuid
 */
public record GravestoneDeath(@Getter List<ItemStack> items, int x, int y, int z, @Getter UUID uuid) {
    public String getLocationString() { return x+"."+y+"."+z; }
}
