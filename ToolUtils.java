package net.blockname;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.GlassBlock;
import net.minecraft.block.IceBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public class ToolUtils {

    public static String getExactTool(BlockState state) {
        Block block = state.getBlock();
        String id = Registries.BLOCK.getId(block).toString();
        // Fallback générique multi-version : à spécialiser plus tard si tu veux
        return "Outil inconnu (" + id + ")";
    }

    public static String getSpecialRequirements(BlockState state) {
        Block block = state.getBlock();
        Identifier loot = block.getLootTableId();
        String path = loot.getPath();

        if (path.contains("silk_touch")) {
            return "Nécessite Silk Touch";
        }
        if (path.contains("fortune")) {
            return "Fortune recommandé";
        }

        if (block instanceof GlassBlock || block instanceof IceBlock) {
            return "Nécessite Silk Touch";
        }

        return "Aucun enchantement requis";
    }

    public static ItemStack getIcon(BlockState state) {
        return new ItemStack(state.getBlock().asItem());
    }
}
