package com.zaphaos.runolith.block.entity;

import java.util.Optional;

import org.jetbrains.annotations.Nullable;

import com.zaphaos.runolith.datamaps.ModDataMaps;
import com.zaphaos.runolith.recipe.EnrichmentChamberRecipe;
import com.zaphaos.runolith.recipe.EnrichmentChamberRecipeInput;
import com.zaphaos.runolith.recipe.ImbuementChamberRecipe;
import com.zaphaos.runolith.recipe.ImbuementChamberRecipeInput;
import com.zaphaos.runolith.recipe.ModRecipes;
import com.zaphaos.runolith.screen.custom.ImbuementChamberMenu;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
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

public class ImbuementChamberBlockEntity extends BlockEntity implements MenuProvider {
	public final ItemStackHandler itemHandler = new ItemStackHandler(3) {
		@Override
		protected void onContentsChanged(int slot) {
			setChanged();
			if(!level.isClientSide()) {
				level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
			}
		}
	};
	
	public static final int INPUT_SLOT = 0;
	public static final int OUTPUT_SLOT = 1;
	public static final int FUEL_SLOT = 2;
	
	protected final ContainerData data;
	private int progress = 0;
	private int maxProgress = 144;
	
	
	public ImbuementChamberBlockEntity(BlockPos pos, BlockState blockState) {
		super(ModBlockEntities.IMBUEMENT_CHAMBER_BE.get(), pos, blockState);
		data = new ContainerData() {

			@Override
			public int get(int index) {
				return switch (index) {
				case 0 -> ImbuementChamberBlockEntity.this.progress;
				case 1 -> ImbuementChamberBlockEntity.this.maxProgress;
				default -> 0;
				};
			}

			@Override
			public void set(int index, int value) {
				switch (index) {
				case 0 -> ImbuementChamberBlockEntity.this.progress = value;
				case 1 -> ImbuementChamberBlockEntity.this.maxProgress = value;
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
		return Component.translatable("block.runolith.imbuement_chamber");
	}
	
	@Nullable
	@Override
	public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
		return new ImbuementChamberMenu(i, inventory, this, this.data);
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
		pTag.putInt("imbuement_chamber.progress", progress);
		pTag.putInt("imbuement_chamber.max_progress", maxProgress);
		
		super.saveAdditional(pTag, pRegistries);
	}
	
	@Override
	protected void loadAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
		super.loadAdditional(pTag, pRegistries);
		
		itemHandler.deserializeNBT(pRegistries, pTag.getCompound("inventory"));
		progress = pTag.getInt("imbuement_chamber.progress");
		maxProgress = pTag.getInt("imbuement_chamber.max_progress");
	}
	
	public void tick(Level level, BlockPos blockPos, BlockState blockState) {
		if(hasRecipe() && hasFuel()) {
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

	private boolean hasRecipe() {
		Optional<RecipeHolder<ImbuementChamberRecipe>> recipe = getCurrentRecipe();
	    if (recipe.isEmpty()) return false;

	    ImbuementChamberRecipe r = recipe.get().value();

	    // check primary
	    ItemStack primary = r.output();
	    if (!canInsertItemIntoOutputSlot(primary, OUTPUT_SLOT) ||
	        !canInstertAmountIntoOutputSlot(primary.getCount(), OUTPUT_SLOT)) {
	        return false;
	    }

	    return true;
	}
	
	private Optional<RecipeHolder<ImbuementChamberRecipe>> getCurrentRecipe() {
		return this.level.getRecipeManager()
				.getRecipeFor(ModRecipes.IMBUEMENT_CHAMBER_TYPE.get(), new ImbuementChamberRecipeInput(itemHandler.getStackInSlot(INPUT_SLOT)), level);
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

	private boolean hasFuel() {
		var holder =  BuiltInRegistries.ITEM.wrapAsHolder(itemHandler.getStackInSlot(FUEL_SLOT).getItem());
		var fuel = holder.getData(ModDataMaps.IMBUEMENT_FUEL);
		return fuel != null;
	}
	
	private void increaseCraftingProgress() {
		progress++;
	}

	private boolean hasCraftingFinished() {
		return this.progress >= maxProgress;
	}

	private void craftItem() {
		Optional<RecipeHolder<ImbuementChamberRecipe>> recipe = getCurrentRecipe();
	    if (recipe.isEmpty()) return;

	    ImbuementChamberRecipe r = recipe.get().value();

	    // consume input
	    itemHandler.extractItem(INPUT_SLOT, 1, false);
	    
	    //consume fuel
	    itemHandler.extractItem(FUEL_SLOT, 1, false);

	    // primary output
	    ItemStack primary = r.output();
	    itemHandler.setStackInSlot(OUTPUT_SLOT,
	        new ItemStack(primary.getItem(),
	            itemHandler.getStackInSlot(OUTPUT_SLOT).getCount() + primary.getCount()));
	}

	private void resetProgress() {
		progress = 0;
		maxProgress = 144;
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
