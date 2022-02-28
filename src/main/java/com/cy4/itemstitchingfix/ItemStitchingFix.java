package com.cy4.itemstitchingfix;

import org.lwjgl.glfw.GLFW;

import com.cy4.itemstitchingfix.config.ItemStitchingFixConfig;
import com.cy4.itemstitchingfix.gui.MethodSelectScreen;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.client.event.InputEvent.KeyInputEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.IExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.network.NetworkConstants;

@Mod("itemstitchingfix")
public class ItemStitchingFix {

	public static final String MOD_ID = "itemstitchingfix";
	public static KeyMapping openGui = new KeyMapping("key.itemstitchingfix.open_gui", GLFW.GLFW_KEY_I,
			"key.itemstitchingfix.categories.itemstitchingfix");

	public ItemStitchingFix() {
		ModLoadingContext.get().registerExtensionPoint(IExtensionPoint.DisplayTest.class,
				() -> new IExtensionPoint.DisplayTest(() -> NetworkConstants.IGNORESERVERONLY,
						(remote, isServer) -> true));

		ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ItemStitchingFixConfig.CLIENT_SPEC,
				"itemstitchingfix-client.toml");

		MinecraftForge.EVENT_BUS.register(this);
	}

	@Mod.EventBusSubscriber(modid = MOD_ID, bus = Bus.MOD, value = Dist.CLIENT)
	public class ClientModEvents {

		@SubscribeEvent
		public static void clientSetup(FMLClientSetupEvent evt) {
			ClientRegistry.registerKeyBinding(openGui);
		}
	}

	@Mod.EventBusSubscriber(modid = MOD_ID, bus = Bus.FORGE, value = Dist.CLIENT)
	public class ClientForgeEvents {

		@SubscribeEvent
		public static void onKey(KeyInputEvent evt) {
			if (openGui.isDown()) {
				Minecraft.getInstance().setScreen(new MethodSelectScreen());
			}
		}
	}
}