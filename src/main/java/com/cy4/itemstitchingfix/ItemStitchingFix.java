package com.cy4.itemstitchingfix;

import org.apache.commons.lang3.tuple.Pair;

import com.cy4.itemstitchingfix.config.Config;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.fml.network.FMLNetworkConstants;

@Mod("itemstitchingfix")
public class ItemStitchingFix {
	public ItemStitchingFix() {
		ModLoadingContext.get().registerExtensionPoint(ExtensionPoint.DISPLAYTEST,
				() -> Pair.of(() -> FMLNetworkConstants.IGNORESERVERONLY, (a, b) -> true));
		ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.CLIENT);
		Config.instance.loadConfig(Config.CLIENT, FMLPaths.CONFIGDIR.get().resolve("itemstitchingfix-client.toml"));
		MinecraftForge.EVENT_BUS.register(this);
	}
}
