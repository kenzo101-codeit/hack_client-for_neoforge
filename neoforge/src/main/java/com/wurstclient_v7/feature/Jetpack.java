package com.wurstclient_v7.feature;

import com.wurstclient_v7.config.ConfigManager;
import net.minecraft.client.Minecraft;
import net.minecraft.world.phys.Vec3;

public final class Jetpack {
    private static boolean enabled = ConfigManager.getBoolean("jetpack.enabled", false);

    private Jetpack() { }

    public static boolean isEnabled() { return enabled; }

    public static void toggle() {
        enabled = !enabled;
        ConfigManager.setBoolean("jetpack.enabled", enabled);
    }

    public static void onClientTick() {
        if (!enabled) return;
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return;

        // Only fly up if the jump key (Space) is held down
        if (mc.options.keyJump.isDown()) {
            Vec3 motion = mc.player.getDeltaMovement();

            // 0.45 is roughly the height of a normal jump.
            // 0.6 is quite fast. Let's stick to a snappy but manageable 0.5.
            mc.player.setDeltaMovement(new Vec3(motion.x, 0.5, motion.z));

            // Optional: Reset fall distance so you don't die when you let go!
            mc.player.fallDistance = 0;
        }
    }
}