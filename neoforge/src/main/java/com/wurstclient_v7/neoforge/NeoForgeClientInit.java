package com.wurstclient_v7.neoforge;

import com.wurstclient_v7.feature.FeatureManager;
import com.wurstclient_v7.input.KeybindManager;

public final class NeoForgeClientInit {
    public static void initClient() {
        // Register a simple listener that prints toggle messages to the console.
        FeatureManager.registerListener(enabled -> {
            System.out.println("Feature " + (enabled ? "enabled" : "disabled"));
        });

        // TODO: register client key binding and HUD integration here.
        // Ensure keybind manager is initialized (loads persisted binds)
        // Use stderr and a clear prefix so the message is visible in the run logs.
        System.err.println("[NEOFORGE INIT] Loaded bind for open_menu: " + KeybindManager.getLabel("open_menu"));
    }
}
