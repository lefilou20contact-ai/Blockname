package net.blockname;

import net.minecraft.client.MinecraftClient;

public class ScanSystem {

    public static boolean isScanAllowed(MinecraftClient client) {
        return client.isIntegratedServerRunning();
    }

    public static void tickScan(MinecraftClient client) {
        if (!BlockInfoConfig.scanEnabled) return;
        if (!isScanAllowed(client)) return;

        // Scan avancé à ajouter plus tard
    }
}
