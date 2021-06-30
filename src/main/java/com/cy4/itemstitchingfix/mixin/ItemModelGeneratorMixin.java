package com.cy4.itemstitchingfix.mixin;

import java.util.List;
import java.util.Map;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import com.cy4.itemstitchingfix.config.Settings;
import com.cy4.itemstitchingfix.config.Settings.ModelGenerationType;
import com.cy4.itemstitchingfix.util.ModelUtil;

import net.minecraft.client.renderer.model.BlockPart;
import net.minecraft.client.renderer.model.BlockPartFace;
import net.minecraft.client.renderer.model.ItemModelGenerator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.Direction;

@Mixin(ItemModelGenerator.class)
public class ItemModelGeneratorMixin {

	@Inject(at = @At(value = "HEAD"), method = "Lnet/minecraft/client/renderer/model/ItemModelGenerator;getBlockParts(ILjava/lang/String;Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;)Ljava/util/List;", cancellable = true)
	private void onHeadAddLayerElements(int layer, String key, TextureAtlasSprite sprite,
			CallbackInfoReturnable<List<BlockPart>> cir) {

		if (Settings.type == ModelGenerationType.OUTLINE) {
			cir.setReturnValue(ModelUtil.createOutlineLayerElements(layer, key, sprite));
		} else if (Settings.type == ModelGenerationType.PIXEL) {
			cir.setReturnValue(ModelUtil.createPixelLayerElements(layer, key, sprite));
		}
	}

	@Inject(at = @At(value = "TAIL"), method = "Lnet/minecraft/client/renderer/model/ItemModelGenerator;getBlockParts(ILjava/lang/String;Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;)Ljava/util/List;", locals = LocalCapture.CAPTURE_FAILHARD)
	private void onTailAddLayerElements(int layer, String key, TextureAtlasSprite sprite,
			CallbackInfoReturnable<List<BlockPart>> cir, Map<Direction, BlockPartFace> map, List<BlockPart> list) {
		if (Settings.type == ModelGenerationType.UNLERP) {
			ModelUtil.unlerpElements(list, sprite.getUvShrinkRatio());
		}
	}

}
