package com.tm.calemicore.util.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.tm.calemicore.util.helper.ScreenHelper;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;

/**
 * Base class for Screens.
 */
public abstract class ScreenBase extends Screen {

    /**
     * Used to obtain the GUI's texture, so it can render it.
     */
    public ResourceLocation textureLocation;

    protected final Player player;
    protected final InteractionHand hand;

    protected ScreenBase(Player player, InteractionHand hand) {
        super(new TextComponent("Help"));
        this.player = player;
        this.hand = hand;
    }

    /**
     * Used to render anything in the background layer.
     */
    protected abstract void drawGuiBackground(PoseStack poseStack, int mouseX, int mouseY);

    /**
     * Used to render anything in the foreground layer.
     */
    protected abstract void drawGuiForeground(PoseStack poseStack, int mouseX, int mouseY);

    /**
     * Used to determine the width of the GUI.
     */
    protected abstract int getGuiSizeX();

    /**
     * Used to determine the height of the GUI.
     */
    protected abstract int getGuiSizeY();

    /**
     * Used to determine the left of the GUI.
     */
    protected int getScreenX() {
        return (this.width - getGuiSizeX()) / 2;
    }

    /**
     * Used to determine the top of the GUI.
     */
    protected int getScreenY() {
        return (this.height - getGuiSizeY()) / 2;
    }

    protected abstract boolean canCloseWithInvKey();

    /**
     * The base render method. Handles ALL rendering.
     */
    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float f1) {

        renderBackground(poseStack);

        if (textureLocation != null) {
            RenderSystem.setShaderTexture(0, textureLocation);
            ScreenHelper.drawRect(0, 0, new ScreenRect(getScreenX(), getScreenY(), getGuiSizeX(), getGuiSizeY()), 0);
        }

        drawGuiBackground(poseStack, mouseX, mouseY);
        super.render(poseStack, mouseX, mouseY, f1);
        drawGuiForeground(poseStack, mouseX, mouseY);
    }

    /**
     * Handles closing the GUI when the inventory key is pressed.
     */
    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {

        if (minecraft != null) {

            if (canCloseWithInvKey() && keyCode == minecraft.options.keyInventory.getKey().getValue()) {
                onClose();
                return true;
            }
        }

        return super.keyPressed(keyCode, scanCode, modifiers);
    }
}
