package net.blockname;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.toast.Toast;
import net.minecraft.client.toast.ToastManager;
import net.minecraft.item.ItemStack;

public class BlockInfoToast implements Toast {

    private final ItemStack icon;
    private final String title;
    private final String subtitle;
    private final String debugLine;

    private float alpha = 0f;
    private float offsetY = -10f;
    private boolean fadingOut = false;

    private float bounce = 0f;
    private boolean bouncing = true;

    private long lastUpdate = System.currentTimeMillis();

    public BlockInfoToast(ItemStack icon, String title, String subtitle, String debugLine) {
        this.icon = icon;
        this.title = title;
        this.subtitle = subtitle;
        this.debugLine = debugLine;
    }

    public void refresh() {
        fadingOut = false;

        alpha = Math.min(alpha + BlockInfoConfig.fadeInSpeed, 1f);

        if (BlockInfoConfig.bounce && bouncing) {
            bounce += 0.25f;
            offsetY = (float) (-10 * Math.cos(bounce));
            if (bounce >= Math.PI / 2) {
                bouncing = false;
                offsetY = 0;
            }
        } else {
            offsetY = 0;
        }

        lastUpdate = System.currentTimeMillis();
    }

    public void startFadeOut() {
        fadingOut = true;
        lastUpdate = System.currentTimeMillis();
    }

    @Override
    public Visibility draw(DrawContext ctx, ToastManager manager, long time) {
        long now = System.currentTimeMillis();
        float delta = (now - lastUpdate) / 1000f;

        if (fadingOut) {
            alpha -= delta * BlockInfoConfig.fadeOutSpeed;
            if (alpha <= 0) return Visibility.HIDE;
        }

        ctx.getMatrices().push();
        ctx.setShaderColor(1f, 1f, 1f, alpha);

        int x = 0;
        int y = (int) offsetY;
        int w = getWidth();
        int h = getHeight();

        boolean dark = BlockInfoConfig.theme.equalsIgnoreCase("dark");

        int bgColor = (int) (alpha * 0.6f) << 24 | (dark ? 0x202020 : 0xE0E0E0);
        int shadowColor = 0x55000000;
        int textColor = dark ? 0xFFFFFF : 0x000000;
        int subColor = dark ? 0xAAAAAA : 0x555555;

        ctx.fill(x + 2, y + 2, x + w + 2, y + h + 2, shadowColor);
        ctx.fill(x, y, x + w, y + h, bgColor);

        MinecraftClient client = MinecraftClient.getInstance();

        if (BlockInfoConfig.compactMode) {
            ctx.drawItem(icon, 6, y + 4);
            ctx.drawText(client.textRenderer, title, 24, y + 6, textColor, false);
        } else {
            ctx.drawItem(icon, 8, y + 8);
            ctx.drawText(client.textRenderer, title, 30, y + 7, textColor, false);
            ctx.drawText(client.textRenderer, subtitle, 30, y + 18, subColor, false);
            if (BlockInfoConfig.debugMode && debugLine != null && !debugLine.isEmpty()) {
                ctx.drawText(client.textRenderer, debugLine, 30, y + 29, subColor, false);
            }
        }

        ctx.setShaderColor(1f, 1f, 1f, 1f);
        ctx.getMatrices().pop();
        return Visibility.SHOW;
    }

    @Override
    public int getWidth() {
        return 160;
    }

    @Override
    public int getHeight() {
        if (BlockInfoConfig.compactMode) return 20;
        return BlockInfoConfig.debugMode ? 44 : 32;
    }
}
