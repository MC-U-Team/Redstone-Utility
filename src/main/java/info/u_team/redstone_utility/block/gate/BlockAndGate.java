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
	public void checkInputs(World world, IBlockState state, BlockPos pos) {
		boolean powerleft = isPowered(world, pos, state, getLeftSide(state));
		boolean powerright = isPowered(world, pos, state, getRightSide(state));
		updateActiveState(world, state, pos, (powerleft && powerright));
	}
	
	@Override
	protected boolean isValidSide(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
		return getRightSide(state) == side || getLeftSide(state) == side;
	}
}
