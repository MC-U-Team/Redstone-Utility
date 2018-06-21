package info.u_team.redstone_utility.block.gate;

import info.u_team.redstone_utility.RedstoneUtilityConstants;
import net.minecraft.util.ResourceLocation;

public class BlockAndGate extends BlockGate {
	
	public BlockAndGate() {
		super();
		setUnlocalizedName("andgate");
		setRegistryName(new ResourceLocation(RedstoneUtilityConstants.MODID, "andgate"));
	}
}
