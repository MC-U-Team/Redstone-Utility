package info.u_team.redstone_utility.block.gate;

import info.u_team.redstone_utility.RedstoneUtilityConstants;
import net.minecraft.util.ResourceLocation;

public class BlockXnorGate extends BlockGate {
	
	public BlockXnorGate() {
		super();
		setUnlocalizedName("xnorgate");
		setRegistryName(new ResourceLocation(RedstoneUtilityConstants.MODID, "xnorgate"));
	}
	
}
