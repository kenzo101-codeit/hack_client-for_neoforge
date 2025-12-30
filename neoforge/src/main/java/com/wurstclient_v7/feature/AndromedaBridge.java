package com.wurstclient_v7.feature;

import com.wurstclient_v7.config.ConfigManager;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public final class AndromedaBridge {
    private static boolean enabled = ConfigManager.getBoolean("andromeda.enabled", false);
    private static float startYaw;
    private static int tickCounter = 0;

    public static boolean isEnabled() { return enabled; }

    public static void toggle() {
        enabled = !enabled;
        ConfigManager.setBoolean("andromeda.enabled", enabled);
        Minecraft mc = Minecraft.getInstance();
        if (enabled && mc.player != null) {
            startYaw = mc.player.getYRot();
        } else {
            stopInput(mc);
        }
    }

    public static void onClientTick() {
        if (!enabled) return;
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.level == null) return;

        tickCounter++;

        // 1. THE "CRAZY" FLICK LOGIC
        // Every even tick look DOWN, every odd tick look UP
        if (tickCounter % 2 == 0) {
            mc.player.setXRot(85.0f); // Look down for the floor
        } else {
            mc.player.setXRot(-85.0f); // Look up for the ceiling
        }
        mc.player.setYRot(startYaw); // Keep direction locked

        // 2. FORCE MOVEMENT
        mc.options.keyUp.setDown(true);
        mc.options.keySprint.setDown(true);

        // 3. AUTO JUMP
        if (mc.player.onGround()) {
            mc.player.jumpFromGround();
        }

        // 4. PLACEMENT
        BlockPos playerPos = mc.player.blockPosition();

        if (tickCounter % 2 == 0) {
            // Place Floor
            placeBlock(mc, playerPos.below(), Direction.UP);
        } else {
            // Place Ceiling
            placeBlock(mc, playerPos.above(2), Direction.DOWN);
        }
    }

    private static void placeBlock(Minecraft mc, BlockPos pos, Direction side) {
        if (!mc.level.getBlockState(pos).isAir()) return;

        Vec3 hitVec = new Vec3(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
        BlockHitResult hit = new BlockHitResult(hitVec, side, pos, false);

        // Interaction in 1.20.4/1.21.1
        mc.gameMode.useItemOn(mc.player, InteractionHand.MAIN_HAND, hit);
        mc.player.swing(InteractionHand.MAIN_HAND);
    }

    private static void stopInput(Minecraft mc) {
        mc.options.keyUp.setDown(false);
        mc.options.keySprint.setDown(false);
    }
}