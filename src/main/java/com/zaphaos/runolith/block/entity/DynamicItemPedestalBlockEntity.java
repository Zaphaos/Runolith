package com.zaphaos.runolith.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class DynamicItemPedestalBlockEntity extends BlockEntity {

	public DynamicItemPedestalBlockEntity(BlockPos pos, BlockState blockState) {
		super(ModBlockEntities.DYNAMIC_ITEM_PEDESTAL_BE.get(), pos, blockState);
	}

	public void drops() {
		// TODO Auto-generated method stub
		
	}

}
