package info.u_team.redstone_utility.block.gate;

import info.u_team.redstone_utility.RedstoneUtilityConstants;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.*;

public class BlockOrGate extends BlockGate {
	
	//public static final PropertyBool ACTIVE = PropertyBool.create("active");
	
	public BlockOrGate() {
		super();
		setUnlocalizedName("orgate");
		setRegistryName(new ResourceLocation(RedstoneUtilityConstants.MODID, "orgate"));
		//setDefaultState(getDefaultState().withProperty(ACTIVE, false));
	}
	
	@Override
	protected void checkInputs(World world, IBlockState state, BlockPos pos) {
		// TODO Auto-generated method stub
		
	}
	
//	
//	@Override
//	public int getMetaFromState(IBlockState state) {
//		return state.getValue(ACTIVE) ? 1 : 0;
//	}
//	
//	@Override
//	public IBlockState getStateFromMeta(int meta) {
//		return getDefaultState().withProperty(ACTIVE, meta == 1 ? true : false);
//	}
//	
//	@Override
//	public BlockStateContainer createBlockState() {
//		return new BlockStateContainer(this, new IProperty[] { ACTIVE });
//	}
//	
//	@Override
//	public int tickRate(World worldIn) {
//		return 2;
//	}
	
	@Override
	public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
		BlockPos west = pos.west();
		neighborChanged(state, world, pos, world.getBlockState(west).getBlock(), west);
		
		BlockPos east = pos.east();
		neighborChanged(state, world, pos, world.getBlockState(east).getBlock(), east);
	}
	
	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos neighbor) {
		checkInputs(world, state, pos, neighbor);
	}
	
	public void checkInputs(World world, IBlockState state, BlockPos pos, BlockPos neighbor) {
		EnumFacing facing = checkFacing(pos, neighbor);
		if (facing == EnumFacing.EAST || facing == EnumFacing.WEST) {
			
			boolean power = isPowered(world, pos, state, facing);
			if (power) {
				world.setBlockState(pos, state.withProperty(ACTIVE, true));
			} else {
				if (!isPowered(world, pos, state, facing.getOpposite())) {
					world.setBlockState(pos, state.withProperty(ACTIVE, false));
				}
			}
		}
		// world.scheduleUpdate(pos.north(), this, this.tickRate(world));
		// world.notifyNeighborsOfStateChange(pos, this, false);
		// world.notifyNeighborsOfStateChange(pos.offset(facing.getOpposite()), this,
		// false);
		// notifyNeighbors(world, pos, state);
	}
	
	// protected void notifyNeighbors(World worldIn, BlockPos pos, IBlockState
	// state) {
	// EnumFacing enumfacing = EnumFacing.NORTH;
	// BlockPos blockpos = pos.offset(enumfacing.getOpposite());
	// if (net.minecraftforge.event.ForgeEventFactory.onNeighborNotify(worldIn, pos,
	// worldIn.getBlockState(pos), java.util.EnumSet.of(enumfacing.getOpposite()),
	// false).isCanceled())
	// return;
	// worldIn.neighborChanged(blockpos, this, pos);
	// worldIn.notifyNeighborsOfStateExcept(blockpos, this, enumfacing);
	// }
	
	
	@Override
	public boolean canProvidePower(IBlockState state) {
		return true;
	}
	
	@Override
	public int getWeakPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		if (side == EnumFacing.SOUTH && blockState.getValue(ACTIVE)) {
			return 15;
		}
		return 0;
	}
	
	@Override
	public int getStrongPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		return getWeakPower(blockState, blockAccess, pos, side);
	}
	
	private EnumFacing checkFacing(BlockPos pos, BlockPos neighbor) {
		if (pos.north().equals(neighbor)) {
			return EnumFacing.NORTH;
		}
		if (pos.south().equals(neighbor)) {
			return EnumFacing.SOUTH;
		}
		if (pos.west().equals(neighbor)) {
			return EnumFacing.WEST;
		}
		if (pos.east().equals(neighbor)) {
			return EnumFacing.EAST;
		}
		return EnumFacing.NORTH;
	}
	
}
