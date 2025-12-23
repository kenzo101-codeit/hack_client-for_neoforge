package com.wurstclient_v7.gui;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import com.wurstclient_v7.feature.KillAura;
import com.wurstclient_v7.config.ConfigManager;
import com.wurstclient_v7.input.KeybindManager;
import org.lwjgl.glfw.GLFW;

 

public class ModuleScreen extends Screen {
    private final int WIDTH = 120;
    private final int HEIGHT = 140;
    private final int PADDING = 8;

    public ModuleScreen() {
        super(Component.literal("My hack client"));
    }
    private String listeningAction = null; // action name we're capturing, null when idle
    @Override
    protected void init() {
        super.init();
    }

    @Override
    @SuppressWarnings("null")
    public void render(GuiGraphics gfx, int mouseX, int mouseY, float partialTick) {
        // Dim background
        this.renderBackground(gfx, mouseX, mouseY, partialTick);

        int x = (this.width - WIDTH) / 2;
        int y = (this.height - HEIGHT) / 2;

        // Panel background
        gfx.fill(x, y, x + WIDTH, y + HEIGHT, 0xAA002233);

        // Title (centered)
        gfx.drawString(this.font, this.title, x + (WIDTH - this.font.width(this.title.getString())) / 2, y + PADDING, 0xFFB9D1FF, false);

        // List modules
        int lineY = y + PADDING + 16;

        // Kill-aura entry
        String kaLabel = "kill-aura";
        String kaStatus = KillAura.isEnabled() ? "ON" : "OFF";
        gfx.drawString(this.font, kaLabel, x + PADDING, lineY, 0xFFFFFFFF, false);
        // Show status
        gfx.drawString(this.font, kaStatus, x + WIDTH - PADDING - this.font.width(kaStatus) - 40, lineY, KillAura.isEnabled() ? 0xFF66FF66 : 0xFFFF6666, false);
        // Show binding (for kill aura)
        String binding = listeningAction != null && listeningAction.equals("kill_aura_toggle") ? "Press any key or click mouse..." : KeybindManager.getLabel("kill_aura_toggle");
        gfx.drawString(this.font, binding, x + WIDTH - PADDING - this.font.width(binding), lineY, 0xFFFFFFAA, false);

        // Autoattack entry (next line)
        lineY += 12;
        String aaLabel = "autoattack";
        String aaStatus = com.wurstclient_v7.feature.AutoAttack.isEnabled() ? "ON" : "OFF";
        gfx.drawString(this.font, aaLabel, x + PADDING, lineY, 0xFFFFFFFF, false);
        gfx.drawString(this.font, aaStatus, x + WIDTH - PADDING - this.font.width(aaStatus) - 40, lineY, com.wurstclient_v7.feature.AutoAttack.isEnabled() ? 0xFF66FF66 : 0xFFFF6666, false);
        String aaBinding = listeningAction != null && listeningAction.equals("autoattack_toggle") ? "Press any key or click mouse..." : KeybindManager.getLabel("autoattack_toggle");
        gfx.drawString(this.font, aaBinding, x + WIDTH - PADDING - this.font.width(aaBinding), lineY, 0xFFFFFFAA, false);

        // Speedhack entry
        lineY += 12;
        String shLabel = "speedhack";
        String shStatus = com.wurstclient_v7.feature.SpeedHack.isEnabled() ? "ON" : "OFF";
        double shMult = ConfigManager.getDouble("speed.multiplier", 1.5);
        gfx.drawString(this.font, shLabel, x + PADDING, lineY, 0xFFFFFFFF, false);
        gfx.drawString(this.font, shStatus, x + WIDTH - PADDING - this.font.width(shStatus) - 60, lineY, com.wurstclient_v7.feature.SpeedHack.isEnabled() ? 0xFF66FF66 : 0xFFFF6666, false);
        String shMultText = String.format("x%.2f", shMult);
        gfx.drawString(this.font, shMultText, x + WIDTH - PADDING - this.font.width(shMultText), lineY, 0xFFCCCCCC, false);
        String shBinding = listeningAction != null && listeningAction.equals("speedhack_toggle") ? "Press any key or click mouse..." : KeybindManager.getLabel("speedhack_toggle");
        gfx.drawString(this.font, shBinding, x + WIDTH - PADDING - this.font.width(shBinding), lineY, 0xFFFFFFAA, false);

        // MobVision entry
        lineY += 12;
        String mvLabel = "mobvision";
        String mvStatus = com.wurstclient_v7.feature.MobVision.isEnabled() ? "ON" : "OFF";
        gfx.drawString(this.font, mvLabel, x + PADDING, lineY, 0xFFFFFFFF, false);
        gfx.drawString(this.font, mvStatus, x + WIDTH - PADDING - this.font.width(mvStatus) - 40, lineY, com.wurstclient_v7.feature.MobVision.isEnabled() ? 0xFF66FF66 : 0xFFFF6666, false);
        String mvBinding = listeningAction != null && listeningAction.equals("mobvision_toggle") ? "Press any key or click mouse..." : KeybindManager.getLabel("mobvision_toggle");
        gfx.drawString(this.font, mvBinding, x + WIDTH - PADDING - this.font.width(mvBinding), lineY, 0xFFFFFFAA, false);

        // FullBright entry
        lineY += 12;
        String fbLabel = "fullbright";
        String fbStatus = com.wurstclient_v7.feature.FullBright.isEnabled() ? "ON" : "OFF";
        gfx.drawString(this.font, fbLabel, x + PADDING, lineY, 0xFFFFFFFF, false);
        gfx.drawString(this.font, fbStatus, x + WIDTH - PADDING - this.font.width(fbStatus) - 40, lineY, com.wurstclient_v7.feature.FullBright.isEnabled() ? 0xFF66FF66 : 0xFFFF6666, false);
        String fbBinding = listeningAction != null && listeningAction.equals("fullbright_toggle") ? "Press any key or click mouse..." : KeybindManager.getLabel("fullbright_toggle");
        gfx.drawString(this.font, fbBinding, x + WIDTH - PADDING - this.font.width(fbBinding), lineY, 0xFFFFFFAA, false);

        super.render(gfx, mouseX, mouseY, partialTick);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        // If we're capturing, use mouse button as the bind
        if (listeningAction != null) {
            KeybindManager.setKey(listeningAction, button, 0, true);
            listeningAction = null;
            return true;
        }
        int x = (this.width - WIDTH) / 2;
        int y = (this.height - HEIGHT) / 2;
        int lineY = y + PADDING + 16;

        // If clicked on the kill-aura name area toggle it
        if (mouseX >= x + PADDING && mouseX <= x + WIDTH - PADDING - 40 && mouseY >= lineY - 2 && mouseY <= lineY + 10) {
            KillAura.toggle();
            return true;
        }

        // If clicked on the autoattack name area toggle it
        int aaLabelY = lineY;
        if (mouseX >= x + PADDING && mouseX <= x + WIDTH - PADDING - 40 && mouseY >= aaLabelY - 2 && mouseY <= aaLabelY + 10) {
            com.wurstclient_v7.feature.AutoAttack.toggle();
            return true;
        }

        // Speedhack label area
        int shLabelY = aaLabelY + 12;
        if (mouseX >= x + PADDING && mouseX <= x + WIDTH - PADDING - 40 && mouseY >= shLabelY - 2 && mouseY <= shLabelY + 10) {
            if (button == 1) {
                // cycle presets on right click
                double[] presets = {1.0, 1.25, 1.5, 2.0, 2.5};
                double cur = ConfigManager.getDouble("speed.multiplier", 1.5);
                int idx = 0;
                for (int i = 0; i < presets.length; i++) if (Math.abs(presets[i] - cur) < 0.001) { idx = i; break; }
                double next = presets[(idx + 1) % presets.length];
                ConfigManager.setDouble("speed.multiplier", next);
                com.wurstclient_v7.feature.SpeedHack.onClientTick();
            } else {
                com.wurstclient_v7.feature.SpeedHack.toggle();
            }
            return true;
        }

        // MobVision label area
        int mvLabelY = shLabelY + 12;
        if (mouseX >= x + PADDING && mouseX <= x + WIDTH - PADDING - 40 && mouseY >= mvLabelY - 2 && mouseY <= mvLabelY + 10) {
            com.wurstclient_v7.feature.MobVision.toggle();
            return true;
        }

        // FullBright label area
        int fbLabelY = mvLabelY + 12;
        if (mouseX >= x + PADDING && mouseX <= x + WIDTH - PADDING - 40 && mouseY >= fbLabelY - 2 && mouseY <= fbLabelY + 10) {
            com.wurstclient_v7.feature.FullBright.toggle();
            return true;
        }

        // If clicked on the binding area for kill-aura
        int bindStartX = x + WIDTH - PADDING - 40;
        if (mouseX >= bindStartX && mouseX <= x + WIDTH - PADDING && mouseY >= lineY - 14 && mouseY <= lineY - 4) {
            if (button == 1) { // right click clears binding
                KeybindManager.clear("kill_aura_toggle");
            } else {
                listeningAction = "kill_aura_toggle";
            }
            return true;
        }

        // If clicked on the binding area for autoattack
        int aaBindY = lineY;
        int aaBindStartX = x + WIDTH - PADDING - 40;
        if (mouseX >= aaBindStartX && mouseX <= x + WIDTH - PADDING && mouseY >= aaBindY - 2 && mouseY <= aaBindY + 10) {
            if (button == 1) {
                KeybindManager.clear("autoattack_toggle");
            } else {
                listeningAction = "autoattack_toggle";
            }
            return true;
        }

        // Speedhack binding area
        int shBindY = aaBindY + 12;
        int shBindStartX = x + WIDTH - PADDING - 40;
        if (mouseX >= shBindStartX && mouseX <= x + WIDTH - PADDING && mouseY >= shBindY - 2 && mouseY <= shBindY + 10) {
            if (button == 1) {
                KeybindManager.clear("speedhack_toggle");
            } else {
                listeningAction = "speedhack_toggle";
            }
            return true;
        }

        // MobVision binding area
        int mvBindY = shBindY + 12;
        int mvBindStartX = x + WIDTH - PADDING - 40;
        if (mouseX >= mvBindStartX && mouseX <= x + WIDTH - PADDING && mouseY >= mvBindY - 2 && mouseY <= mvBindY + 10) {
            if (button == 1) {
                KeybindManager.clear("mobvision_toggle");
            } else {
                listeningAction = "mobvision_toggle";
            }
            return true;
        }

        // FullBright binding area
        int fbBindY = mvBindY + 12;
        int fbBindStartX = x + WIDTH - PADDING - 40;
        if (mouseX >= fbBindStartX && mouseX <= x + WIDTH - PADDING && mouseY >= fbBindY - 2 && mouseY <= fbBindY + 10) {
            if (button == 1) {
                KeybindManager.clear("fullbright_toggle");
            } else {
                listeningAction = "fullbright_toggle";
            }
            return true;
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (listeningAction != null) {
            // capture key for the action, using modifier mask from GLFW
            int mods = 0;
            if ((modifiers & GLFW.GLFW_MOD_CONTROL) != 0) mods |= 2;
            if ((modifiers & GLFW.GLFW_MOD_SHIFT) != 0) mods |= 1;
            if ((modifiers & GLFW.GLFW_MOD_ALT) != 0) mods |= 4;
            if ((modifiers & GLFW.GLFW_MOD_SUPER) != 0) mods |= 8;
            KeybindManager.setKey(listeningAction, keyCode, mods, false);
            listeningAction = null;
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    
}
