package com.wurstclient_v7.neoforge;

import net.neoforged.fml.common.Mod;

import com.wurstclient_v7.ExampleMod;

@Mod(ExampleMod.MOD_ID)
public final class ExampleModNeoForge {
    public ExampleModNeoForge() {
        // Run our common setup.
        ExampleMod.init();
        // Initialize client-only pieces (safe stub; actual client code lives behind this method)
        NeoForgeClientInit.initClient();
    }
}
