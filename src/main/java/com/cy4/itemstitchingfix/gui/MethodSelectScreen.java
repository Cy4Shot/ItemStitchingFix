package com.cy4.itemstitchingfix.gui;

import com.cy4.itemstitchingfix.config.ItemStitchingFixConfig;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.LiteralContents;

public class MethodSelectScreen extends Screen {

	Button selectButton;

	public MethodSelectScreen() {
		super(create("Select Method For Rendering"));
	}

	public void cycleButton() {
		ItemStitchingFixConfig.method.set(ItemStitchingFixConfig.method.get().next());
		selectButton.setMessage(create(ItemStitchingFixConfig.method.get().toString()));
	}

	@Override
	protected void init() {
		selectButton = new Button(this.width / 2 - 100, this.height / 2 - 10, 200, 20,
				create(ItemStitchingFixConfig.method.get().toString()), (p_96781_) -> {
					this.cycleButton();
				});
		this.addRenderableWidget(selectButton);

		this.addRenderableWidget(
				new Button(this.width / 2 - 100, this.height / 2 + 15, 200, 20, create("Save and Exit"), (p_96781_) -> {
					this.onClose();
					minecraft.reloadResourcePacks();
				}));
	}

	@Override
	public void render(PoseStack ps, int p_96563_, int p_96564_, float p_96565_) {
		this.renderBackground(ps);
		super.render(ps, p_96563_, p_96564_, p_96565_);
	}

	protected <T extends GuiEventListener & Widget & NarratableEntry> T addRenderableWidget(T p_169406_) {
		this.renderables.add(p_169406_);
		return this.addWidget(p_169406_);
	}

	private static Component create(String text) {
		return MutableComponent.create(new LiteralContents(text));
	}
}