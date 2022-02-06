package com.tm.calemicore.util.menu;

import com.tm.calemicore.util.blockentity.BlockEntityContainerBase;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import org.jetbrains.annotations.Nullable;

public abstract class MenuBlockBase extends MenuBase {

    private final BlockEntityContainerBase blockEntity;

    protected MenuBlockBase(@Nullable MenuType<?> menuType, int containerID, BlockEntityContainerBase blockEntity) {
        super(menuType, containerID);
        this.blockEntity = blockEntity;
    }

    public BlockEntityContainerBase getBlockEntity() {
        return blockEntity;
    }

    @Override
    public boolean stillValid(Player player) {
        return getBlockEntity().stillValid(player);
    }
}
