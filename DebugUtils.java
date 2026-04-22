package net.blockname;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.registry.Registries;
import net.minecraft.util.shape.VoxelShape;

public class DebugUtils {

    public static String getDebugInfo(BlockState state) {
        Block block = state.getBlock();

        float hardness = 0f; // Multi-version fallback
        float resistance = 0f;
        int light = state.getLuminance();
        String id = Registries.BLOCK.getId(block).toString();
        VoxelShape shape = state.getCollisionShape(null, null);

        String tags = Registries.BLOCK.getEntry(block)
                .streamTags()
                .map(tag -> tag.id().toString())
                .reduce((a, b) -> a + ", " + b)
                .orElse("");

        String loot = block.getLootTableId().toString();

        return "ID: " + id +
                " | Hardness: " + hardness +
                " | Resistance: " + resistance +
                " | Light: " + light +
                " | Tags: " + tags +
                " | Loot: " + loot +
                " | Shape: " + shape.getBoundingBox().toString();
    }
}
