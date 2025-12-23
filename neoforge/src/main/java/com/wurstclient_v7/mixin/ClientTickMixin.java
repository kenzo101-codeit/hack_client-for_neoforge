package com.wurstclient_v7.mixin;

import com.wurstclient_v7.feature.KillAura;
import com.wurstclient_v7.input.KeybindManager;
import net.minecraft.client.Minecraft;
import com.mojang.blaze3d.platform.InputConstants;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "net.minecraft.client.Minecraft")
public class ClientTickMixin {
    private static boolean prevTogglePressed = false;
    private static boolean prevMenuPressed = false;
    private static boolean prevLeftPressed = false;
    private static boolean prevAutoPressed = false;
    private static boolean prevSpeedPressed = false;
    private static boolean prevMobPressed = false;
    private static boolean prevFBPressed = false;

    @Inject(method = "tick", at = @At("TAIL"))
    private void onTick(CallbackInfo ci) {
        Minecraft mc = Minecraft.getInstance();
        if (mc == null) return;

        long window = mc.getWindow().getWindow();
        boolean pressed = KeybindManager.isPressed(window, "kill_aura_toggle");
        if (pressed && !prevTogglePressed) {
            KillAura.toggle();
            System.out.println("KillAura toggled: " + (KillAura.isEnabled() ? "ON" : "OFF"));
        }
        prevTogglePressed = pressed;

        // Menu key handling
        boolean menuPressed = KeybindManager.isPressed(window, "open_menu");
        if (menuPressed && !prevMenuPressed) {
            Minecraft.getInstance().setScreen(new com.wurstclient_v7.gui.ModuleScreen());
        }
        prevMenuPressed = menuPressed;

        // Autoattack toggle key
        boolean aaPressed = KeybindManager.isPressed(window, "autoattack_toggle");
        if (aaPressed && !prevAutoPressed) {
            com.wurstclient_v7.feature.AutoAttack.toggle();
            System.out.println("AutoAttack toggled: " + (com.wurstclient_v7.feature.AutoAttack.isEnabled() ? "ON" : "OFF"));
        }
        prevAutoPressed = aaPressed;

        // Speedhack toggle key
        boolean shPressed = KeybindManager.isPressed(window, "speedhack_toggle");
        if (shPressed && !prevSpeedPressed) {
            com.wurstclient_v7.feature.SpeedHack.toggle();
        }
        prevSpeedPressed = shPressed;

        // MobVision toggle key
        boolean mvPressed = KeybindManager.isPressed(window, "mobvision_toggle");
        if (mvPressed && !prevMobPressed) {
            com.wurstclient_v7.feature.MobVision.toggle();
            System.out.println("MobVision toggled: " + (com.wurstclient_v7.feature.MobVision.isEnabled() ? "ON" : "OFF"));
        }
        prevMobPressed = mvPressed;

        // FullBright toggle key
        boolean fbPressed = KeybindManager.isPressed(window, "fullbright_toggle");
        if (fbPressed && !prevFBPressed) {
            com.wurstclient_v7.feature.FullBright.toggle();
            System.out.println("FullBright toggled: " + (com.wurstclient_v7.feature.FullBright.isEnabled() ? "ON" : "OFF"));
        }
        prevFBPressed = fbPressed;
        // reuse prevTogglePressed only for kill-aura; do not update here to avoid interfering

        // Mouse left click handling (for autoattack)
        boolean leftPressed = InputConstants.isKeyDown(window, org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LEFT);
        if (leftPressed && !prevLeftPressed) {
            com.wurstclient_v7.feature.AutoAttack.onLeftClick();
        }
        prevLeftPressed = leftPressed;

        // Call feature tick handler
        KillAura.onClientTick();
        // SpeedHack tick handler (applies boosts when movement begins)
        com.wurstclient_v7.feature.SpeedHack.onClientTick();
        // MobVision and FullBright tick handlers for effects
        com.wurstclient_v7.feature.MobVision.onClientTick();
        com.wurstclient_v7.feature.FullBright.onClientTick();
    }
}
