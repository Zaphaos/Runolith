package com.zaphaos.runolith.screen.custom;

import com.zaphaos.runolith.block.ModBlocks;
import com.zaphaos.runolith.block.entity.GrowthChamberBlockEntity;
import com.zaphaos.runolith.screen.ModMenuTypes;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.items.SlotItemHandler;

public class GrowthChamberMenu extends AbstractContainerMenu {
	public final GrowthChamberBlockEntity blockEntity;
	private final Level level;
	private final ContainerData data;
	
	/*public EnrichmentChamberMenu(int pContainerId, Inventory inv, FriendlyByteBuf extraData) {
		this(pContainerId, inv, inv.player.level().getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(2));
	}
	
	public EnrichmentChamberMenu(int pContainerId, Inventory inv, BlockEntity entity, ContainerData data) {
		super(ModMenuTypes.ENRICHMENT_CHAMBER_MENU.get(), pContainerId);
		this.blockEntity = ((EnrichmentChamberBlockEntity) entity);
		this.level = inv.player.level();
		this.data = data;
		addPlayerInventory(inv);
		addPlayerHotbar(inv);
		
		this.addSlot(new SlotItemHandler(blockEntity.itemHandler, 0, 54, 34));
		this.addSlot(new SlotItemHandler(blockEntity.itemHandler, 1, 104, 34) {
		    @Override
		    public boolean mayPlace(ItemStack stack) {
		        return false; // nothing can be placed here manually
		    }});
		this.addSlot(new SlotItemHandler(blockEntity.itemHandler, 2, 154, 34) {
		    @Override
		    public boolean mayPlace(ItemStack stack) {
		        return false; // nothing can be placed here manually
		    }}); //this one is new
		
		addDataSlots(data);
	}*/
	public GrowthChamberMenu(int id, Inventory inv, FriendlyByteBuf buf) {
	    this(id, inv, (GrowthChamberBlockEntity) inv.player.level().getBlockEntity(buf.readBlockPos()),
	         new SimpleContainerData(2));
	}

	public GrowthChamberMenu(int id, Inventory inv, GrowthChamberBlockEntity be, ContainerData data) {
	    super(ModMenuTypes.ENRICHMENT_CHAMBER_MENU.get(), id);
	    this.blockEntity = be;
	    this.level = inv.player.level();
	    this.data = data;
	    addPlayerInventory(inv);
	    addPlayerHotbar(inv);

	    this.addSlot(new SlotItemHandler(be.itemHandler, 0, 54, 34));
	    this.addSlot(new SlotItemHandler(be.itemHandler, 1, 104, 34) {
	        @Override public boolean mayPlace(ItemStack stack) { return false; }
	    });

	    addDataSlots(data);
	}

	
	public boolean isCrafting() {
		return data.get(0) > 0;
	}
	
	public int getScaledArrowProgress() {
		int progress = this.data.get(0);
		int maxProgress = this.data.get(1);
		int arrowPixelSize = 24;
		
		return maxProgress != 0 && progress != 0 ? progress * arrowPixelSize / maxProgress : 0;
	}

	private static final int HOTBAR_SLOT_COUNT = 9;
	private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
	private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
	private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
	private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
	private static final int VANILLA_FIRST_SLOT_INDEX = 0;
	private static final int TE_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;
	
	//This one has to be defined!
	private static final int TE_INVENTORY_SLOT_COUNT = 3; //Must be correct number of slots from modded block entity! //used to be 2
	@Override
	public ItemStack quickMoveStack(Player playerIn, int pIndex) {
		Slot sourceSlot = slots.get(pIndex);
		if (sourceSlot == null || !sourceSlot.hasItem()) return ItemStack.EMPTY;
		ItemStack sourceStack = sourceSlot.getItem();
		ItemStack copyOfSourceStack = sourceStack.copy();
		
		//check if the slot clicked is one of the vanilla container slots
		if (pIndex  < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT) {
			//this is a vanilla container slot, so merge the stack into the tile inventory
			if (!moveItemStackTo(sourceStack, TE_INVENTORY_FIRST_SLOT_INDEX, TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT, false)) {
				return ItemStack.EMPTY; // EMPTY_ITEM
			}
		} else if (pIndex < TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT) {
			//this is a TE slot, so merge the stack into the player inventory
			if (!moveItemStackTo(sourceStack, VANILLA_FIRST_SLOT_INDEX, VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT, false)) {
				return ItemStack.EMPTY;
			}
		} else {
			System.out.println("Invalid slotIndex" + pIndex);
			return ItemStack.EMPTY;
		}
		//if stack size == 0 (the entire stack was moved) set slot to null
		if (sourceStack.getCount() == 0) {
			sourceSlot.set(ItemStack.EMPTY);
		} else {
			sourceSlot.setChanged();
		}
		sourceSlot.onTake(playerIn, sourceStack);
		return copyOfSourceStack;
	}

	@Override
	public boolean stillValid(Player pPlayer) {
		return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()),
				pPlayer, ModBlocks.ENRICHMENT_CHAMBER.get());
	}
	
	private void addPlayerInventory(Inventory playerInventory) {
		for (int i = 0; i < 3; ++i) {
			for (int l = 0; l < 9; ++l) {
				this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 84 + i * 18));
			}
		}
	}
	
	private void addPlayerHotbar(Inventory playerInventory) {
		for (int i = 0; i < 9; ++i) {
			this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
		}
	}	
}
