package com.zaphaos.runolith.block.entity;

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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;

import java.util.Optional;

import org.jetbrains.annotations.Nullable;

import com.zaphaos.runolith.recipe.EnrichmentChamberRecipe;
import com.zaphaos.runolith.recipe.EnrichmentChamberRecipeInput;
import com.zaphaos.runolith.recipe.ModRecipes;
import com.zaphaos.runolith.screen.custom.EnrichmentChamberMenu;

public class EnrichmentChamberBlockEntity extends BlockEntity implements MenuProvider {
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
	private static final int SECONDARY_OUTPUT_SLOT = 2;
	
	protected final ContainerData data;
	private int progress = 0;
	private int maxProgress = 72;
	
	
	public EnrichmentChamberBlockEntity(BlockPos pos, BlockState blockState) {
		super(ModBlockEntities.ENRICHMENT_CHAMBER_BE.get(), pos, blockState);
		data = new ContainerData() {

			@Override
			public int get(int index) {
				return switch (index) {
				case 0 -> EnrichmentChamberBlockEntity.this.progress;
				case 1 -> EnrichmentChamberBlockEntity.this.maxProgress;
				default -> 0;
				};
			}

			@Override
			public void set(int index, int value) {
				switch (index) {
				case 0 -> EnrichmentChamberBlockEntity.this.progress = value;
				case 1 -> EnrichmentChamberBlockEntity.this.maxProgress = value;
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
		return Component.translatable("block.runolith.enrichment_chamber");
	}
	
	@Nullable
	@Override
	public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
		return new EnrichmentChamberMenu(i, inventory, this, this.data);
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
		pTag.putInt("enrichment_chamber.progress", progress);
		pTag.putInt("enrichment_chamber.max_progress", maxProgress);
		
		super.saveAdditional(pTag, pRegistries);
	}
	
	@Override
	protected void loadAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
		super.loadAdditional(pTag, pRegistries);
		
		itemHandler.deserializeNBT(pRegistries, pTag.getCompound("inventory"));
		progress = pTag.getInt("enrichment_chamber.progress");
		maxProgress = pTag.getInt("enrichment_chamber.max_progress");
	}
	
	public void tick(Level level, BlockPos blockPos, BlockState blockState) {
		if(hasRecipe()) {
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
	
	/*private void craftItem() {
		Optional<RecipeHolder<EnrichmentChamberRecipe>> recipe = getCurrentRecipe();
		ItemStack output = recipe.get().value().primaryOutput();
		itemHandler.extractItem(INPUT_SLOT, 1, false);
		itemHandler.setStackInSlot(OUTPUT_SLOT, new ItemStack(output.getItem(),
				itemHandler.getStackInSlot(OUTPUT_SLOT).getCount() + output.getCount()));
	}*/
	private void craftItem() {
	    Optional<RecipeHolder<EnrichmentChamberRecipe>> recipe = getCurrentRecipe();
	    if (recipe.isEmpty()) return;

	    EnrichmentChamberRecipe r = recipe.get().value();

	    // consume input
	    itemHandler.extractItem(INPUT_SLOT, 1, false);

	    // primary output
	    ItemStack primary = r.primaryOutput();
	    itemHandler.setStackInSlot(OUTPUT_SLOT,
	        new ItemStack(primary.getItem(),
	            itemHandler.getStackInSlot(OUTPUT_SLOT).getCount() + primary.getCount()));

	    // secondary output (optional)
	    ItemStack secondary = r.secondaryOutput();
	    if (!secondary.isEmpty() && level.random.nextFloat() < r.secondaryChance()) {
	        itemHandler.setStackInSlot(SECONDARY_OUTPUT_SLOT,
	            new ItemStack(secondary.getItem(),
	                itemHandler.getStackInSlot(SECONDARY_OUTPUT_SLOT).getCount() + secondary.getCount()));
	    }
	}

	private void resetProgress() {
		progress = 0;
		maxProgress = 72;
	}

	private boolean hasCraftingFinished() {
		return this.progress >= maxProgress;
	}

	private void increaseCraftingProgress() {
		progress++;
	}

	/*private boolean hasRecipe() {
		Optional<RecipeHolder<EnrichmentChamberRecipe>> recipe = getCurrentRecipe();
		if(recipe.isEmpty()) {
			return false;
		}
		
		ItemStack output = recipe.get().value().primaryOutput();
		return canInstertAmountIntoOutputSlot(output.getCount()) && canInsertItemIntoOutputSlot(output);
	}*/
	

	private Optional<RecipeHolder<EnrichmentChamberRecipe>> getCurrentRecipe() {
		return this.level.getRecipeManager()
				.getRecipeFor(ModRecipes.ENRICHMENT_CHAMBER_TYPE.get(), new EnrichmentChamberRecipeInput(itemHandler.getStackInSlot(INPUT_SLOT)), level);
	}
	/*
	private boolean canInsertItemIntoOutputSlot(ItemStack output) {
		return itemHandler.getStackInSlot(OUTPUT_SLOT).isEmpty() ||
				itemHandler.getStackInSlot(OUTPUT_SLOT).getItem() == output.getItem();
	}

	private boolean canInstertAmountIntoOutputSlot(int count) {
		int maxCount = itemHandler.getStackInSlot(OUTPUT_SLOT).isEmpty() ? 64 : itemHandler.getStackInSlot(OUTPUT_SLOT).getMaxStackSize();
		int currentCount = itemHandler.getStackInSlot(OUTPUT_SLOT).getCount();
		
		return maxCount >= currentCount + count;
	}*/
	
	private boolean hasRecipe() {
	    Optional<RecipeHolder<EnrichmentChamberRecipe>> recipe = getCurrentRecipe();
	    if (recipe.isEmpty()) return false;

	    EnrichmentChamberRecipe r = recipe.get().value();

	    // check primary
	    ItemStack primary = r.primaryOutput();
	    if (!canInsertItemIntoOutputSlot(primary, OUTPUT_SLOT) ||
	        !canInstertAmountIntoOutputSlot(primary.getCount(), OUTPUT_SLOT)) {
	        return false;
	    }

	    // check secondary (only if recipe has one)
	    ItemStack secondary = r.secondaryOutput();
	    if (!secondary.isEmpty()) {
	        if (!canInsertItemIntoOutputSlot(secondary, SECONDARY_OUTPUT_SLOT) ||
	            !canInstertAmountIntoOutputSlot(secondary.getCount(), SECONDARY_OUTPUT_SLOT)) {
	            return false;
	        }
	    }

	    return true;
	}
	private boolean canInsertItemIntoOutputSlot(ItemStack stack, int slot) {
	    return itemHandler.getStackInSlot(slot).isEmpty() ||
	           itemHandler.getStackInSlot(slot).getItem() == stack.getItem();
	}

	private boolean canInstertAmountIntoOutputSlot(int count, int slot) {
	    ItemStack existing = itemHandler.getStackInSlot(slot);
	    int maxCount = existing.isEmpty() ? 64 : existing.getMaxStackSize();
	    int currentCount = existing.getCount();
	    return maxCount >= currentCount + count;
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
