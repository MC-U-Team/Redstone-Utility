package info.u_team.redstone_utility.block.gate;

import info.u_team.redstone_utility.RedstoneUtilityConstants;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.*;

public class BlockAndGate extends BlockGate {
	
	public BlockAndGate() {
		super();
		setUnlocalizedName("andgate");
		setRegistryName(new ResourceLocation(RedstoneUtilityConstants.MODID, "andgate"));
	}

	@Override
	protected void checkInputs(World world, IBlockState state, BlockPos pos) {
		
	}
	
	@Override
	protected boolean isValidSide(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
