package com.wurstclient_v7.feature;

import com.wurstclient_v7.config.ConfigManager;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public final class AutoAttack {
    private static boolean enabled = ConfigManager.getBoolean("autoattack.enabled", false);
    private static int timer = 0;

    private AutoAttack() { }

    public static boolean isEnabled() { return enabled; }

    public static void toggle() {
        enabled = !enabled;
        ConfigManager.setBoolean("autoattack.enabled", enabled);
    }

    // This is called by your Mixin when you hold or click Left Mouse
    public static void onLeftClick() {
        if (!enabled) return;

        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.hitResult == null) return;

        // Check if we are looking at an entity
        if (mc.hitResult.getType() == HitResult.Type.ENTITY) {
            Entity target = ((EntityHitResult) mc.hitResult).getEntity();

            // Only attack if it's alive and not the player
            if (target.isAlive() && target != mc.player) {
                // Perform the attack
                mc.gameMode.attack(mc.player, target);
                mc.player.swing(InteractionHand.MAIN_HAND);
            }
        }
    }
}