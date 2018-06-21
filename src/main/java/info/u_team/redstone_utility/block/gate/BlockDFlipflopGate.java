package info.u_team.redstone_utility.block.gate;

import info.u_team.redstone_utility.RedstoneUtilityConstants;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.*;

public class BlockDFlipflopGate extends BlockGate {

	public BlockDFlipflopGate() {
		super();
		setRegistryName(new ResourceLocation(RedstoneUtilityConstants.MODID, "dflipflopgate"));
		setUnlocalizedName("dflipflopgate");
		setDefaultState(getDefaultState().withProperty(ACTIVE, false));
	}

	@Override
	public void checkInputs(World world, IBlockState state, BlockPos pos) {
		boolean data = isPowered(world, pos, state, getOppositeSide(state));
		boolean control = isPowered(world, pos, state, getLeftSide(state))
				|| isPowered(world, pos, state, getRightSide(state));

		if (control) {
			updateActiveState(world, state, pos, data);
		}
	}

	@Override
	protected boolean isValidSide(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
		return getRightSide(state) == side || getLeftSide(state) == side || getSide(state) == side;
	}
}
