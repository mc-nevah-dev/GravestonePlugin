package com.nevah5.gravestone.models;

import lombok.Getter;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.UUID;

/**
 * Gravestones that failed to spawn
 * @param items
 * @param x
 * @param z
 * @param uuid
 */
public record GravestoneDeathFail(@Getter List<ItemStack> items, int x, int z, @Getter UUID uuid) {
}
