package com.cy4.itemstitchingfix;

import org.apache.commons.lang3.tuple.Pair;
import org.lwjgl.glfw.GLFW;

import com.cy4.itemstitchingfix.config.Config;
import com.cy4.itemstitchingfix.config.ConfigScreen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent.KeyInputEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.fml.network.FMLNetworkConstants;

@Mod("itemstitchingfix")
public class ItemStitchingFix {

	public static KeyBinding openGui = new KeyBinding("key.itemstitchingfix.open_gui", GLFW.GLFW_KEY_I,
			"key.itemstitchingfix.categories.itemstitchingfix");

	public ItemStitchingFix() {
		ModLoadingContext.get().registerExtensionPoint(ExtensionPoint.DISPLAYTEST,
				() -> Pair.of(() -> FMLNetworkConstants.IGNORESERVERONLY, (a, b) -> true));
		ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.CLIENT);
		Config.instance.loadConfig(Config.CLIENT, FMLPaths.CONFIGDIR.get().resolve("itemstitchingfix-client.toml"));
		MinecraftForge.EVENT_BUS.register(this);
	}

	@Mod.EventBusSubscriber(modid = "itemstitchingfix", bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
	public static class ClientModEvents {

		@SubscribeEvent
		public static void clientSetup(FMLClientSetupEvent evt) {
			ClientRegistry.registerKeyBinding(openGui);
		}
	}

	@Mod.EventBusSubscriber(modid = "itemstitchingfix", bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
	public static class ClientForgeEvents {

		@SubscribeEvent
		public static void onKey(KeyInputEvent evt) {
			if (openGui.isKeyDown()) {
				Minecraft.getInstance().displayGuiScreen(new ConfigScreen());
			}
		}
	}
}
