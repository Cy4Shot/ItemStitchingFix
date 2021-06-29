package com.cy4.itemstitchingfix;

import com.cy4.itemstitchingfix.config.Config;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.loading.FMLPaths;

@Mod("itemstitchingfix")
public class ItemStitchingFix {
	public ItemStitchingFix() {
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.COMMON);
		Config.instance.loadConfig(Config.COMMON, FMLPaths.CONFIGDIR.get().resolve("itemstitchingfix-common.toml"));
		MinecraftForge.EVENT_BUS.register(this);
	}
}
