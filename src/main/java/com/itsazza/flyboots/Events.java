package com.itsazza.flyboots;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class Events implements Listener {
    static NamespacedKey key = new NamespacedKey(FlyBoots.instance, "fly");

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (player.getGameMode() != GameMode.SURVIVAL) return;

        ItemStack item = player.getInventory().getBoots();

        if (item == null || item.getType() == Material.AIR) return;
        if (player.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() == Material.AIR && hasFlight(item)) {
            player.setAllowFlight(true);
            player.setFlying(true);
        }
    }

    public static boolean hasFlight(ItemStack item) {
        if (isAirOrNull(item)) return false;
        if (!item.hasItemMeta()) return false;
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        Integer value = meta.getPersistentDataContainer().get(key, PersistentDataType.INTEGER);
        if (value == null) return false;
        return (value == 1);
    }

    public static boolean isAirOrNull(ItemStack item){
        return item == null || item.getType().equals(Material.AIR);
    }
}
