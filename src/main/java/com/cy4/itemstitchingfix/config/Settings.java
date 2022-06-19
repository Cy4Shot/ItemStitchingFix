package com.cy4.itemstitchingfix.config;

public final class Settings {
	public static ModelGenerationType type;

	public enum ModelGenerationType {
		VANILLA, UNLERP, OUTLINE, PIXEL;
		
		private static ModelGenerationType[] vals = values();
		public ModelGenerationType next() {
			return vals[(this.ordinal() + 1) % vals.length];
		}
	}
}
