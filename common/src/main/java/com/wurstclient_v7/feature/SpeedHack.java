package com.wurstclient_v7.feature;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;

public final class SpeedHack {

    private static volatile boolean enabled = false;

    private SpeedHack() {
    }

    public static boolean isEnabled() {
        return enabled;
    }

    public static void toggle() {
        enabled = !enabled;
    }

    public static void onClientTick() {
        if (!enabled) return;
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || !mc.player.onGround()) return;

        Player player = mc.player;
        if (player.xxa != 0 || player.zza != 0) {
            // Instead of multiplying existing speed, we slightly boost the movement
            // This is more stable and less likely to trigger anti-cheats
            player.setDeltaMovement(player.getDeltaMovement().multiply(1.2, 1.0, 1.2));
        }
    }
}