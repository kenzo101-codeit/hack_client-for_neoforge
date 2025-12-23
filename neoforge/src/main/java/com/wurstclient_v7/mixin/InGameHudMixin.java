package com.wurstclient_v7.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "net.minecraft.client.gui.hud.InGameHud")
public class InGameHudMixin {
    @Inject(method = "render", at = @At("TAIL"))
    @SuppressWarnings("null")
    private void onRender(CallbackInfo ci) {
        // This is a harmless example: print to the console when feature is enabled.
        if (com.wurstclient_v7.feature.KillAura.isEnabled()) {
            net.minecraft.client.Minecraft.getInstance().gui.setOverlayMessage(net.minecraft.network.chat.Component.literal("kill-aura ON"), false);
        }
    }
}
