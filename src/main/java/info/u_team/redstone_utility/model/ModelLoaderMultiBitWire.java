package info.u_team.redstone_utility.model;

import java.util.*;

import info.u_team.redstone_utility.RedstoneUtilityConstants;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.*;
import net.minecraftforge.client.model.*;

public class ModelLoaderMultiBitWire implements ICustomModelLoader {
	
	@Override
	public void onResourceManagerReload(IResourceManager resourceManager) {
	}
	
	@Override
	public boolean accepts(ResourceLocation resourcelocation) {
		return resourcelocation.getNamespace().equals(RedstoneUtilityConstants.MODID) && resourcelocation.getPath().equals("multibitwire");
	}
	
	@Override
	public IModel loadModel(ResourceLocation resourcelocation) throws Exception {
		if (resourcelocation instanceof ModelResourceLocation) {
			ModelResourceLocation modelresource = (ModelResourceLocation) resourcelocation;
			String variant = modelresource.getVariant();
			
			if (variant.equals("inventory")) {
				return new ModelMultiBitWire(true, null);
			}
			
			String[] values = variant.split(",");
			List<EnumFacing> facings = new ArrayList<>();
			for (String value : values) {
				String[] pair = value.split("=");
				if (Boolean.valueOf(pair[1])) {
					facings.add(EnumFacing.byName(pair[0]));
				}
			}
			return new ModelMultiBitWire(false, facings);
		}
		return null;
	}
	
}
