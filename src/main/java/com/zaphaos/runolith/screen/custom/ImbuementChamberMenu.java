package com.zaphaos.runolith.screen.custom;

import com.zaphaos.runolith.block.ModBlocks;
import com.zaphaos.runolith.block.entity.ImbuementChamberBlockEntity;
import com.zaphaos.runolith.datamaps.ModDataMaps;
import com.zaphaos.runolith.screen.ModMenuTypes;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.items.SlotItemHandler;

public class ImbuementChamberMenu extends AbstractContainerMenu {
	public final ImbuementChamberBlockEntity blockEntity;
	private final Level level;
	private final ContainerData data;
	
	public ImbuementChamberMenu(int id, Inventory inv, FriendlyByteBuf buf) {
	    this(id, inv, (ImbuementChamberBlockEntity) inv.player.level().getBlockEntity(buf.readBlockPos()),
	         new SimpleContainerData(2));
	}

	public ImbuementChamberMenu(int id, Inventory inv, ImbuementChamberBlockEntity be, ContainerData data) {
	    super(ModMenuTypes.IMBUEMENT_CHAMBER_MENU.get(), id);
	    this.blockEntity = be;
	    this.level = inv.player.level();
	    this.data = data;
	    addPlayerInventory(inv);
	    addPlayerHotbar(inv);

	    this.addSlot(new SlotItemHandler(be.itemHandler, 0, 80, 47));
	    this.addSlot(new SlotItemHandler(be.itemHandler, 1, 80, 24) {
	        @Override public boolean mayPlace(ItemStack stack) { return false; }
	    });
	    this.addSlot(new SlotItemHandler(be.itemHandler, 2, 26, 47) {
	    	@Override public boolean mayPlace(ItemStack stack)
	    	{
	    		Item item = stack.getItem();
	            var holder = BuiltInRegistries.ITEM.wrapAsHolder(item);
	            var fuel = holder.getData(ModDataMaps.IMBUEMENT_FUEL);
	            return fuel != null;
	    	}
	    });

	    addDataSlots(data);
	}

	
	public boolean isCrafting() {
		return data.get(0) > 0;
	}
	
	public int getScaledProgress() {
		int progress = this.data.get(0);
		int maxProgress = this.data.get(1);
		int progressPixelSize = 42;
		
		return maxProgress != 0 && progress != 0 ? progress * progressPixelSize / maxProgress : 0;
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
	public ItemStack quickMoveStack(Player player, int pIndex) {
	    Slot sourceSlot = slots.get(pIndex);
	    if (sourceSlot == null || !sourceSlot.hasItem()) return ItemStack.EMPTY;
	    ItemStack sourceStack = sourceSlot.getItem();
	    ItemStack copyOfSourceStack = sourceStack.copy();

	    // check if the slot clicked is one of the vanilla container slots
	    if (pIndex < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT) {
	        // from player inventory → try TE slots
	        if (this.slots.get(TE_INVENTORY_FIRST_SLOT_INDEX + ImbuementChamberBlockEntity.FUEL_SLOT).mayPlace(sourceStack)) {
	            if (!moveItemStackTo(sourceStack,
	                    TE_INVENTORY_FIRST_SLOT_INDEX + ImbuementChamberBlockEntity.FUEL_SLOT,
	                    TE_INVENTORY_FIRST_SLOT_INDEX + ImbuementChamberBlockEntity.FUEL_SLOT + 1,
	                    false)) {
	                return ItemStack.EMPTY;
	            }
	        }
	        // otherwise, try input slot(s)
	        else if (!moveItemStackTo(sourceStack,
	                TE_INVENTORY_FIRST_SLOT_INDEX + ImbuementChamberBlockEntity.INPUT_SLOT,
	                TE_INVENTORY_FIRST_SLOT_INDEX + ImbuementChamberBlockEntity.OUTPUT_SLOT, // exclusive → won’t include output
	                false)) {
	            return ItemStack.EMPTY; // EMPTY_ITEM
	        }
	    }
	    // from TE slot → move into player inventory
	    else if (pIndex < TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT) {
	        if (!moveItemStackTo(sourceStack, VANILLA_FIRST_SLOT_INDEX, VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT, false)) {
	            return ItemStack.EMPTY;
	        }
	    } else {
	        System.out.println("Invalid slotIndex " + pIndex);
	        return ItemStack.EMPTY;
	    }

	    if (sourceStack.isEmpty()) {
	        sourceSlot.set(ItemStack.EMPTY);
	    } else {
	        sourceSlot.setChanged();
	    }
	    sourceSlot.onTake(player, sourceStack);
	    return copyOfSourceStack;
	}

	@Override
	public boolean stillValid(Player pPlayer) {
		return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()),
				pPlayer, ModBlocks.IMBUEMENT_CHAMBER.get());
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
