package com.wurstclient_v7.feature;

import com.wurstclient_v7.config.ConfigManager;
import net.minecraft.client.Minecraft;

public final class Flight {
    private static boolean enabled = ConfigManager.getBoolean("flight.enabled", false);

    private Flight() { }

    public static boolean isEnabled() { return enabled; }

    public static void toggle() {
        enabled = !enabled;
        ConfigManager.setBoolean("flight.enabled", enabled);

        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return;

        if (!enabled) {
            // Landing logic: Stop flying and reset abilities
            mc.player.getAbilities().flying = false;
            if (!mc.player.isCreative() && !mc.player.isSpectator()) {
                mc.player.getAbilities().mayfly = false;
            }
            // Reset fall distance so you don't die on impact
            mc.player.fallDistance = 0;
        }

        mc.player.onUpdateAbilities();
    }

    public static void onClientTick() {
        if (!enabled) return;
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return;

        // Force abilities on every tick to prevent the game from resetting them
        if (!mc.player.getAbilities().mayfly || !mc.player.getAbilities().flying) {
            mc.player.getAbilities().mayfly = true;
            mc.player.getAbilities().flying = true;
            mc.player.onUpdateAbilities();
        }

        // Set flying speed (standard is 0.05)
        mc.player.getAbilities().setFlyingSpeed(0.1f);
    }
}