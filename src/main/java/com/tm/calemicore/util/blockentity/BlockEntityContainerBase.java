package com.tm.calemicore.util.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.Nameable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Base class for Block Entities with Containers.
 */
public abstract class BlockEntityContainerBase extends BlockEntityBase implements Container, MenuProvider, Nameable {

    /**
     * The list for all items contained in the Block Entity.
     */
    public NonNullList<ItemStack> items = NonNullList.withSize(getContainerSize(), ItemStack.EMPTY);

    /**
     * Creates a Block Entity.
     * @param type The type of the Block Entity.
     * @param pos The position of the Block Entity.
     * @param state The Block State of the Block Entity.
     */
    public BlockEntityContainerBase(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    /*
        Name Methods
     */

    /**
     * @return The default name of the Block Entity. Rendering uses this unless renamed.
     */
    protected abstract Component getDefaultName();

    @Override
    public Component getName() {
        return getDefaultName();
    }

    @Override
    public Component getDisplayName() {
        return getName();
    }

    /*
        Container Methods
     */

    @Override
    public ItemStack getItem(int index) {
        return items.get(index);
    }

    @Override
    public ItemStack removeItem(int index, int count) {

        ItemStack stack = ContainerHelper.removeItem(items, index, count);

        if (!stack.isEmpty()) {
            setChanged();
        }

        return stack;
    }

    @Override
    public ItemStack removeItemNoUpdate(int index) {
        return ContainerHelper.takeItem(items, index);
    }

    @Override
    public void setItem(int index, ItemStack stack) {

        items.set(index, stack);

        if (stack.getCount() > getMaxStackSize()) {
            stack.setCount(getMaxStackSize());
        }

        setChanged();
    }

    @Override
    public boolean stillValid(Player player) {
        return level.getBlockEntity(worldPosition) == this;
    }

    @Override
    public boolean isEmpty() {
        return items.stream().allMatch(ItemStack::isEmpty);
    }

    @Override
    public void clearContent() {
        items.clear();
    }

    /*
        NBT Methods
     */

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(tag, this.items);
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        ContainerHelper.saveAllItems(tag, this.items);
    }
}
