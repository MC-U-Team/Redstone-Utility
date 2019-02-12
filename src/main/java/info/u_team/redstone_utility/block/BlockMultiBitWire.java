package info.u_team.redstone_utility.block;

import org.apache.commons.lang3.ArrayUtils;

import info.u_team.redstone_utility.RedstoneUtilityConstants;
import info.u_team.redstone_utility.api.*;
import info.u_team.redstone_utility.init.RedstoneUtilityCreativeTabs;
import info.u_team.redstone_utility.model.ModelLoaderMultiBitWire;
import info.u_team.redstone_utility.tileentity.TileEntityMultiBitWire;
import info.u_team.u_team_core.block.UBlockTileEntity;
import info.u_team.u_team_core.tileentity.UTileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.*;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.fml.relauncher.*;

public class BlockMultiBitWire extends UBlockTileEntity {
	
	public static final PropertyBool DOWN = PropertyBool.create("down");
	public static final PropertyBool UP = PropertyBool.create("up");
	public static final PropertyBool NORTH = PropertyBool.create("north");
	public static final PropertyBool SOUTH = PropertyBool.create("south");
	public static final PropertyBool WEST = PropertyBool.create("west");
	public static final PropertyBool EAST = PropertyBool.create("east");
	
	private static PropertyBool[] properties = new PropertyBool[] { DOWN, UP, NORTH, SOUTH, WEST, EAST };
	
	public BlockMultiBitWire(String name) {
		super(name, Material.CLOTH, RedstoneUtilityCreativeTabs.tab, new UTileEntityProvider(new ResourceLocation(RedstoneUtilityConstants.MODID, "multibitwire"), TileEntityMultiBitWire.class));
		setDefaultState(getDefaultState().withProperty(UP, false).withProperty(DOWN, false).withProperty(NORTH, false).withProperty(SOUTH, false).withProperty(EAST, false).withProperty(WEST, false));
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerModel() {
		ModelLoaderRegistry.registerLoader(new ModelLoaderMultiBitWire());
		super.registerModel();
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		return 0;
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState();
	}
	
	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
		int i = 0;
		for (EnumFacing facing : EnumFacing.VALUES) {
			TileEntity tileentity = getTileEntitySave(world, pos.offset(facing));
			boolean connect = tileentity instanceof ITunnel;
			if (connect && tileentity instanceof ITunnelConnection) {
				ITunnelConnection connection = (ITunnelConnection) tileentity;
				connect = ArrayUtils.contains(connection.isConnection(), facing.getOpposite());
			}
			state = state.withProperty(properties[i], connect);
			i++;
		}
		return state;
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, properties);
	}
	
	@Override
	public boolean isBlockNormalCube(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean isTranslucent(IBlockState state) {
		return true;
	}
	
	@Override
	public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		return true;
	}
	
	@Override
	public boolean doesSideBlockRendering(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing face) {
		return false;
	}
	
	@Override
	public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
		return false;
	}
	
	@Override
	public boolean causesSuffocation(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean isFullBlock(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean isNormalCube(IBlockState state, IBlockAccess world, BlockPos pos) {
		return false;
	}
	
	@Override
	public boolean isSideSolid(IBlockState base_state, IBlockAccess world, BlockPos pos, EnumFacing side) {
		return false;
	}
	
}
