package com.zaphaos.runolith.block.custom;

import org.jetbrains.annotations.Nullable;

import com.mojang.serialization.MapCodec;
import com.zaphaos.runolith.block.entity.ModBlockEntities;
import com.zaphaos.runolith.block.entity.ImbuementChamberBlockEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class ImbuementChamberBlock extends BaseEntityBlock {
public static final MapCodec<ImbuementChamberBlock> CODEC = simpleCodec(ImbuementChamberBlock::new);
	
	public ImbuementChamberBlock(Properties properties) {
		super(properties);
	}
	
	@Override
	protected MapCodec<? extends BaseEntityBlock> codec() {
		return CODEC;
	}
	
	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
		return new ImbuementChamberBlockEntity(blockPos, blockState);
	}
	
	@Override
	protected RenderShape getRenderShape(BlockState state) {
		return RenderShape.MODEL;
	}
	
	@Override
	public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
		if (pState.getBlock() != pNewState.getBlock()) {
			BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
			if (blockEntity instanceof ImbuementChamberBlockEntity imbuementChamberBlockEntity) {
				imbuementChamberBlockEntity.drops();
			}
		}
		super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
	}
	
	@Override
	protected ItemInteractionResult useItemOn(ItemStack pStack, BlockState pState, Level pLevel, BlockPos pPos,
											  Player pPlayer, InteractionHand pHand, BlockHitResult pHitResult) {
		if (!pLevel.isClientSide()) {
			BlockEntity entity = pLevel.getBlockEntity(pPos);
			if(entity instanceof ImbuementChamberBlockEntity imbuementChamberBlockEntity) {
				((ServerPlayer) pPlayer).openMenu(new SimpleMenuProvider(imbuementChamberBlockEntity, Component.literal("Enrichment Chamber")), pPos);
			} else {
				throw new IllegalStateException("Our Container Provider is missing!");
			}
		}
		return ItemInteractionResult.sidedSuccess(pLevel.isClientSide());
	}
	
	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
		if (level.isClientSide()) {
			return null;
		}
		return createTickerHelper(blockEntityType, ModBlockEntities.IMBUEMENT_CHAMBER_BE.get(),
				(level1, blockPos, blockState, blockEntity) -> blockEntity.tick(level1, blockPos, blockState));
	}
}
