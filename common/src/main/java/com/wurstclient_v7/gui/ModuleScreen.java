package com.wurstclient_v7.gui;

import com.wurstclient_v7.config.ConfigManager;
import com.wurstclient_v7.feature.*;
import com.wurstclient_v7.input.KeybindManager;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class ModuleScreen extends Screen {
    private final int WIDTH = 120;
    private final int HEIGHT = 250; // Adjusted for 1.20.4 (removed ElytraMace)
    private String listeningAction;

    public ModuleScreen() {
        super(Component.literal("Wurst Client v7"));
        this.listeningAction = null;
    }

    @Override
    public void render(GuiGraphics gfx, int mouseX, int mouseY, float partialTick) {
        // In 1.20.4, renderBackground might require only gfx in some versions
        this.renderBackground(gfx, mouseX, mouseY, partialTick);

        int x = (this.width - WIDTH) / 2;
        int y = (this.height - HEIGHT) / 2;

        // Background Panel
        gfx.fill(x, y, x + WIDTH, y + HEIGHT, 0xA0000000);

        // Title
        gfx.drawString(this.font, this.title, x + (WIDTH - this.font.width(this.title)) / 2, y + 8, -4599297, false);

        int lineY = y + 24;

        // Render standard modules
        renderModule(gfx, x, lineY, "Kill Aura", KillAura.isEnabled(), "kill_aura_toggle");
        lineY += 12;
        renderModule(gfx, x, lineY, "AutoAttack", AutoAttack.isEnabled(), "autoattack_toggle");
        lineY += 12;

        // Specialized SpeedHack Render
        renderSpeedHack(gfx, x, lineY);
        lineY += 12;

        renderModule(gfx, x, lineY, "FullBright", FullBright.isEnabled(), "fullbright_toggle");
        lineY += 12;
        renderModule(gfx, x, lineY, "Flight", Flight.isEnabled(), "flight_toggle");
        lineY += 12;
        renderModule(gfx, x, lineY, "NoFall", NoFall.isEnabled(), "nofall_toggle");
        lineY += 12;
        renderModule(gfx, x, lineY, "XRay", XRay.isEnabled(), "xray_toggle");
        lineY += 12;
        renderModule(gfx, x, lineY, "Jetpack", Jetpack.isEnabled(), "jetpack_toggle");
        lineY += 12;
        renderModule(gfx, x, lineY, "Nuker", Nuker.isEnabled(), "nuker_toggle");
        lineY += 12;
        renderModule(gfx, x, lineY, "Spider", Spider.isEnabled(), "spider_toggle");
        lineY += 12;
        renderModule(gfx, x, lineY, "ESP", ESP.isEnabled(), "esp_toggle");
        lineY += 12;
        renderModule(gfx, x, lineY, "Tracers", Tracers.isEnabled(), "tracers_toggle");
        lineY += 12;
        renderModule(gfx, x, lineY, "Andromeda", AndromedaBridge.isEnabled(), "andromeda_toggle");
        lineY += 12;
        renderModule(gfx, x, lineY, "SafeWalk", SafeWalk.isEnabled(), "safewalk_toggle");

        super.render(gfx, mouseX, mouseY, partialTick);
    }

    private void renderModule(GuiGraphics gfx, int x, int y, String label, boolean enabled, String action) {
        String status = enabled ? "ON" : "OFF";
        gfx.drawString(this.font, label, x + 8, y, -1, false);

        int statusColor = enabled ? 0xFF55FF55 : 0xFFFF5555;
        gfx.drawString(this.font, status, x + 70, y, statusColor, false);

        String binding = (this.listeningAction != null && this.listeningAction.equals(action)) ? "???" : "[" + KeybindManager.getLabel(action) + "]";
        gfx.drawString(this.font, binding, x + WIDTH - 8 - this.font.width(binding), y, 0xFFAAAAAA, false);
    }

    private void renderSpeedHack(GuiGraphics gfx, int x, int y) {
        double mult = ConfigManager.getDouble("speed.multiplier", 1.5D);
        String label = String.format("Speed (x%.1f)", mult);
        renderModule(gfx, x, y, label, SpeedHack.isEnabled(), "speedhack_toggle");
    }

    // MouseClicked and KeyPressed logic remains largely the same
    // but remember to remove the ElytraMace click checks!
}