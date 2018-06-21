package info.u_team.redstone_utility.block.gate;

import info.u_team.redstone_utility.RedstoneUtilityConstants;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockXorGate extends BlockGate {
	
	public BlockXorGate() {
		super();
		setUnlocalizedName("xorgate");
		setRegistryName(new ResourceLocation(RedstoneUtilityConstants.MODID, "xorgate"));
	}
	@Override
	protected void checkInputs(World world, IBlockState state, BlockPos pos) {
		// TODO Auto-generated method stub
		
	}
	
}
