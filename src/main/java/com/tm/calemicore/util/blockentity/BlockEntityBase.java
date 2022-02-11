package com.tm.calemicore.util.blockentity;

import com.tm.calemicore.util.Location;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

/**
 * Base class for Block Entities
 */
public abstract class BlockEntityBase extends BlockEntity {

    /**
     * Creates a Block Entity.
     * @param type The type of the Block Entity.
     * @param pos The position of the Block Entity.
     * @param state The Block State of the Block Entity.
     */
    public BlockEntityBase(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    /**
     * @return The Location of the Block Entity.
     */
    public Location getLocation() {
        return new Location(getLevel(), getBlockPos());
    }

    /**
     * Call this method to sync server NBT data to the all clients.
     */
    public void markUpdated() {

        if (getLevel() != null ) {
            setChanged();
            getLevel().sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
        }
    }

    /*
        Packet Methods
     */

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        if (pkt.getTag() != null) load(pkt.getTag());
    }

    @Override
    public void handleUpdateTag(CompoundTag tag) {
        load(tag);
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = new CompoundTag();
        saveAdditional(tag);
        return tag;
    }

    @Override
    public CompoundTag getTileData() {
        CompoundTag tag = new CompoundTag();
        saveAdditional(tag);
        return tag;
    }
}