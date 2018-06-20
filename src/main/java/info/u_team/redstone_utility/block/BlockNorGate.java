package info.u_team.redstone_utility.block;

import info.u_team.redstone_utility.RedstoneUtilityConstants;
import net.minecraft.util.ResourceLocation;

public class BlockNorGate extends Gates {

	public BlockNorGate() {
		super();
		setUnlocalizedName("norgate");
		setRegistryName(new ResourceLocation(RedstoneUtilityConstants.MODID, "norgate"));
	}

}
