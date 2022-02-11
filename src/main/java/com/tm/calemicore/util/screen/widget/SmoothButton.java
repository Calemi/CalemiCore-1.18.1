package com.tm.calemicore.util.screen.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.tm.calemicore.main.CCReference;
import com.tm.calemicore.util.helper.ScreenHelper;
import com.tm.calemicore.util.screen.ScreenRect;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * Smooth textured variant of the default Button.
 */
@OnlyIn(Dist.CLIENT)
public class SmoothButton extends Button {

    public final ScreenRect rect;

    /**
     * Creates a Smooth Button.
     * @param x The x coordinate of the Button.
     * @param y The y coordinate of the Button.
     * @param width The width of the Button.
     * @param textKey The text key on the Button.
     * @param onPress Use this to handle anything when the Button is pressed.
     */
    public SmoothButton(int x, int y, int width, String textKey, OnPress onPress) {
        super(width,16, x, y, new TranslatableComponent(textKey), onPress);

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = 16;

        rect = new ScreenRect(x, y, width, 16);
    }

    public void setPosition(int x, int y) {
        rect.x = x;
        this.x = x;
        rect.y = y;
        this.y = y;
    }

    @Override
    public void renderButton(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {

        if (visible) {

            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderTexture(0, CCReference.GUI_TEXTURES);

            if (rect.contains(mouseX, mouseY)) {
                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, alpha);
                isHovered = true;
            }

            else {
                RenderSystem.setShaderColor(0.8F, 0.8F, 0.8F, alpha);
                isHovered = false;
            }

            if (!active) {
                RenderSystem.setShaderColor(0.5F, 0.5F, 0.5F, alpha);
            }

            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.enableDepthTest();

            ScreenHelper.drawExpandableRect(0, 0, rect, 256, 16, 0);
            ScreenHelper.drawCenteredString(poseStack, rect.x + (rect.width / 2), rect.y + (rect.height - 8) / 2, 0, 0xFFFFFF, (MutableComponent) getMessage());
        }
    }
}
