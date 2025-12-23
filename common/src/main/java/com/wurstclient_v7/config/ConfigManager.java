package com.wurstclient_v7.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Very small properties-backed config manager for runtime settings.
 */
public final class ConfigManager {
    private static final File CONFIG_DIR = new File("config");
    private static final File CONFIG_FILE = new File(CONFIG_DIR, "hackclient-config.properties");
    private static final Properties PROPS = new Properties();

    static {
        // defaults
        PROPS.setProperty("speed.multiplier", "1.5");
        PROPS.setProperty("mobvision.range", "25");
        PROPS.setProperty("mobvision.interval", "20");
        PROPS.setProperty("fullbright.duration", "260");
        load();
    }

    private ConfigManager() { }

    private static void load() {
        if (!CONFIG_FILE.exists()) return;
        try (FileInputStream in = new FileInputStream(CONFIG_FILE)) {
            PROPS.load(in);
        } catch (IOException e) {
            System.err.println("Failed to load config: " + e.getMessage());
        }
    }

    private static void save() {
        CONFIG_DIR.mkdirs();
        try (FileOutputStream out = new FileOutputStream(CONFIG_FILE)) {
            PROPS.store(out, "Hack client config");
        } catch (IOException e) {
            System.err.println("Failed to save config: " + e.getMessage());
        }
    }

    public static double getDouble(String key, double defaultVal) {
        String s = PROPS.getProperty(key);
        if (s == null) return defaultVal;
        try { return Double.parseDouble(s); } catch (NumberFormatException e) { return defaultVal; }
    }

    public static void setDouble(String key, double val) {
        PROPS.setProperty(key, Double.toString(val));
        save();
    }
}
