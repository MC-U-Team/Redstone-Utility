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
	
	protected static final PropertyBool ACTIVE = PropertyBool.create("active");
	protected static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
	
	protected static final AxisAlignedBB AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.0625D, 1.0D);
	
	public BlockGate() {
		super(Material.CIRCUITS);
		setCreativeTab(TAB);
		setDefaultState(getDefaultState().withProperty(ACTIVE, false));
	}
	
	// Meta things
	
	@Override
	public int getMetaFromState(IBlockState blockstate) {
		return blockstate.getValue(FACING).getHorizontalIndex() + (blockstate.getValue(ACTIVE) ? 0 : 4);
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta % 4)).withProperty(ACTIVE, meta > 3);
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
	public void onBlockAdded(World world, BlockPos pos, IBlockState blockstate) {
		world.updateBlockTick(pos, this, tickRate(world), 0);
	}
	
	@Override
	public void neighborChanged(IBlockState blockstate, World world, BlockPos pos, Block block, BlockPos neighbor) {
		world.updateBlockTick(pos, this, tickRate(world), 0);
	}
	
	// Redstone logic
	
	@Override
	public boolean canProvidePower(IBlockState blockstate) {
		return true;
	}
	
	@Override
	public int getWeakPower(IBlockState blockstate, IBlockAccess world, BlockPos pos, EnumFacing side) {
		if (side == blockstate.getValue(FACING) && blockstate.getValue(ACTIVE)) {
			return 15;
		}
		return 0;
	}
	
	@Override
	public int getStrongPower(IBlockState blockstate, IBlockAccess world, BlockPos pos, EnumFacing side) {
		return getWeakPower(blockstate, world, pos, side);
	}
	
	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random random) {
		checkInputs(world, state, pos);
	}
	
	protected abstract void checkInputs(World world, IBlockState state, BlockPos pos);
	
	protected boolean isPowered(World world, BlockPos pos, IBlockState blockstate, EnumFacing facing) {
		BlockPos blockpos = pos.offset(facing);
		int i = world.getRedstonePower(blockpos, facing);
		
		if (i >= 15) {
			return true;
		} else {
			IBlockState iblockstate = world.getBlockState(blockpos);
			return Math.max(i, iblockstate.getBlock() == Blocks.REDSTONE_WIRE ? iblockstate.getValue(BlockRedstoneWire.POWER).intValue() : 0) > 0;
		}
	}
	
	// Just render things and bounding box
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState blockstate, IBlockAccess source, BlockPos pos) {
		return AABB;
	}
	
	@Override
	public boolean isFullCube(IBlockState blockstate) {
		return false;
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState blockstate) {
		return false;
	}
	
	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess world, IBlockState blockstate, BlockPos pos, EnumFacing face) {
		return face == EnumFacing.DOWN ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public boolean shouldSideBeRendered(IBlockState blockstate, IBlockAccess world, BlockPos pos, EnumFacing side) {
		return side.getAxis() != EnumFacing.Axis.Y;
	}
	
}
