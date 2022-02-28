package com.cy4.itemstitchingfix.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ItemStitchingFixConfig {
	public static final ForgeConfigSpec CLIENT_SPEC;

	static {
		ForgeConfigSpec.Builder configBuilder = new ForgeConfigSpec.Builder();
		setupConfig(configBuilder);
		CLIENT_SPEC = configBuilder.build();
	}

	public enum Methods {
		VANILLA, UNLERP, OUTLINE, PIXEL;

		private static Methods[] vals = values();
		public Methods next() {
			return vals[(this.ordinal() + 1) % vals.length];
		}
	}

	public static ForgeConfigSpec.EnumValue<Methods> method;

	private static void setupConfig(ForgeConfigSpec.Builder builder) {
		method = builder.comment("Set the method used by item stitching fix.").defineEnum("method", Methods.OUTLINE);
	}

	public static boolean isUnlerp() {
		return method.get().equals(Methods.UNLERP);
	}

	public static boolean isOutline() {
		return method.get().equals(Methods.OUTLINE);
	}

	public static boolean isPixel() {
		return method.get().equals(Methods.PIXEL);
	}
}
