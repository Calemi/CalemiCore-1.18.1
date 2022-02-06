package com.tm.calemicore.util.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.tm.calemicore.util.helper.ScreenHelper;
import com.tm.calemicore.util.menu.MenuBase;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public abstract class ScreenContainerBase<T extends MenuBase> extends AbstractContainerScreen<T> {

    /**
     * Used to obtain the GUI's texture, so it can render it.
     */
    public ResourceLocation textureLocation;

    public ScreenContainerBase(T menu, Inventory playerInv, Component title) {
        super(menu, playerInv, title);
    }

    /**
     * Used to determine the left of the GUI.
     */
    public int getScreenX() {
        return (width - imageWidth) / 2;
    }

    /**
     * Used to determine the top of the GUI.
     */
    public int getScreenY() {
        return (height - imageHeight) / 2;
    }

    /**
     * The base render method. Handles ALL rendering.
     */
    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(poseStack);
        super.render(poseStack, mouseX, mouseY, partialTick);
        this.renderTooltip(poseStack, mouseX, mouseY);
    }

    /**
     * The background render method.
     */
    @Override
    protected void renderBg(PoseStack poseStack, float partialTick, int mouseX, int mouseY) {

        if (textureLocation != null) {
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.setShaderTexture(0, textureLocation);
            ScreenHelper.drawRect(poseStack, 0, 0, new ScreenRect(getScreenX(), getScreenY(), imageWidth, imageHeight), 0);
        }
    }
}
