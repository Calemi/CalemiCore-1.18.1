package com.tm.calemicore.util.helper;

import com.tm.calemicore.util.Location;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

/**
 * Used to help with placing Blocks.
 */
public class BlockHelper {

    /**
     * Places a block in the next available place in a row.
     * @param player The Player placing the Blocks.
     * @param block The Block that gets placed.
     * @param originPos The origin of the search.
     * @param rowDirection The Direction of the row.
     * @param maxSearchSize The maximum amount of Blocks that will be placed.
     */
    public static void placeBlockRow(Player player, Block block, BlockPos originPos, Direction rowDirection, int maxSearchSize) {

        Location location = new Location(player.getLevel(), originPos);
        ItemStack currentStack = player.getMainHandItem();

        //Checks if the held stack is a Block.
        if (currentStack.getItem() == Item.BY_BLOCK.get(block)) {

            //Iterates through every possible placement in a line.
            for (int i = 0; i < maxSearchSize; i++) {

                Location nextLocation = new Location(location, rowDirection, i);

                //Checks if a different Block/AIR has been found.
                if (nextLocation.getBlock() != block) {

                    //Checks if the Block be placed here.
                    if (nextLocation.isBlockValidForPlacing()) {

                        nextLocation.setBlock(block);
                        SoundHelper.playBlockPlace( nextLocation, nextLocation.getBlockState());
                        ContainerHelper.consumeItems(player.getInventory(), new ItemStack(block), 1, false);
                    }

                    break;
                }
            }
        }
    }
}
