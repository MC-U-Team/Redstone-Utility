package info.u_team.redstone_utility.block.gate;

import info.u_team.redstone_utility.RedstoneUtilityConstants;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.*;

public class BlockTFlipflopGate extends BlockGate {
	
	private boolean output = false;
	
	public BlockTFlipflopGate() {
		super();
		setRegistryName(new ResourceLocation(RedstoneUtilityConstants.MODID, "tflipflopgate"));
		setUnlocalizedName("tflipflopgate");
		setDefaultState(getDefaultState().withProperty(ACTIVE, true));
	}
	
	@Override
	public void checkInputs(World world, IBlockState state, BlockPos pos) {
		if(isPowered(world, pos, state, getOppositeSide(state))) {
			output = !output;
		}
		updateActiveState(world, state, pos, output);
	}
	
	@Override
	protected boolean isValidSide(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
		return getSide(state) == side;
	}
}
