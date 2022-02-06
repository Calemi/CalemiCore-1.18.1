package com.tm.calemicore.util.helper;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.tm.calemicore.main.CCReference;
import com.tm.calemicore.util.screen.ScreenRect;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ScreenHelper {

    private static final int TEXTURE_SIZE = 256;
    private static final Minecraft mc = Minecraft.getInstance();

    /*
        Rectangle Methods
     */

    public static void drawRect(PoseStack poseStack, int u, int v, ScreenRect rect, int zLevel) {

        int maxX = rect.x + rect.width;
        int maxY = rect.y + rect.height;

        int maxU = u + rect.width;
        int maxV = v + rect.height;

        float pixel = 1F / TEXTURE_SIZE;

        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder buffer = tesselator.getBuilder();

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.enableBlend();

        buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        buffer.vertex((float) rect.x, (float) maxY, zLevel).uv(u * pixel, maxV * pixel).endVertex();
        buffer.vertex((float) maxX, (float) maxY, zLevel).uv(maxU * pixel, maxV * pixel).endVertex();
        buffer.vertex((float) maxX, (float) rect.y, zLevel).uv(maxU * pixel, v * pixel).endVertex();
        buffer.vertex((float) rect.x, (float) rect.y, zLevel).uv(u * pixel, v * pixel).endVertex();
        tesselator.end();

        RenderSystem.disableBlend();
    }

    public static void drawExpandableRect(PoseStack poseStack, int u, int v, ScreenRect rect, int maxWidth, int maxHeight, int zLevel) {

        //TOP LEFT
        ScreenRect topLeft = new ScreenRect(rect.x, rect.y, Math.min(rect.width - 2, maxWidth), Math.min(rect.height - 2, maxHeight));
        drawRect(poseStack, u, v, topLeft, zLevel);

        //RIGHT
        ScreenRect right = new ScreenRect(rect.x + rect.width - 2, rect.y, 2, Math.min(rect.height - 2, maxHeight));
        if (rect.width <= maxWidth) drawRect(poseStack, u + maxWidth - 2, v, right, zLevel);

        //BOTTOM
        ScreenRect bottom = new ScreenRect(rect.x, rect.y + rect.height - 2, Math.min(rect.width - 2, maxWidth), 2);
        if (rect.height <= maxHeight) drawRect(poseStack, u, v + maxHeight - 2, bottom, zLevel);

        //BOTTOM RIGHT
        ScreenRect bottomRight = new ScreenRect(rect.x + rect.width - 2, rect.y + rect.height - 2,  2, 2);
        if (rect.width <= maxWidth && rect.height <= maxHeight) drawRect(poseStack, u + maxWidth - 2, v + maxHeight - 2, bottomRight, zLevel);
    }

    public static void drawColoredRect(ScreenRect rect, int zLevel, int hex, float alpha) {

        float r = (hex >> 16) & 0xFF;
        float g = (hex >> 8) & 0xFF;
        float b = (hex) & 0xFF;

        float red = ((((r * 100) / 255) / 100));
        float green = ((((g * 100) / 255) / 100));
        float blue = ((((b * 100) / 255) / 100));

        int maxX = rect.x + rect.width;
        int maxY = rect.y + rect.height;

        RenderSystem.disableTexture();
        RenderSystem.enableBlend();

        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder buffer = tesselator.getBuilder();

        buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
        buffer.vertex(rect.x, maxY, zLevel).color(red, green, blue, alpha).endVertex();
        buffer.vertex(maxX, maxY, zLevel).color(red, green, blue, alpha).endVertex();
        buffer.vertex(maxX, rect.y, zLevel).color(red, green, blue, alpha).endVertex();
        buffer.vertex(rect.x, rect.y, zLevel).color(red, green, blue, alpha).endVertex();
        tesselator.end();

        RenderSystem.disableBlend();
        RenderSystem.enableTexture();
    }

    /*
        String Methods
     */

    public static void drawCenteredString(PoseStack poseStack, int x, int y, int zLevel, int color, MutableComponent text) {
        poseStack.pushPose();
        poseStack.translate(0, 0, 50 + zLevel);
        mc.font.draw(poseStack, text, x - (float) (mc.font.width(text) / 2), y, color);
        poseStack.popPose();
    }

    public static void drawTextBox(PoseStack poseStack, int x, int y, int zLevel, boolean centeredString, int color, MutableComponent... text) {

        poseStack.pushPose();
        poseStack.translate(0, 0, zLevel);

        int maxLength = mc.font.width(text[0]);

        List<MutableComponent> textToRender = new ArrayList<>(Arrays.asList(text));

        for (MutableComponent msg : textToRender) {

            if (mc.font.width(msg) > maxLength) {
                maxLength = mc.font.width(msg);
            }
        }

        RenderSystem.setShaderTexture(0, CCReference.GUI_TOOLTIP);
        ScreenRect rect = new ScreenRect(x + (centeredString ? - maxLength / 2 : 0), y, maxLength + 5, 13 + ((textToRender.size() - 1) * 9));
        drawExpandableRect(poseStack, 0, 0, rect, 512, 512, zLevel);

        poseStack.translate(0, 0, 100);
        for (int i = 0; i < textToRender.size(); i++) {

            MutableComponent msg = textToRender.get(i);
            mc.font.draw(poseStack, msg.withStyle(ChatFormatting.WHITE), x + 3 + (centeredString ? -(int) (mc.font.width(msg.getString()) / 2) : 0), y + 3 + (i * 9), color);
        }

        poseStack.translate(0, 0, 0);
        poseStack.popPose();
    }

    public static void drawHoveringTextBox(PoseStack poseStack, ScreenRect hoverRect, int zLevel, int mouseX, int mouseY, int color, MutableComponent... text) {

        if (hoverRect.contains(mouseX, mouseY)) {
            drawTextBox(poseStack, mouseX + 8, mouseY - 10, 50, false, color, text);
        }
    }

    /*
        Misc Methods
     */

    public static void drawItemStack(ItemRenderer itemRender, ItemStack stack, int x, int y) {
        itemRender.blitOffset = -100;
        itemRender.renderGuiItem(stack, x, y);
        itemRender.blitOffset = 0F;
    }
}
