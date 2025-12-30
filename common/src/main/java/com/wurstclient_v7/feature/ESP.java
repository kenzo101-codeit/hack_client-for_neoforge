package com.wurstclient_v7.feature;

import com.wurstclient_v7.config.ConfigManager;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.animal.Animal;

public final class ESP {
    private static boolean enabled = ConfigManager.getBoolean("esp.enabled", false);

    private ESP() { }

    public static boolean isEnabled() { return enabled; }

    public static void toggle() {
        enabled = !enabled;
        ConfigManager.setBoolean("esp.enabled", enabled);
    }

    public static boolean shouldGlow(Entity entity) {
        if (!enabled) return false;
        if (entity == Minecraft.getInstance().player) return false;

        return entity instanceof Player || entity instanceof Monster || entity instanceof Animal;
    }

    // This returns the color in Decimal (Hex) format
    public static int getGlowColor(Entity entity) {
        if (entity instanceof Player) return 0x55FF55;   // Light Green for Players
        if (entity instanceof Monster) return 0xFF5555;  // Light Red for Hostiles
        if (entity instanceof Animal) return 0xFFFF55;   // Yellow for Neutrals/Animals
        return 0xFFFFFF; // White default
    }
}