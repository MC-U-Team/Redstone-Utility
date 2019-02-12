package info.u_team.redstone_utility.model;

import java.util.*;
import java.util.function.Function;

import com.google.common.collect.*;

import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.*;
import net.minecraftforge.client.model.*;
import net.minecraftforge.common.model.*;

public class ModelMultiBitWire implements IModel {
	
	private ResourceLocation particle;
	private ResourceLocation texture;
	private boolean isItem;
	private List<EnumFacing> facings;
	
	public ModelMultiBitWire(boolean isItem, List<EnumFacing> facings) {
		this.particle = new ResourceLocation("minecraft", "blocks/iron_block");
		this.texture = new ResourceLocation("minecraft", "blocks/iron_block");
		this.isItem = isItem;
		this.facings = facings;
	}
	
	@Override
	public Collection<ResourceLocation> getTextures() {
		return ImmutableList.of(particle, texture);
	}
	
	@Override
	public IModelState getDefaultState() {
		return ModelRotation.X0_Y0;
	}
	
	@Override
	public IBakedModel bake(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
		ImmutableMap<TransformType, TRSRTransformation> map = PerspectiveMapWrapper.getTransforms(state);
		return new BakedModelMultiBitWire(facings, isItem, state.apply(Optional.empty()), map, format, bakedTextureGetter.apply(particle), bakedTextureGetter.apply(texture), 0xFFFFFF);
	}
	
}
