package com.wurstclient_v7.feature;

import com.wurstclient_v7.config.ConfigManager;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public final class KillAura {
    private static boolean enabled = ConfigManager.getBoolean("killaura.enabled", false);
    private static double range = 4.5; // Standard reach distance
    private static int delayTicks = 10; // Simple attack speed (0.5 seconds)
    private static int timer = 0;

    private KillAura() { }

    public static boolean isEnabled() { return enabled; }

    public static void toggle() {
        enabled = !enabled;
        ConfigManager.setBoolean("killaura.enabled", enabled);
    }

    public static void onClientTick() {
        if (!enabled) return;

        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.level == null) return;

        // Attack Timer (Throttle)
        if (timer > 0) {
            timer--;
            return;
        }

        // Find a target
        for (Entity entity : mc.level.entitiesForRendering()) {
            // Logic: Is it alive? Is it NOT us? Is it close enough?
            if (entity instanceof LivingEntity target && entity != mc.player) {
                if (mc.player.distanceTo(target) <= range && target.isAlive()) {

                    // 1. Attack the entity via the GameMode (sends the packet)
                    mc.gameMode.attack(mc.player, target);

                    // 2. Swing our arm visually
                    mc.player.swing(InteractionHand.MAIN_HAND);

                    // 3. Reset the timer
                    timer = delayTicks;

                    // Only attack one target per tick
                    return;
                }
            }
        }
    }
}