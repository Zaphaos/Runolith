package com.zaphaos.runolith.block.entity.renderer;

import com.zaphaos.runolith.block.custom.DynamicItemPedestalBlock;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class DynamicItemPedestalRenderer<T extends DynamicItemPedestalBlock> extends EntityRenderer {

	protected DynamicItemPedestalRenderer(Context context) {
		super(context);
		
	}

	@Override
	public ResourceLocation getTextureLocation(Entity entity) {
		// TODO Auto-generated method stub
		return null;
	}

}
