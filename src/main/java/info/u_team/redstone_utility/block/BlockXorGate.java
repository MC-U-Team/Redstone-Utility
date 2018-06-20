package info.u_team.redstone_utility.block;

import info.u_team.redstone_utility.RedstoneUtilityConstants;
import net.minecraft.util.ResourceLocation;

public class BlockXorGate extends Gates {

	public BlockXorGate() {
		super();
		setUnlocalizedName("xorgate");
		setRegistryName(new ResourceLocation(RedstoneUtilityConstants.MODID, "xorgate"));
	}

}
