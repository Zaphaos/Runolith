package com.zaphaos.runolith.block.entity;

import org.jetbrains.annotations.Nullable;

import com.zaphaos.runolith.screen.custom.GrowthChamberMenu;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;

public class GrowthChamberBlockEntity extends BlockEntity implements MenuProvider {
	public final ItemStackHandler itemHandler = new ItemStackHandler(3) { //2 for 1 output
		@Override
		protected void onContentsChanged(int slot) {
			setChanged();
			if(!level.isClientSide()) {
				level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
			}
		}
	};
	
	private static final int INPUT_SLOT = 0;
	private static final int OUTPUT_SLOT = 1;
	
	protected final ContainerData data;
	private int progress = 0;
	private int maxProgress = 720;
	
	public GrowthChamberBlockEntity(BlockPos pos, BlockState blockState) {
		super(ModBlockEntities.GROWTH_CHAMBER_BE.get(), pos, blockState);
		data = new ContainerData() {

			@Override
			public int get(int index) {
				return switch (index) {
				case 0 -> GrowthChamberBlockEntity.this.progress;
				case 1 -> GrowthChamberBlockEntity.this.maxProgress;
				default -> 0;
				};
			}

			@Override
			public void set(int index, int value) {
				switch (index) {
				case 0 -> GrowthChamberBlockEntity.this.progress = value;
				case 1 -> GrowthChamberBlockEntity.this.maxProgress = value;
				};
			}

			@Override
			public int getCount() {
				return 2;
			}
		};
	}

	@Override
	public Component getDisplayName() {
		return Component.translatable("block.runolith.growth_chamber");
	}

	@Nullable
	@Override
	public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
		return new GrowthChamberMenu(i, inventory, this, this.data);
	}
	
	public void drops() {
		SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
		for (int i = 0; i < itemHandler.getSlots(); i++) {
			inventory.setItem(i, itemHandler.getStackInSlot(i));
		};
		Containers.dropContents(this.level, this.worldPosition, inventory);
	}
	
	@Override
	protected void saveAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
		pTag.put("inventory", itemHandler.serializeNBT(pRegistries));
		pTag.putInt("growth_chamber.progress", progress);
		pTag.putInt("growth_chamber.max_progress", maxProgress);
		
		super.saveAdditional(pTag, pRegistries);
	}
	
	@Override
	protected void loadAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
		super.loadAdditional(pTag, pRegistries);
		
		itemHandler.deserializeNBT(pRegistries, pTag.getCompound("inventory"));
		progress = pTag.getInt("growth_chamber.progress");
		maxProgress = pTag.getInt("growth_chamber.max_progress");
	}
	
	public void tick(Level level, BlockPos blockPos, BlockState blockState) {
		if (hasGem() && hasFuel()) {
			increaseCraftingProgress();
			setChanged(level, blockPos, blockState);
			if(hasCraftingFinished()) {
				craftItem();
				resetProgress();
			}
		} else {
			resetProgress();
		}
	}
	
	private void craftItem() {
		
	}

	private boolean hasCraftingFinished() {
		return this.progress >= maxProgress;
	}

	private void resetProgress() {
		progress = 0;
		maxProgress = 720;
	}

	private void increaseCraftingProgress() {
		progress++;
	}

	private boolean hasFuel() {
		return false;
	}

	private boolean hasGem() {
		return false;
	}
	
	@Override
	public CompoundTag getUpdateTag(HolderLookup.Provider pRegistries) {
		return saveWithoutMetadata(pRegistries);
	}
	
	@Nullable
	@Override
	public Packet <ClientGamePacketListener> getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(this);
	}
	public ContainerData getData() {
	    return this.data;
	}
}
