package net.blockname;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class BlockNameMod implements ClientModInitializer {

    public static final String MOD_ID = "blockname";

    public static KeyBinding TOGGLE_SCAN_KEY;

    @Override
    public void onInitializeClient() {
        BlockInfoConfig.load();

        TOGGLE_SCAN_KEY = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.blockname.toggleScan",
                GLFW.GLFW_KEY_F9,
                "category.blockname"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            ClientTickHandler.onClientTick(client);
            handleKeybinds(client);
        });
    }

    private void handleKeybinds(MinecraftClient client) {
        while (TOGGLE_SCAN_KEY.wasPressed()) {
            if (!ScanSystem.isScanAllowed(client)) {
                if (client.player != null) {
                    client.player.sendMessage(Text.literal("§cScan désactivé en multijoueur"), true);
                }
                return;
            }

            BlockInfoConfig.scanEnabled = !BlockInfoConfig.scanEnabled;
            BlockInfoConfig.save();

            if (client.player != null) {
                String msg = BlockInfoConfig.scanEnabled ? "§aScan activé" : "§cScan désactivé";
                client.player.sendMessage(Text.literal(msg), true);
            }
        }
    }
}
