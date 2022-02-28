package com.cy4.itemstitchingfix.mixin;

import java.util.List;
import java.util.Map;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import com.cy4.itemstitchingfix.config.ItemStitchingFixConfig;
import com.cy4.itemstitchingfix.model.ItemModelUtil;

import net.minecraft.client.renderer.block.model.BlockElement;
import net.minecraft.client.renderer.block.model.BlockElementFace;
import net.minecraft.client.renderer.block.model.ItemModelGenerator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Direction;

@Mixin(ItemModelGenerator.class)
public class ItemModelGeneratorMixin {
	@Inject(at = @At(value = "HEAD"), method = "Lnet/minecraft/client/renderer/block/model/ItemModelGenerator;processFrames(ILjava/lang/String;Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;)Ljava/util/List;", cancellable = true)
	private void onHeadProcessFrame(int layer, String key, TextureAtlasSprite sprite,
			CallbackInfoReturnable<List<BlockElement>> cir) {
		if (ItemStitchingFixConfig.isOutline()) {
			cir.setReturnValue(ItemModelUtil.doOutline(layer, key, sprite));
		} else if (ItemStitchingFixConfig.isPixel()) {
			cir.setReturnValue(ItemModelUtil.doPixel(layer, key, sprite));
		}
	}

	@Inject(at = @At(value = "TAIL"), method = "Lnet/minecraft/client/renderer/block/model/ItemModelGenerator;processFrames(ILjava/lang/String;Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;)Ljava/util/List;", locals = LocalCapture.CAPTURE_FAILHARD)
	private void onTailAddLayerElements(int layer, String key, TextureAtlasSprite sprite,
			CallbackInfoReturnable<List<BlockElement>> cir, Map<Direction, BlockElementFace> map,
			List<BlockElement> list) {

		if (ItemStitchingFixConfig.isUnlerp()) {
			ItemModelUtil.doUnlerp(list, sprite.uvShrinkRatio());
		}
	}
}
