package com.nevah5.gravestone.models;

import lombok.AllArgsConstructor;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
public class GravestoneDeath {
    public List<ItemStack> items;
    public int x;
    public int y;
    public int z;
    public UUID uuid;
    public String getLocationString() { return x+";"+y+";"+z; }
}
