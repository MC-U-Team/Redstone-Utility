package info.u_team.redstone_utility.block;

import info.u_team.redstone_utility.RedstoneUtilityConstants;
import net.minecraft.util.ResourceLocation;

public class BlockOrGate extends Gates {

	public BlockOrGate() {
		super();
		setUnlocalizedName("orgate");
		setRegistryName(new ResourceLocation(RedstoneUtilityConstants.MODID, "orgate"));
	}

}
