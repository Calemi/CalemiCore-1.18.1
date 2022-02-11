package com.tm.calemicore.util.helper;

import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;

import java.util.Objects;

/**
 * Use this class for to help with Containers.
 */
public class ContainerHelper {

    /**
     * @param container The Container to count in.
     * @param stack The type of Item Stack to count.
     * @param exactNBT Should it only count the same NBT items?
     * @return The amount of a specified Item Stack in an Inventory.
     */
    public static int countItems(Container container, ItemStack stack, boolean exactNBT) {

        if (stack.getCount() > stack.getMaxStackSize()) {

            for (int i = 0; i < stack.getCount(); i++) {
                stack.setCount(1);
                countItems(container, stack, exactNBT);
            }
        }

        int count = 0;

        for (int slotId = 0; slotId < container.getContainerSize(); slotId++) {

            ItemStack stackInSlot = container.getItem(slotId);

            if (stackInSlot.sameItem(stack)) {

                if (exactNBT && stack.hasTag()) {

                    if (stackInSlot.hasTag() && Objects.equals(stackInSlot.getTag(), stack.getTag())) {
                        count += stackInSlot.getCount();
                    }
                }

                else count += stackInSlot.getCount();
            }
        }

        return count;
    }

    /**
     * Removes a specified amount of Items from an Inventory.
     * @param container The Container to remove from.
     * @param stack The type of Item Stack to remove.
     * @param amount The amount to remove.
     * @param exactNBT Should it only remove the same NBT items?
     */
    public static void consumeItems(Container container, ItemStack stack, int amount, boolean exactNBT) {

        int amountLeft = amount;

        if (countItems(container, stack, exactNBT) >= amount) {

            for (int slotId = 0; slotId < container.getContainerSize(); slotId++) {

                if (amountLeft <= 0) {
                    break;
                }

                ItemStack stackInSlot = container.getItem(slotId);

                if (!stackInSlot.isEmpty()) {

                    if (stackInSlot.sameItem(stack)) {

                        if (exactNBT && stack.hasTag()) {

                            if (!stackInSlot.hasTag() || !Objects.equals(stackInSlot.getTag(), stack.getTag())) {
                                continue;
                            }
                        }

                        if (amountLeft >= stackInSlot.getCount()) {
                            amountLeft -= stackInSlot.getCount();
                            container.setItem(slotId, ItemStack.EMPTY);
                        }

                        else {
                            ItemStack copy = stackInSlot.copy();
                            container.removeItem(slotId, amountLeft);
                            amountLeft -= copy.getCount();
                        }
                    }
                }
            }
        }
    }

    /**
     * @param container The Container to test.
     * @param stack The Item Stack to test.
     * @return true, if the given Item Stack can be inserted in the Container.
     */
    public static boolean canInsertStack(Container container, ItemStack stack) {

        int amountLeft = stack.getCount();

        for (int slotId = 0; slotId < container.getContainerSize(); slotId++) {

            ItemStack stackInSlot = container.getItem(slotId);

            if (container.canPlaceItem(slotId, stack)) {

                if (stackInSlot.isEmpty()) {
                    amountLeft -= stack.getMaxStackSize();
                }

                else if (ItemStack.isSame(stackInSlot, stack)) {

                    if (stack.hasTag()) {

                        if (!stackInSlot.hasTag() || ItemStack.isSameItemSameTags(stack, stackInSlot)) {
                            continue;
                        }
                    }

                    int spaceLeftInStack = stack.getMaxStackSize() - stackInSlot.getCount();
                    amountLeft -= spaceLeftInStack;
                }
            }
        }

        return amountLeft <= 0;
    }

    /**
     * Inserts the given ItemStack into the Container.
     * @param container The Container to insert in.
     * @param stack The ItemStack to insert.
     */
    public static void insertStack(Container container, ItemStack stack) {

        for (int slotId = 0; slotId < container.getContainerSize(); slotId++) {

            ItemStack stackInSlot = container.getItem(slotId);

            if (ItemStack.isSame(stackInSlot, stack) && (stackInSlot.getCount() + stack.getCount() <= stack.getMaxStackSize())) {

                if (stack.hasTag()) {

                    if (!ItemStack.isSameItemSameTags(stackInSlot, stack)) {
                        continue;
                    }
                }

                ItemStack stack2 = stack.copy();
                stack2.setCount(stackInSlot.getCount() + stack.getCount());

                container.setItem(slotId, stack2);
                break;
            }

            else if (stackInSlot.isEmpty()) {
                container.setItem(slotId, stack);
                break;
            }
        }
    }

    /**
     * Inserts the given ItemStack into the Container. Supports Item Stacks that have more than their max stack size.
     * @param container The Container to insert in.
     * @param stack The ItemStack to insert.
     */
    public static void insertOverflowingStack(Container container, ItemStack stack) {

        if (stack.getCount() >= stack.getMaxStackSize()) {

            int amountLeft = stack.getCount();

            while (amountLeft > 0) {

                ItemStack partialStack = stack.copy();
                int stackSize = Math.min(amountLeft, stack.getMaxStackSize());
                partialStack.setCount(stackSize);
                amountLeft -= stackSize;

                ContainerHelper.insertStack(container, partialStack);
            }
        }

        else ContainerHelper.insertStack(container, stack);
    }
}
