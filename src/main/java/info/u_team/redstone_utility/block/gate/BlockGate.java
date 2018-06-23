package info.u_team.redstone_utility.block.gate;

import static info.u_team.redstone_utility.init.Tabs.TAB;

import java.util.Random;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.*;
import net.minecraft.block.state.*;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import net.minecraftforge.fml.relauncher.*;

public abstract class BlockGate extends Block {
	
	public static final PropertyBool ACTIVE = PropertyBool.create("active");
	public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
	
	protected static final AxisAlignedBB AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.0625D, 1.0D);
	
	public BlockGate() {
		super(Material.CIRCUITS);
		setCreativeTab(TAB);
		setDefaultState(getDefaultState().withProperty(ACTIVE, false));
	}
	
	// Meta things
	
	@Override
	public int getMetaFromState(IBlockState state) {
		int i = 0;
		i = i | state.getValue(FACING).getHorizontalIndex();
		
		if (!state.getValue(ACTIVE)) {
			i |= 4;
		}
		return i;
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta % 4)).withProperty(ACTIVE, (meta & 4) == 0);
	}
	
	@Override
	public BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { FACING, ACTIVE });
	}
	
	@Override
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		return getDefaultState().withProperty(FACING, placer.getHorizontalFacing());
	}
	
	// Do update tick whenever something changes (2 Game Ticks = 1 Redstone Tick)
	
	@Override
	public int tickRate(World world) {
		return 2;
	}
	
	@Override
	public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
		// world.updateBlockTick(pos, this, tickRate(world), 0); // Instant check or
		// wait 2 ticks???
		updateTick(world, pos, state, world.rand);
	}
	
	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos neighbor) {
		if (isValidSide(state, world, pos, checkFacing(pos, neighbor))) {
			world.updateBlockTick(pos, this, tickRate(world), 0);
		}
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
	
	// Redstone logic
	
	@Override
	public boolean canProvidePower(IBlockState state) {
		return true;
	}
	
	@Override
	public boolean canConnectRedstone(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
		if (side.getHorizontalIndex() == -1) {
			return false;
		}
		side = side.getOpposite(); // Cause this is the perspective of the block to connect we need the opposite
									// direction for out block side
		if (side == state.getValue(FACING)) {
			return true;
		}
		return isValidSide(state, world, pos, side);
	}
	
	@Override
	public int getWeakPower(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
		if (side.getOpposite() == state.getValue(FACING) && state.getValue(ACTIVE)) {
			return 15;
		}
		return 0;
	}
	
	@Override
	public int getStrongPower(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
		return getWeakPower(state, world, pos, side);
	}
	
	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random random) {
		checkInputs(world, state, pos);
	}
	
	protected abstract void checkInputs(World world, IBlockState state, BlockPos pos);
	
	protected abstract boolean isValidSide(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side);
	
	protected boolean isPowered(World world, BlockPos pos, IBlockState state, EnumFacing facing) {
		BlockPos blockpos = pos.offset(facing);
		int i = world.getRedstonePower(blockpos, facing); // Copied from BlockRedstoneDiode (But this is not a god checking method as it
															// checks many many states.
		if (i >= 15) {
			return true;
		} else {
			IBlockState iblockstate = world.getBlockState(blockpos);
			return Math.max(i, iblockstate.getBlock() == Blocks.REDSTONE_WIRE ? iblockstate.getValue(BlockRedstoneWire.POWER).intValue() : 0) > 0;
		}
	}
	
	protected void updateActiveState(World world, IBlockState state, BlockPos pos, boolean value) {
		world.setBlockState(pos, state.withProperty(ACTIVE, value));
	}
	
	// Facing
	
	public EnumFacing getSide(IBlockState state) {
		return state.getValue(FACING);
	}
	
	public EnumFacing getOppositeSide(IBlockState state) {
		return state.getValue(FACING).getOpposite();
	}
	
	public EnumFacing getRightSide(IBlockState state) {
		return state.getValue(FACING).rotateY();
	}
	
	public EnumFacing getLeftSide(IBlockState state) {
		return state.getValue(FACING).rotateYCCW();
	}
	
	// Just render things and bounding box
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return AABB;
	}
	
	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess world, IBlockState state, BlockPos pos, EnumFacing face) {
		return face == EnumFacing.DOWN ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public boolean shouldSideBeRendered(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
		return side.getAxis() != EnumFacing.Axis.Y;
	}
	
}
