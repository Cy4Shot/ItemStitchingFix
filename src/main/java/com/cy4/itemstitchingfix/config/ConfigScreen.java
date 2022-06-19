package com.cy4.itemstitchingfix.config;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.StringTextComponent;

public class ConfigScreen extends Screen {

	Button selectButton;

	public ConfigScreen() {
		super(new StringTextComponent("Select Method For Rendering"));
	}

	public void cycleButton() {
		ConfigValues.type.set(ConfigValues.type.get().next());
		selectButton.setMessage(new StringTextComponent(ConfigValues.type.get().toString()));
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void init() {
		selectButton = new Button(this.width / 2 - 100, this.height / 2 - 10, 200, 20,
				new StringTextComponent(ConfigValues.type.get().toString()), (p_96781_) -> {
					this.cycleButton();
				});
		this.addRenderableWidget(selectButton);

		this.addRenderableWidget(new Button(this.width / 2 - 100, this.height / 2 + 15, 200, 20,
				new StringTextComponent("Save and Exit"), (p_96781_) -> {
					this.onClose();
					minecraft.reloadResources();
				}));
	}

	@Override
	public void render(MatrixStack ps, int p_96563_, int p_96564_, float p_96565_) {
		this.renderBackground(ps);
		super.render(ps, p_96563_, p_96564_, p_96565_);
	}

	protected Button addRenderableWidget(Button p_169406_) {
		this.buttons.add(p_169406_);
		return this.addButton(p_169406_);
	}

}