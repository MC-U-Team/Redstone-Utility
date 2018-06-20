package info.u_team.redstone_utility.block;

import info.u_team.redstone_utility.RedstoneUtilityConstants;
import net.minecraft.util.ResourceLocation;

public class BlockNotGate extends BlockGates {

	public BlockNotGate() {
		super();
		setRegistryName(new ResourceLocation(RedstoneUtilityConstants.MODID, "notgate"));
		setUnlocalizedName("notgate");
	}
}
