package info.u_team.redstone_utility.block.gate;

import info.u_team.redstone_utility.RedstoneUtilityConstants;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.*;

public class BlockNotGate extends BlockGate {
	
	public BlockNotGate() {
		super();
		setRegistryName(new ResourceLocation(RedstoneUtilityConstants.MODID, "notgate"));
		setUnlocalizedName("notgate");
		setDefaultState(getDefaultState().withProperty(ACTIVE, true));
	}
	
	@Override
	public void checkInputs(World world, IBlockState blockstate, BlockPos pos) {
		boolean power = isPowered(world, pos, blockstate, blockstate.getValue(FACING).getOpposite());
		if (power) {
			world.setBlockState(pos, blockstate.withProperty(ACTIVE, false));
		} else {
			world.setBlockState(pos, blockstate.withProperty(ACTIVE, true));
		}
	}
	
	@Override
	protected boolean isValidSide(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
		return state.getValue(FACING) == side;
	}
}
