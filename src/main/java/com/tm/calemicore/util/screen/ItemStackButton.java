package com.tm.calemicore.util.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import com.tm.calemicore.util.helper.ScreenHelper;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class ItemStackButton extends Button {

    protected ScreenRect hoverRect;
    protected ResourceLocation textureLocation;
    protected final ItemRenderer itemRenderer;

    /**
     * A base button used to render a given ItemStack.
     * @param pressable Called when the button is pressed.
     */
    public ItemStackButton(int x, int y, ResourceLocation textureLocation, ItemRenderer itemRender, OnPress pressable) {
        super(x, y, 16, 16, new TextComponent(""), pressable);
        hoverRect = new ScreenRect(x, y, 16, 16);
        this.textureLocation = textureLocation;
        this.itemRenderer = itemRender;
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
