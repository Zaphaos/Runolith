package com.zaphaos.runolith.screen.custom;

import com.mojang.blaze3d.systems.RenderSystem;
import com.zaphaos.runolith.Runolith;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class ImbuementChamberScreen extends AbstractContainerScreen<ImbuementChamberMenu> {
	
	private static final ResourceLocation GUI_TEXTURE =
			ResourceLocation.fromNamespaceAndPath(Runolith.MODID, "textures/gui/imbuement_chamber/imbuement_chamber_gui.png");
	private static final ResourceLocation PROGRESS_TEXTURE =
			ResourceLocation.fromNamespaceAndPath(Runolith.MODID, "textures/gui/imbuement_chamber/imbuement_chamber_progress.png");
	
	public ImbuementChamberScreen(ImbuementChamberMenu menu, Inventory playerInventory, Component title) {
		super(menu, playerInventory, title);
	}

	@Override
	protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		RenderSystem.setShaderTexture(0, GUI_TEXTURE);
		int x = (width - imageWidth) / 2;
		int y = (height - imageHeight) / 2;
		
		guiGraphics.blit(GUI_TEXTURE, x, y, 0, 0, imageWidth, imageHeight);
		
		renderProgressArrow(guiGraphics, x, y);
	}
	
	private void renderProgressArrow(GuiGraphics guiGraphics, int x, int y) {
	    if (menu.isCrafting()) {
	        int progress = menu.getScaledProgress();
	        guiGraphics.blit(PROGRESS_TEXTURE, x + 60, y + 23 + (42 - progress), 0, 42 - progress, 53, progress, 53, 42
	        );
	    }
	}
	
	@Override
	public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
		super.render(pGuiGraphics, pMouseX,pMouseY, pPartialTick);
		this.renderTooltip(pGuiGraphics, pMouseX, pMouseY);
	}
	
	@Override
	protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        guiGraphics.drawString(this.font, this.title, this.titleLabelX, this.titleLabelY, 4210752, false);
        guiGraphics.drawString(this.font, this.playerInventoryTitle, this.inventoryLabelX, this.inventoryLabelY, 4210752, false);
    }
}
