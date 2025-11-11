package com.zaphaos.runolith.block.custom;

import org.jetbrains.annotations.Nullable;

import com.mojang.serialization.MapCodec;
import com.zaphaos.runolith.block.entity.DynamicItemPedestalBlockEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class DynamicItemPedestalBlock extends BaseEntityBlock {
	public static final VoxelShape SHAPE = Block.box(2, 0, 2, 14, 13, 14);
	public static final MapCodec<DynamicItemPedestalBlock> CODEC = simpleCodec(DynamicItemPedestalBlock::new);

	public DynamicItemPedestalBlock(Properties properties) {
		super(properties);
	}
	
	@Override
	protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return SHAPE;
		
	}

	@Override
	protected MapCodec<? extends BaseEntityBlock> codec() {
		return CODEC;
	}
	
	@Override
	protected RenderShape getRenderShape(BlockState state) {
		return RenderShape.MODEL;
	}
	
	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return null;
	}
	
	@Override
	protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
		if(state.getBlock() != newState.getBlock()) {
			if(level.getBlockEntity(pos) instanceof DynamicItemPedestalBlockEntity dynamicItemPedestalBlockEntity) {
				dynamicItemPedestalBlockEntity.drops();
				level.updateNeighborsAt(pos, this);
			}
		}
		super.onRemove(state, level, pos, newState, movedByPiston);
	}
	
	@Override
	protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
		return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
	}

}
