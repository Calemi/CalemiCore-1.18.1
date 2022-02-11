package com.tm.calemicore.util.screen.widget;

import com.mojang.blaze3d.vertex.PoseStack;
import com.tm.calemicore.util.helper.ScreenHelper;
import com.tm.calemicore.util.screen.ScreenRect;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * A Button that mimics a Slot.
 */
@OnlyIn(Dist.CLIENT)
public class FakeSlot extends Button {

    private final ScreenRect rect;
    private final Screen screen;
    private final ItemRenderer itemRender;
    private ItemStack stack = new ItemStack(Items.AIR);

    /**
     * Creates a Fake Slot.
     * @param x The x coordinate of the Fake Slot.
     * @param y The y coordinate of the Fake Slot.
     * @param screen The screen of the Fake Slot.
     * @param itemRenderer The Item Renderer to use.
     * @param onPress Use this to handle anything when the Button is pressed.
     */
    public FakeSlot(int x, int y, Screen screen, ItemRenderer itemRenderer,  Button.OnPress onPress) {
        super(x, y, 16, 16, new TextComponent(""), onPress);
        rect = new ScreenRect(this.x, this.y, width, height);
        this.screen = screen;
        this.itemRender = itemRenderer;
    }

    @Override
    public void renderButton (PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {

        if (this.visible && !stack.isEmpty()) {

            ScreenHelper.drawItemStack(itemRender, getItemStack(), rect.x, rect.y);

            if (rect.contains(mouseX, mouseY)) {
                screen.renderTooltip(poseStack, screen.getTooltipFromItem(getItemStack()), getItemStack().getTooltipImage(), mouseX, mouseY);
            }
        }
    }

    private ItemStack getItemStack () {
        return stack;
    }

    public void setItemStack (ItemStack stack) {
        this.stack = stack;
    }
}
