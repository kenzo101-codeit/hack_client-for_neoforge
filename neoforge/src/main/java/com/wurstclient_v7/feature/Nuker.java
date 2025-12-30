package com.wurstclient_v7.feature;

import com.wurstclient_v7.config.ConfigManager;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.protocol.game.ServerboundPlayerActionPacket;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public final class Nuker {
    private static boolean enabled = ConfigManager.getBoolean("nuker.enabled", false);
    private static int range = 4;

    private Nuker() { }

    public static boolean isEnabled() { return enabled; }

    public static void toggle() {
        enabled = !enabled;
        ConfigManager.setBoolean("nuker.enabled", enabled);
    }

    public static void onClientTick() {
        if (!enabled) return;
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.level == null) return;

        BlockPos playerPos = mc.player.blockPosition();

        // Loop through blocks in range
        for (int x = -range; x <= range; x++) {
            for (int y = -range; y <= range; y++) {
                for (int z = -range; z <= range; z++) {
                    BlockPos pos = playerPos.offset(x, y, z);
                    BlockState state = mc.level.getBlockState(pos);

                    if (canBreak(state)) {
                        // 1. Tell the server we started breaking
                        mc.player.connection.send(new ServerboundPlayerActionPacket(
                                ServerboundPlayerActionPacket.Action.START_DESTROY_BLOCK, pos, Direction.UP));

                        // 2. Tell the server we finished breaking (Instant break)
                        mc.player.connection.send(new ServerboundPlayerActionPacket(
                                ServerboundPlayerActionPacket.Action.STOP_DESTROY_BLOCK, pos, Direction.UP));

                        // 3. Optional: Play the break effect locally
                        mc.level.destroyBlock(pos, false);

                        return; // Break 1 block per tick to prevent Anti-Cheat kicks
                    }
                }
            }
        }
    }

    private static boolean canBreak(BlockState state) {
        return !state.isAir()
                && state.getBlock() != Blocks.BEDROCK
                && state.getBlock() != Blocks.WATER
                && state.getBlock() != Blocks.LAVA;
    }
}