package info.u_team.redstone_utility.block;

import org.apache.commons.lang3.tuple.Pair;

import info.u_team.redstone_utility.api.RedstoneTunnel;
import info.u_team.redstone_utility.tileentity.TileEntityMultiBitWireInput;
import net.minecraft.block.*;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockMultiBitWireInput extends BlockMultiBitWireConnection {
	
	public BlockMultiBitWireInput(String name) {
		super(name, TileEntityMultiBitWireInput.class);
	}
	
	// Listen for neighbor changes to update network input
	
	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block neighborBlock, BlockPos neighborPos) {
		if (world.isRemote) {
			return;
		}
		
		EnumFacing facing = checkFacing(pos, neighborPos);
		
		if (facing == state.getValue(FACING)) {
			
			boolean newValue = isPowered(world, pos, facing);
			boolean oldValue = state.getValue(POWERED);
			if (newValue != oldValue) {
				world.setBlockState(pos, state.withProperty(POWERED, newValue));
				Pair<Boolean, TileEntity> pair = isTileEntityFromProvider(world, pos);
				if (pair.getLeft()) {
					TileEntityMultiBitWireInput tileentity = (TileEntityMultiBitWireInput) pair.getRight();
					long startTime = System.nanoTime();
					RedstoneTunnel.instance.update(tileentity, facing.getOpposite());
					long endTime = System.nanoTime();
					
					long timeElapsed = endTime - startTime;
					
					System.out.println("nanoseconds: " + timeElapsed);
					System.out.println("milliseconds: " + timeElapsed / 1000000);
				}
			}
		}
	}
	
	protected boolean isPowered(World world, BlockPos pos, EnumFacing facing) {
		BlockPos blockpos = pos.offset(facing);
		int i = world.getRedstonePower(blockpos, facing);
		if (i >= 15) {
			return true;
		} else {
			IBlockState iblockstate = world.getBlockState(blockpos);
			return Math.max(i, iblockstate.getBlock() == Blocks.REDSTONE_WIRE ? iblockstate.getValue(BlockRedstoneWire.POWER).intValue() : 0) > 0;
		}
	}
	
	private EnumFacing checkFacing(BlockPos pos, BlockPos other) {
		if (pos.north().equals(other)) {
			return EnumFacing.NORTH;
		} else if (pos.south().equals(other)) {
			return EnumFacing.SOUTH;
		} else if (pos.west().equals(other)) {
			return EnumFacing.WEST;
		} else if (pos.east().equals(other)) {
			return EnumFacing.EAST;
		} else if (pos.up().equals(other)) {
			return EnumFacing.EAST;
		} else if (pos.down().equals(other)) {
			return EnumFacing.EAST;
		}
		return EnumFacing.NORTH;
	}
	
}
