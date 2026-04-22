package net.blockname;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.toast.ToastManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;

import java.util.HashMap;
import java.util.Map;

public class ClientTickHandler {

    private static BlockInfoToast currentToast = null;
    private static Block lastBlock = null;
    private static long lastToastTime = 0;
    private static final Map<Block, BlockInfoCache> CACHE = new HashMap<>();

    public static void onClientTick(MinecraftClient client) {
        if (client.player == null || client.world == null) return;

        ScanSystem.tickScan(client);

        long now = System.currentTimeMillis();
        HitResult hit = client.crosshairTarget;

        if (hit instanceof BlockHitResult bhr) {
            BlockState state = client.world.getBlockState(bhr.getBlockPos());
            Block block = state.getBlock();

            BlockInfoCache info = getBlockInfo(state);

            String title = info.name;
            String subtitle = "Outil : " + info.exactTool;
            if (BlockInfoConfig.showEnchantInfo) {
                subtitle += " | " + info.enchantInfo;
            }

            if (currentToast == null || block != lastBlock) {
                if (now - lastToastTime > BlockInfoConfig.cooldown) {
                    currentToast = new BlockInfoToast(
                            info.icon,
                            title,
                            subtitle,
                            BlockInfoConfig.debugMode ? info.debugInfo : ""
                    );
                    ToastManager tm = client.getToastManager();
                    tm.add(currentToast);
                    lastToastTime = now;
                }
            } else {
                currentToast.refresh();
            }

            lastBlock = block;
            return;
        }

        if (currentToast != null) {
            currentToast.startFadeOut();
            lastBlock = null;
        }
    }

    private static BlockInfoCache getBlockInfo(BlockState state) {
        Block block = state.getBlock();

        if (CACHE.containsKey(block)) {
            return CACHE.get(block);
        }

        String name = block.getName().getString();
        ItemStack icon = ToolUtils.getIcon(state);
        String exactTool = ToolUtils.getExactTool(state);
        String enchantInfo = ToolUtils.getSpecialRequirements(state);
        String debugInfo = BlockInfoConfig.debugMode ? DebugUtils.getDebugInfo(state) : "";

        BlockInfoCache info = new BlockInfoCache(name, icon, exactTool, enchantInfo, debugInfo);
        CACHE.put(block, info);
        return info;
    }
}
