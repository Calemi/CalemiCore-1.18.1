package com.tm.calemicore.util.menu;

import com.tm.calemicore.util.blockentity.BlockEntityContainerBase;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import org.jetbrains.annotations.Nullable;

/**
 * The base class for Menus for Blocks.
 */
public abstract class MenuBlockBase extends MenuBase {

    private final BlockEntityContainerBase blockEntity;

    /**
     * Creates a Menu.
     * @param menuType The type of the Menu.
     * @param containerID The container ID of the Menu.
     * @param blockEntity The Block Entity containing this menu.
     */
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
