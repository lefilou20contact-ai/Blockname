package net.blockname;

import net.minecraft.item.ItemStack;

public class BlockInfoCache {
    public final String name;
    public final ItemStack icon;
    public final String exactTool;
    public final String enchantInfo;
    public final String debugInfo;

    public BlockInfoCache(String name, ItemStack icon, String exactTool, String enchantInfo, String debugInfo) {
        this.name = name;
        this.icon = icon;
        this.exactTool = exactTool;
        this.enchantInfo = enchantInfo;
        this.debugInfo = debugInfo;
    }
}
