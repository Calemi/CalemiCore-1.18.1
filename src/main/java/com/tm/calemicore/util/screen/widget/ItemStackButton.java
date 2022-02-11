package com.tm.calemicore.util.screen.widget;

import com.mojang.blaze3d.vertex.PoseStack;
import com.tm.calemicore.util.helper.ScreenHelper;
import com.tm.calemicore.util.screen.ScreenRect;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * A Button that renders an Item Stack.
 */
@OnlyIn(Dist.CLIENT)
public abstract class ItemStackButton extends Button {

    protected ScreenRect hoverRect;
    protected final ItemRenderer itemRenderer;

    /**
     * Creates an Item Stack Button.
     * @param x The x coordinate of the Button.
     * @param y The y coordinate of the Button.
     * @param itemRenderer The Item Renderer to use.
     * @param onPress Use this to handle anything when the Button is pressed.
     */
    public ItemStackButton(int x, int y, ItemRenderer itemRenderer, OnPress onPress) {
        super(x, y, 16, 16, new TextComponent(""), onPress);
        hoverRect = new ScreenRect(x, y, 16, 16);
        this.itemRenderer = itemRenderer;
    }

    public abstract ItemStack getRenderedStack();
    public abstract TranslatableComponent[] getTooltip();

    @Override
    public void renderButton (PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {

        if (this.visible && this.active) {

            isHovered = hoverRect.contains(mouseX, mouseY);

            ScreenHelper.drawItemStack(itemRenderer, getRenderedStack(), hoverRect.x, hoverRect.y);
            ScreenHelper.drawHoveringTextBox(poseStack, hoverRect, 150, mouseX, mouseY, 0xFFFFFF, getTooltip());
        }
    }
}
