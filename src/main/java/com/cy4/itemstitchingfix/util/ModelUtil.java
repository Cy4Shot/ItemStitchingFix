package com.cy4.itemstitchingfix.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.client.renderer.model.BlockFaceUV;
import net.minecraft.client.renderer.model.BlockPart;
import net.minecraft.client.renderer.model.BlockPartFace;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.Vector3f;

public class ModelUtil {

	public static void unlerpElements(List<BlockPart> elements, float delta) {
		for (BlockPart element : elements) {
			for (BlockPartFace face : element.mapFaces.values()) {
				MathUtil.unlerpUVs(face.blockFaceUV.uvs, delta);
			}
		}
	}

	public static List<BlockPart> createOutlineLayerElements(int layer, String key, TextureAtlasSprite sprite) {
		List<BlockPart> elements = new ArrayList<>();

		int width = sprite.getWidth();
		int height = sprite.getHeight();
		float animationFrameDelta = sprite.getUvShrinkRatio();
		float xFactor = width / 16.0F;
		float yFactor = height / 16.0F;

		Map<Direction, BlockPartFace> map = new HashMap<>();
		map.put(Direction.SOUTH, new BlockPartFace(null, layer, key,
				createUnlerpedTexture(new float[] { 0.0F, 0.0F, 16.0F, 16.0F }, 0, animationFrameDelta)));
		map.put(Direction.NORTH, new BlockPartFace(null, layer, key,
				createUnlerpedTexture(new float[] { 16.0F, 0.0F, 0.0F, 16.0F }, 0, animationFrameDelta)));
		elements.add(new BlockPart(new Vector3f(0.0F, 0.0F, 7.5F), new Vector3f(16.0F, 16.0F, 8.5F), map, null, true));

		int first1 = -1;
		int first2 = -1;
		int last1 = -1;
		int last2 = -1;
		for (int frame = 0; frame < sprite.getFrameCount(); ++frame) {
			for (int y = 0; y < height; ++y) {
				for (int x = 0; x < width; ++x) {
					if (!isPixelTransparent(sprite, frame, x, y)) {
						if (isPixelTransparent(sprite, frame, x, y + 1)) { // DOWN
							if (first1 == -1) {
								first1 = x;
							}
							last1 = x;
						}
						if (isPixelTransparent(sprite, frame, x, y - 1)) { // UP
							if (first2 == -1) {
								first2 = x;
							}
							last2 = x;
						}
					} else {
						if (first1 != -1) {
							elements.add(createHorizontalOutlineElement(Direction.DOWN, layer, key, first1, last1, y,
									height, animationFrameDelta, xFactor, yFactor));
							first1 = -1;
						}
						if (first2 != -1) {
							elements.add(createHorizontalOutlineElement(Direction.UP, layer, key, first2, last2, y,
									height, animationFrameDelta, xFactor, yFactor));
							first2 = -1;
						}
					}
				}

				if (first1 != -1) {
					elements.add(createHorizontalOutlineElement(Direction.DOWN, layer, key, first1, last1, y, height,
							animationFrameDelta, xFactor, yFactor));
					first1 = -1;
				}
				if (first2 != -1) {
					elements.add(createHorizontalOutlineElement(Direction.UP, layer, key, first2, last2, y, height,
							animationFrameDelta, xFactor, yFactor));
					first2 = -1;
				}
			}

			for (int x = 0; x < width; ++x) {
				for (int y = 0; y < height; ++y) {
					if (!isPixelTransparent(sprite, frame, x, y)) {
						if (isPixelTransparent(sprite, frame, x + 1, y)) { // EAST
							if (first1 == -1) {
								first1 = y;
							}
							last1 = y;
						}
						if (isPixelTransparent(sprite, frame, x - 1, y)) { // WEST
							if (first2 == -1) {
								first2 = y;
							}
							last2 = y;
						}
					} else {
						if (first1 != -1) {
							elements.add(createVerticalOutlineElement(Direction.EAST, layer, key, first1, last1, x,
									height, animationFrameDelta, xFactor, yFactor));
							first1 = -1;
						}
						if (first2 != -1) {
							elements.add(createVerticalOutlineElement(Direction.WEST, layer, key, first2, last2, x,
									height, animationFrameDelta, xFactor, yFactor));
							first2 = -1;
						}
					}
				}

				if (first1 != -1) {
					elements.add(createVerticalOutlineElement(Direction.EAST, layer, key, first1, last1, x, height,
							animationFrameDelta, xFactor, yFactor));
					first1 = -1;
				}
				if (first2 != -1) {
					elements.add(createVerticalOutlineElement(Direction.WEST, layer, key, first2, last2, x, height,
							animationFrameDelta, xFactor, yFactor));
					first2 = -1;
				}
			}
		}

		return elements;
	}

	public static BlockPart createHorizontalOutlineElement(Direction direction, int layer, String key, int start,
			int end, int y, int height, float animationFrameDelta, float xFactor, float yFactor) {
		Map<Direction, BlockPartFace> faces = new HashMap<>();
		faces.put(direction,
				new BlockPartFace(null, layer, key,
						createUnlerpedTexture(
								new float[] { start / xFactor, y / yFactor, (end + 1) / xFactor, (y + 1) / yFactor }, 0,
								animationFrameDelta)));
		return new BlockPart(new Vector3f(start / xFactor, (height - (y + 1)) / yFactor, 7.5F),
				new Vector3f((end + 1) / xFactor, (height - y) / yFactor, 8.5F), faces, null, true);
	}

	public static BlockPart createVerticalOutlineElement(Direction direction, int layer, String key, int start, int end,
			int x, int height, float animationFrameDelta, float xFactor, float yFactor) {
		Map<Direction, BlockPartFace> faces = new HashMap<>();
		faces.put(direction,
				new BlockPartFace(null, layer, key,
						createUnlerpedTexture(
								new float[] { (x + 1) / xFactor, start / yFactor, x / xFactor, (end + 1) / yFactor }, 0,
								animationFrameDelta)));
		return new BlockPart(new Vector3f(x / xFactor, (height - (end + 1)) / yFactor, 7.5F),
				new Vector3f((x + 1) / xFactor, (height - start) / yFactor, 8.5F), faces, null, true);
	}

	public static BlockFaceUV createUnlerpedTexture(float[] uvs, int rotation, float delta) {
		return new BlockFaceUV(MathUtil.unlerpUVs(uvs, delta), rotation);
	}

	public static List<BlockPart> createPixelLayerElements(int layer, String key, TextureAtlasSprite sprite) {
		List<BlockPart> elements = new ArrayList<>();

		int width = sprite.getWidth();
		int height = sprite.getHeight();
		float xFactor = width / 16.0F;
		float yFactor = height / 16.0F;

		for (int frame = 0; frame < sprite.getFrameCount(); ++frame) {
			for (int y = 0; y < height; ++y) {
				for (int x = 0; x < width; ++x) {
					if (!isPixelTransparent(sprite, frame, x, y)) {
						Map<Direction, BlockPartFace> faces = new HashMap<>();
						BlockPartFace face = new BlockPartFace(null, layer, key, new BlockFaceUV(
								new float[] { x / xFactor, y / yFactor, (x + 1) / xFactor, (y + 1) / yFactor }, 0));
						BlockPartFace flippedFace = new BlockPartFace(null, layer, key, new BlockFaceUV(
								new float[] { (x + 1) / xFactor, y / yFactor, x / xFactor, (y + 1) / yFactor }, 0));

						faces.put(Direction.SOUTH, face);
						faces.put(Direction.NORTH, flippedFace);
						if (isPixelTransparent(sprite, frame, x + 1, y)) {
							faces.put(Direction.EAST, flippedFace);
						}
						if (isPixelTransparent(sprite, frame, x - 1, y)) {
							faces.put(Direction.WEST, flippedFace);
						}
						if (isPixelTransparent(sprite, frame, x, y + 1)) {
							faces.put(Direction.DOWN, face);
						}
						if (isPixelTransparent(sprite, frame, x, y - 1)) {
							faces.put(Direction.UP, face);
						}

						elements.add(new BlockPart(new Vector3f(x / xFactor, (height - (y + 1)) / yFactor, 7.5F),
								new Vector3f((x + 1) / xFactor, (height - y) / yFactor, 8.5F), faces, null, true));
					}
				}
			}
		}

		return elements;
	}

	public static boolean isPixelTransparent(TextureAtlasSprite sprite, int frame, int x, int y) {
		return (x < 0 || y < 0 || x >= sprite.getWidth() || y >= sprite.getHeight()) ? true
				: sprite.isPixelTransparent(frame, x, y);
	}
}
