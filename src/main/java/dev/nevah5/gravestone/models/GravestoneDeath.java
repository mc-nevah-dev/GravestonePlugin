package dev.nevah5.gravestone.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@SerializableAs("dev.nevah5.gravestone.models.GravestoneDeath")
@AllArgsConstructor
public class GravestoneDeath implements ConfigurationSerializable {
    @Getter
    private List<ItemStack> items;
    private int x;
    private int y;
    private int z;
    @Getter
    private UUID uuid;
    public String getLocationString() { return x+";"+y+";"+z; }

    @Override
    @NonNull
    public Map<String, Object> serialize() {
        final HashMap<String, Object> map = new HashMap<>();
        map.put("items", items);
        map.put("x", x);
        map.put("y", y);
        map.put("z", z);
        map.put("uuid", uuid.toString());
        return map;
    }

    @SuppressWarnings("unchecked")
    public static GravestoneDeath deserialize(final Map<String, Object> args) {
        return new GravestoneDeath(
                (List<ItemStack>) args.get("items"),
                (int) args.get("x"),
                (int) args.get("y"),
                (int) args.get("z"),
                UUID.fromString((String) args.get("uuid")));
    }
}
