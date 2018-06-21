package info.u_team.redstone_utility.block.gate;

import info.u_team.redstone_utility.RedstoneUtilityConstants;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.*;

public class BlockRSNORLatchGate extends BlockGate {

	public BlockRSNORLatchGate() {
		super();
		setUnlocalizedName("rsnorlatchgate");
		setRegistryName(new ResourceLocation(RedstoneUtilityConstants.MODID, "rsnorlatchgate"));
		setDefaultState(getDefaultState().withProperty(ACTIVE, false));
	}

	@Override
	public void checkInputs(World world, IBlockState state, BlockPos pos) {
		boolean powerleft = isPowered(world, pos, state, getLeftSide(state));
		boolean powerright = isPowered(world, pos, state, getRightSide(state));
		if (powerleft) {
			updateActiveState(world, state, pos, false);
		}
		if (powerright) {
			updateActiveState(world, state, pos, true);
		}
	}

	@Override
	protected boolean isValidSide(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
		return getRightSide(state) == side || getLeftSide(state) == side;
	}
}
