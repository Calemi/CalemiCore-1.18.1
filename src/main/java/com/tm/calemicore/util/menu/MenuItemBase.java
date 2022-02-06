package com.tm.calemicore.util.menu;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public abstract class MenuItemBase extends MenuBase {

    private final ItemStack stack;

    public MenuItemBase(@Nullable MenuType<?> menuType, int containerID, ItemStack stack) {
        super(menuType, containerID);
        this.stack = stack;
    }

    public ItemStack getItemStack() {
        return stack;
    }
    
    @Override
    public boolean stillValid(Player player) {
        return !stack.isEmpty();
    }
}
