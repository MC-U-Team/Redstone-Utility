package info.u_team.redstone_utility.block;

import info.u_team.redstone_utility.tileentity.TileEntityMultiBitWireOutput;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockMultiBitWireOutput extends BlockMultiBitWireConnection {
	
	public BlockMultiBitWireOutput(String name) {
		super(name, TileEntityMultiBitWireOutput.class);
	}
	
	// Redstone things
	
	@Override
	public boolean canProvidePower(IBlockState state) {
		return true;
	}
	
	@Override
	public int getStrongPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		return getWeakPower(blockState, blockAccess, pos, side);
	}
	
	@Override
	public int getWeakPower(IBlockState state, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		EnumFacing currentFacing = state.getValue(FACING);
		
		if (currentFacing.getOpposite() == side) {
			return state.getValue(POWERED) ? 15 : 0;
		}
		return 0;
	}
	
}
