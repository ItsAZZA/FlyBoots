package com.itsazza.flyboots;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public final class FlyBoots extends JavaPlugin {
    public static FlyBoots instance;

    @Override
    public void onEnable() {
        instance = this;

        ItemStack item = new ItemStack(Material.DIAMOND_BOOTS);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§6§lMagical Fly Boots");
        meta.setLore(Arrays.asList(
                "§7Harnesses the power of Aeolus",
                "§7which allows you to fly without",
                "§7hesitation or effort."));

        meta.getPersistentDataContainer().set(new NamespacedKey(this, "fly"), PersistentDataType.INTEGER, 1);
        item.setItemMeta(meta);

        NamespacedKey key = new NamespacedKey(this, "fly_boots");
        ShapedRecipe recipe = new ShapedRecipe(key, item);
        recipe.shape("   ", "DND", "DED");
        recipe.setIngredient('D', Material.DIAMOND_BLOCK);
        recipe.setIngredient('N', Material.NETHER_STAR);
        recipe.setIngredient('E', Material.END_CRYSTAL);

        Bukkit.addRecipe(recipe);

        Bukkit.getServer().getPluginManager().registerEvents(new Events(), this);

        Bukkit.getScheduler().runTaskTimerAsynchronously(this, () -> {
            for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                if (player.getGameMode() != GameMode.SURVIVAL) return;
                ItemStack boots = player.getInventory().getBoots();
                boolean fly = Events.hasFlight(boots);
                if (!fly) player.setFlying(false);
                if (fly && player.getAllowFlight()) return;
                player.setAllowFlight(fly);
            }
        }, 0L, 30L);
    }
}
