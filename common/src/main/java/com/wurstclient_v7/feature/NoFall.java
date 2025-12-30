package com.wurstclient_v7.feature;

import com.wurstclient_v7.config.ConfigManager;
import net.minecraft.client.Minecraft;
import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket;

public final class NoFall {
    private static boolean enabled = ConfigManager.getBoolean("nofall.enabled", false);

    private NoFall() { }

    public static boolean isEnabled() { return enabled; }

    public static void toggle() {
        enabled = !enabled;
        ConfigManager.setBoolean("nofall.enabled", enabled);
    }

    public static void onClientTick() {
        if (!enabled) return;
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.getConnection() == null) return;

        // 2.0f is the safe distance. Minecraft starts damage at > 3.0 blocks.
        if (mc.player.fallDistance > 2.0f) {
            // We tell the server we are on the ground just before hitting.
            // PosRot is: X, Y, Z, Y-Yaw, X-Pitch, OnGround
            mc.getConnection().send(new ServerboundMovePlayerPacket.PosRot(
                    mc.player.getX(),
                    mc.player.getY(),
                    mc.player.getZ(),
                    mc.player.getYRot(),
                    mc.player.getXRot(),
                    true // The MAGIC bit: tells the server fall distance is now 0
            ));

            // Reset locally so we don't spam packets every tick
            mc.player.fallDistance = 0;
        }
    }
}