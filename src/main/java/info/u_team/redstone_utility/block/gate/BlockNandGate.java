package info.u_team.redstone_utility.block.gate;

import info.u_team.redstone_utility.RedstoneUtilityConstants;
import net.minecraft.util.ResourceLocation;

public class BlockNandGate extends BlockGates {
	
	public BlockNandGate() {
		super();
		setUnlocalizedName("nandgate");
		setRegistryName(new ResourceLocation(RedstoneUtilityConstants.MODID, "nandgate"));
	}
	
}
