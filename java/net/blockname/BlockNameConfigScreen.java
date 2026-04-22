package net.blockname;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.client.gui.widget.CheckboxWidget;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;

public class BlockNameConfigScreen extends Screen {

    private final Screen parent;

    private CheckboxWidget compactMode;
    private CheckboxWidget debugMode;
    private CheckboxWidget showEnchant;
    private CheckboxWidget scanEnabled;

    private SliderWidget fadeInSlider;
    private SliderWidget fadeOutSlider;

    protected BlockNameConfigScreen(Screen parent) {
        super(Text.literal("Configuration Block Name"));
        this.parent = parent;
    }

    @Override
    protected void init() {

        int center = this.width / 2;
        int y = 40;

        compactMode = CheckboxWidget.builder(Text.literal("Mode compact"), this.textRenderer)
                .pos(center - 100, y)
                .checked(BlockInfoConfig.compactMode)
                .build();
        this.addDrawableChild(compactMode);
        y += 25;

        debugMode = CheckboxWidget.builder(Text.literal("Mode debug"), this.textRenderer)
                .pos(center - 100, y)
                .checked(BlockInfoConfig.debugMode)
                .build();
        this.addDrawableChild(debugMode);
        y += 25;

        showEnchant = CheckboxWidget.builder(Text.literal("Afficher enchantements"), this.textRenderer)
                .pos(center - 100, y)
                .checked(BlockInfoConfig.showEnchantInfo)
                .build();
        this.addDrawableChild(showEnchant);
        y += 25;

        scanEnabled = CheckboxWidget.builder(Text.literal("Activer scan solo"), this.textRenderer)
                .pos(center - 100, y)
                .checked(BlockInfoConfig.scanEnabled)
                .build();
        this.addDrawableChild(scanEnabled);
        y += 30;

        fadeInSlider = new SliderWidget(center - 100, y, 200, 20, Text.literal("Fade-in"), BlockInfoConfig.fadeInSpeed / 2f) {
            @Override
            protected void updateMessage() {
                setMessage(Text.literal("Fade-in : " + String.format("%.2f", BlockInfoConfig.fadeInSpeed)));
            }

            @Override
            protected void applyValue() {
                BlockInfoConfig.fadeInSpeed = (float) (this.value * 2f);
            }
        };
        this.addDrawableChild(fadeInSlider);
        y += 30;

        fadeOutSlider = new SliderWidget(center - 100, y, 200, 20, Text.literal("Fade-out"), BlockInfoConfig.fadeOutSpeed / 3f) {
            @Override
            protected void updateMessage() {
                setMessage(Text.literal("Fade-out : " + String.format("%.2f", BlockInfoConfig.fadeOutSpeed)));
            }

            @Override
            protected void applyValue() {
                BlockInfoConfig.fadeOutSpeed = (float) (this.value * 3f);
            }
        };
        this.addDrawableChild(fadeOutSlider);
        y += 40;

        this.addDrawableChild(ButtonWidget.builder(Text.literal("Sauvegarder"), button -> {
            saveConfig();
            this.client.setScreen(parent);
        }).dimensions(center - 100, y, 200, 20).build());
    }

    private void saveConfig() {
        BlockInfoConfig.compactMode = compactMode.isChecked();
        BlockInfoConfig.debugMode = debugMode.isChecked();
        BlockInfoConfig.showEnchantInfo = showEnchant.isChecked();
        BlockInfoConfig.scanEnabled = scanEnabled.isChecked();
        BlockInfoConfig.save();
    }

    @Override
    public void render(DrawContext ctx, int mouseX, int mouseY, float delta) {
        this.renderBackground(ctx);
        ctx.drawCenteredTextWithShadow(this.textRenderer, "Configuration Block Name", this.width / 2, 15, 0xFFFFFF);
        super.render(ctx, mouseX, mouseY, delta);
    }
}
