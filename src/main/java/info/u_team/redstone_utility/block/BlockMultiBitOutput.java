package info.u_team.redstone_utility.block;

import org.apache.commons.lang3.tuple.Pair;

import info.u_team.redstone_utility.RedstoneUtilityConstants;
import info.u_team.redstone_utility.init.RedstoneUtilityCreativeTabs;
import info.u_team.redstone_utility.tileentity.TileEntityMultiBitOutput;
import info.u_team.u_team_core.block.UBlockTileEntity;
import info.u_team.u_team_core.tileentity.UTileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockMultiBitOutput extends UBlockTileEntity {
	
	public BlockMultiBitOutput(String name) {
		super(name, Material.CLOTH, RedstoneUtilityCreativeTabs.tab, new UTileEntityProvider(new ResourceLocation(RedstoneUtilityConstants.MODID, "multibitoutput"), TileEntityMultiBitOutput.class));
	}
	
	// protected void updateNeighbors(World worldIn, BlockPos pos) {
	// worldIn.notifyNeighborsOfStateChange(pos, this, false);
	// worldIn.notifyNeighborsOfStateChange(pos.down(), this, false);
	// }
	
	public int getWeakPower(IBlockState blockState, IBlockAccess world, BlockPos pos, EnumFacing side) {
		Pair<Boolean, TileEntity> pair = isTileEntityFromProvider(world, pos);
		if (pair.getLeft()) {
			TileEntityMultiBitOutput output = (TileEntityMultiBitOutput) pair.getRight();
			return output.getBit();
		}
		return 0;
	}
	
	public int getStrongPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		return 0;
	}
	
	public boolean canProvidePower(IBlockState state) {
		return true;
	}
	
}
