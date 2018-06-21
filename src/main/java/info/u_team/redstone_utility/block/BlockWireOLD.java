package info.u_team.redstone_utility.block;

import info.u_team.redstone_utility.RedstoneUtilityConstants;
import info.u_team.redstone_utility.tileentity.TileEntityWire;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.*;
import net.minecraft.block.state.*;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockWireOLD extends Block {
	
	public static final PropertyBool POWERED = PropertyBool.create("powered");
	
	public static final PropertyBool NORTH = PropertyBool.create("north");
	public static final PropertyBool SOUTH = PropertyBool.create("south");
	public static final PropertyBool WEST = PropertyBool.create("west");
	public static final PropertyBool EAST = PropertyBool.create("east");
	
	public BlockWireOLD() {
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
		return new BlockStateContainer(this, new IProperty[] { POWERED, NORTH, SOUTH, WEST, EAST });
	}
	
	@Override
	public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
		if (world.isRemote) {
			return;
		}
		for (EnumFacing facing : EnumFacing.HORIZONTALS) {
			BlockPos newpos = pos.offset(facing);
			if (world.getBlockState(pos).getBlock() == this) {
				// FACIGN
			}
		}
	}
	
	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		if (worldIn.isRemote) {
			return;
		}
		System.out.println("TEST");
	}
	
}
