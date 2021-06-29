package com.cy4.itemstitchingfix.config;

import java.nio.file.Path;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.config.ModConfig;

public class Config {
	public static Config instance = new Config();

	private Config() {

	}

	public static final ForgeConfigSpec COMMON = ConfigValues.build();

	public void loadConfig(ForgeConfigSpec spec, Path path) {

		final CommentedFileConfig configData = CommentedFileConfig.builder(path).sync().autosave()
				.writingMode(WritingMode.REPLACE).build();

		configData.load();
		spec.setConfig(configData);
		ConfigValues.pushChanges();
	}

	@SubscribeEvent
	public void onLoad(final ModConfig.Loading configEvent) {
		ConfigValues.pushChanges();
	}

	@SubscribeEvent
	public void onFileChange(final ModConfig.Reloading configEvent) {
		ConfigValues.pushChanges();
	}
}
