package info.u_team.redstone_utility.block;

import info.u_team.redstone_utility.RedstoneUtilityConstants;
import info.u_team.redstone_utility.tileentity.TileEntityWire;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.*;
import net.minecraft.block.state.*;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.*;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockWire extends Block {
	
	public static final PropertyBool POWERED = PropertyBool.create("powered");
	
	public BlockWire() {
		super(Material.ROCK);
		setRegistryName(new ResourceLocation(RedstoneUtilityConstants.MODID, "wire"));
		setUnlocalizedName("wire");
		GameRegistry.registerTileEntity(TileEntityWire.class, new ResourceLocation(RedstoneUtilityConstants.MODID, "wire"));
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(POWERED) ? 1 : 0;
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(POWERED, meta == 0 ? false : true);
	}
	
	@Override
	public BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { POWERED });
	}
	
	@Override
	public boolean canProvidePower(IBlockState state) {
		return state.getValue(POWERED);
	}
	
	@Override
	public int getWeakPower(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
		return state.getValue(POWERED) ? 15 : 0;
	}
	
	/*
	 * Just some tests. Far from working correctly
	 */
	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos fromPos) {
		System.out.println("pos: " + pos);
		System.out.println("from: " + fromPos);
		if (world.isBlockPowered(pos)) {
			System.out.println("TEST");
			world.setBlockState(pos, state.withProperty(POWERED, true));
		} else {
			world.setBlockState(pos, state.withProperty(POWERED, false));
		}
	}
	
}
