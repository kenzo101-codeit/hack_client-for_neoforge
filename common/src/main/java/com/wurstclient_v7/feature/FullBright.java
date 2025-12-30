package com.wurstclient_v7.feature;

import com.wurstclient_v7.config.ConfigManager;
import net.minecraft.client.Minecraft;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;

public final class FullBright {
    private static boolean enabled = ConfigManager.getBoolean("fullbright.enabled", false);

    private FullBright() { }

    public static boolean isEnabled() { return enabled; }

    public static void toggle() {
        enabled = !enabled;
        ConfigManager.setBoolean("fullbright.enabled", enabled);

        // If we just disabled it, remove the effect immediately
        if (!enabled) {
            Minecraft mc = Minecraft.getInstance();
            if (mc.player != null) {
                mc.player.removeEffect(MobEffects.NIGHT_VISION);
            }
        }
    }

    public static void onClientTick() {
        if (!enabled) return;

        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return;

        // We apply the effect LOCALLY. The server doesn't know we have it.
        // duration 500 ticks (~25 seconds), amplifier 0, ambient true, showParticles false
        MobEffectInstance nightVision = new MobEffectInstance(MobEffects.NIGHT_VISION, 500, 0, false, false);

        // This method adds it to your client-side player only
        mc.player.addEffect(nightVision);
    }
}