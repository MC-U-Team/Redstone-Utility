package info.u_team.redstone_utility.block;

import info.u_team.redstone_utility.RedstoneUtilityConstants;
import info.u_team.redstone_utility.init.RedstoneUtilityCreativeTabs;
import info.u_team.u_team_core.block.UBlockTileEntity;
import info.u_team.u_team_core.tileentity.*;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.*;
import net.minecraft.block.state.*;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.*;

public class BlockMultiBitWireConnection extends UBlockTileEntity {
	
	public static final PropertyDirection FACING = BlockDirectional.FACING;
	
	public static final PropertyBool POWERED = PropertyBool.create("powered");
	
	public BlockMultiBitWireConnection(String name, Class<? extends UTileEntity> tileentity) {
		super(name, Material.CLOTH, RedstoneUtilityCreativeTabs.tab, new UTileEntityProvider(new ResourceLocation(RedstoneUtilityConstants.MODID, name), tileentity));
		setDefaultState(getDefaultState().withProperty(POWERED, false));
	}
	
	// Redstone things
	@Override
	public boolean canConnectRedstone(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
		return side == state.getValue(FACING).getOpposite();
	}
	
	// Facing things
	
	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
		return getDefaultState().withProperty(FACING, EnumFacing.getDirectionFromEntityLiving(pos, placer));
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(FACING, EnumFacing.byIndex(meta)).withProperty(POWERED, meta >= 6);
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(FACING).getIndex() + (state.getValue(POWERED) ? 6 : 0);
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, FACING, POWERED);
	}
}
