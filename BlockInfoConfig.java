package net.blockname;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Files;
import java.nio.file.Path;

public class BlockInfoConfig {

    public static String theme = "dark";
    public static float fadeOutSpeed = 1.5f;
    public static float fadeInSpeed = 0.15f;
    public static boolean bounce = true;
    public static int cooldown = 300;
    public static boolean compactMode = false;
    public static boolean showEnchantInfo = true;
    public static boolean debugMode = false;
    public static boolean scanEnabled = true;

    private static final Path CONFIG_PATH = FabricLoader.getInstance()
            .getConfigDir().resolve("blockname.json");

    public static void load() {
        try {
            if (!Files.exists(CONFIG_PATH)) {
                save();
                return;
            }

            JsonObject json = JsonParser.parseString(Files.readString(CONFIG_PATH)).getAsJsonObject();

            if (json.has("theme")) theme = json.get("theme").getAsString();
            if (json.has("fadeOutSpeed")) fadeOutSpeed = json.get("fadeOutSpeed").getAsFloat();
            if (json.has("fadeInSpeed")) fadeInSpeed = json.get("fadeInSpeed").getAsFloat();
            if (json.has("bounce")) bounce = json.get("bounce").getAsBoolean();
            if (json.has("cooldown")) cooldown = json.get("cooldown").getAsInt();
            if (json.has("compactMode")) compactMode = json.get("compactMode").getAsBoolean();
            if (json.has("showEnchantInfo")) showEnchantInfo = json.get("showEnchantInfo").getAsBoolean();
            if (json.has("debugMode")) debugMode = json.get("debugMode").getAsBoolean();
            if (json.has("scanEnabled")) scanEnabled = json.get("scanEnabled").getAsBoolean();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void save() {
        try {
            JsonObject json = new JsonObject();
            json.addProperty("theme", theme);
            json.addProperty("fadeOutSpeed", fadeOutSpeed);
            json.addProperty("fadeInSpeed", fadeInSpeed);
            json.addProperty("bounce", bounce);
            json.addProperty("cooldown", cooldown);
            json.addProperty("compactMode", compactMode);
            json.addProperty("showEnchantInfo", showEnchantInfo);
            json.addProperty("debugMode", debugMode);
            json.addProperty("scanEnabled", scanEnabled);

            Files.writeString(CONFIG_PATH, json.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
