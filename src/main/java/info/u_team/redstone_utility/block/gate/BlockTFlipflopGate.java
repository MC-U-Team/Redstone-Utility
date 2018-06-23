package info.u_team.redstone_utility.block.gate;

import info.u_team.redstone_utility.RedstoneUtilityConstants;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.*;

public class BlockTFlipflopGate extends BlockGate {
	
	public BlockTFlipflopGate() {
		super();
		setUnlocalizedName("tflipflopgate");
		setRegistryName(new ResourceLocation(RedstoneUtilityConstants.MODID, "tflipflopgate"));
		setDefaultState(getDefaultState().withProperty(ACTIVE, false));
	}
	
	@Override
	protected void checkInputs(World world, IBlockState state, BlockPos pos) {
		boolean power = isPowered(world, pos, state, getOppositeSide(state));
		boolean statevalue = state.getValue(ACTIVE);
		if (power) {
			statevalue = !statevalue;
		}
		updateActiveState(world, state, pos, statevalue);
	}
	
	@Override
	protected boolean isValidSide(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
		return getOppositeSide(state) == side;
	}
}
