package info.u_team.redstone_utility.block.gate;

import java.util.Random;

import info.u_team.redstone_utility.RedstoneUtilityConstants;
import net.minecraft.block.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.state.*;
import net.minecraft.init.Blocks;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.*;

public class BlockNotGate extends BlockGates {
	
	public static final PropertyBool ACTIVE = PropertyBool.create("active");
	
	public BlockNotGate() {
		super();
		setRegistryName(new ResourceLocation(RedstoneUtilityConstants.MODID, "notgate"));
		setUnlocalizedName("notgate");
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(ACTIVE) ? 1 : 0;
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(ACTIVE, meta == 1 ? true : false);
	}
	
	@Override
	public BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { ACTIVE });
	}
	
	@Override
	public int tickRate(World worldIn) {
		return 2;
	}
	
	@Override
	public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
		BlockPos south = pos.south();
		neighborChanged(state, world, pos, world.getBlockState(south).getBlock(), south);
	}
	
	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos neighbor) {
		world.updateBlockTick(pos, this, tickRate(world), 0);
	}
	
	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
		checkInputs(world, state, pos);
	}
	
	public void checkInputs(World world, IBlockState state, BlockPos pos) {
		EnumFacing facing = EnumFacing.SOUTH;
		if (facing == EnumFacing.SOUTH) {
			boolean power = isPowered(world, pos, state, facing);
			if (power) {
				world.setBlockState(pos, state.withProperty(ACTIVE, true));
			} else {
				world.setBlockState(pos, state.withProperty(ACTIVE, false));
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
	
	private boolean isPowered(World world, BlockPos pos, IBlockState state, EnumFacing facing) {
		BlockPos blockpos = pos.offset(facing);
		int i = world.getRedstonePower(blockpos, facing);
		
		if (i >= 15) {
			return true;
		} else {
			IBlockState iblockstate = world.getBlockState(blockpos);
			return Math.max(i, iblockstate.getBlock() == Blocks.REDSTONE_WIRE ? iblockstate.getValue(BlockRedstoneWire.POWER).intValue() : 0) > 0;
		}
	}
	
	@Override
	public boolean canProvidePower(IBlockState state) {
		return true;
	}
	
	@Override
	public int getWeakPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		if (side == EnumFacing.SOUTH && !blockState.getValue(ACTIVE)) {
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
