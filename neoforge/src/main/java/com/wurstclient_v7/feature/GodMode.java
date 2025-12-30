package com.wurstclient_v7.feature;

import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

@EventBusSubscriber(modid = "wurst_client_on_neoforge")
public class GodMode {
    private static boolean enabled = false;
    private static String targetPlayer = ""; // Empty = Self

    public static boolean isEnabled() { return enabled; }

    public static void toggle() { enabled = !enabled; }

    public static void setTarget(String name) { targetPlayer = name; }

    // THIS IS THE METHOD YOUR GUI WAS LOOKING FOR
    public static String getTarget() {
        if (targetPlayer == null || targetPlayer.isEmpty()) {
            return "Self";
        }
        return targetPlayer;
    }

    @SubscribeEvent
    public static void onIncomingDamage(LivingIncomingDamageEvent event) {
        if (!enabled) return;

        if (event.getEntity() instanceof Player player) {
            String name = player.getScoreboardName();

            if (targetPlayer.equalsIgnoreCase("Everyone")) {
                event.setCanceled(true);
            } else if (targetPlayer.isEmpty()) {
                // In Singleplayer, canceling here makes the local player invincible
                event.setCanceled(true);
            } else if (name.equalsIgnoreCase(targetPlayer)) {
                event.setCanceled(true);
            }
        }
    }
}