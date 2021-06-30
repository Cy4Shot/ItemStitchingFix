package com.cy4.itemstitchingfix.config;

import com.cy4.itemstitchingfix.config.Settings.ModelGenerationType;

import net.minecraftforge.common.ForgeConfigSpec;

public class ConfigValues {

	public static ForgeConfigSpec.EnumValue<ModelGenerationType> type;

	public static ForgeConfigSpec build() {
		ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
		builder.comment("VANILLA - No changes to texture stitching.",
				"UNLERP - Applies a fake stitching algorithm to make the stitching seem correct.",
				"OUTLINE - Same as unlerp, but also prevents enchantment flickering.",
				"PIXEL - Generates each pixel seperately for best results. Can lag with high res resource packs.");
		type = builder.defineEnum("generationType", ModelGenerationType.PIXEL);
		return builder.build();
	}

	public static void pushChanges() {
		Settings.type = type.get();
	}
}
