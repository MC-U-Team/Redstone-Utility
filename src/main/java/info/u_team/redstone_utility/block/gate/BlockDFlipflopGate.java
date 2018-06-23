package info.u_team.redstone_utility.block.gate;

import info.u_team.redstone_utility.RedstoneUtilityConstants;
import net.minecraft.block.properties.*;
import net.minecraft.block.state.*;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.*;

public class BlockDFlipflopGate extends BlockGate {
	
	public static final PropertyBool CONTROL = PropertyBool.create("control");
	
	public BlockDFlipflopGate() {
		super();
		setRegistryName(new ResourceLocation(RedstoneUtilityConstants.MODID, "dflipflopgate"));
		setUnlocalizedName("dflipflopgate");
		setDefaultState(getDefaultState().withProperty(ACTIVE, false).withProperty(CONTROL, false));
	}
	
	@Override
	protected void checkInputs(World world, IBlockState state, BlockPos pos) {
		boolean data = isPowered(world, pos, state, getOppositeSide(state));
		boolean control = isPowered(world, pos, state, getLeftSide(state)) || isPowered(world, pos, state, getRightSide(state));
		
		if (control && !state.getValue(CONTROL)) {
			updateState(world, state, pos, data, true);
		} else if (!control) {
			updateControlState(world, state, pos, false);
		}
	}
	
	@Override
	protected boolean isValidSide(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
		return getRightSide(state) == side || getLeftSide(state) == side || getOppositeSide(state) == side;
	}
	
	// Update Methods
	
	protected void updateState(World world, IBlockState state, BlockPos pos, boolean value, boolean control) {
		world.setBlockState(pos, state.withProperty(ACTIVE, value).withProperty(CONTROL, control));
	}
	
	protected void updateControlState(World world, IBlockState state, BlockPos pos, boolean control) {
		world.setBlockState(pos, state.withProperty(CONTROL, control));
	}
	
	// Meta things
	
	@Override
	public int getMetaFromState(IBlockState state) {
		int i = 0;
		i = i | state.getValue(FACING).getHorizontalIndex();
		
		if (!state.getValue(ACTIVE)) {
			i |= 4;
		}
		
		if (state.getValue(CONTROL)) {
			i |= 8;
		}
		
		return i;
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta % 4)).withProperty(ACTIVE, (meta & 4) == 0).withProperty(CONTROL, (meta & 8) > 0);
	}
	
	@Override
	public BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { FACING, ACTIVE, CONTROL });
	}
}
