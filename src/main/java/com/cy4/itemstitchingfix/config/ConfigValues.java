package com.cy4.itemstitchingfix.config;

import com.cy4.itemstitchingfix.util.ModelGenerationType;

import net.minecraftforge.common.ForgeConfigSpec;

public class ConfigValues {
	
	public static ForgeConfigSpec.EnumValue<ModelGenerationType> type;

    public static ForgeConfigSpec build() {
        ForgeConfigSpec.Builder builder =  new ForgeConfigSpec.Builder();
        type = builder.defineEnum("generationType", ModelGenerationType.OUTLINE);
        return builder.build();
    }

    public static void pushChanges() {
        Settings.type = type.get();
    }
}
