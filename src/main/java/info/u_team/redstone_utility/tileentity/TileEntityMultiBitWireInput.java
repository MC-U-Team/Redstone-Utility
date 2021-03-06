package info.u_team.redstone_utility.tileentity;

import info.u_team.redstone_utility.api.*;
import info.u_team.redstone_utility.block.BlockMultiBitWireConnection;
import info.u_team.u_team_core.tileentity.UTileEntity;
import it.unimi.dsi.fastutil.ints.Int2BooleanOpenHashMap;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileEntityMultiBitWireInput extends UTileEntity implements ITunnelConnection {
	
	private boolean addedToTunnel;
	
	// Save nbt data
	@Override
	public void readNBT(NBTTagCompound compound) {
	}
	
	@Override
	public void writeNBT(NBTTagCompound compound) {
	}
	
	// Add this tileentity to the network
	@Override
	public void invalidate() {
		super.invalidate();
		if (addedToTunnel) {
			RedstoneTunnel.instance.remove(this);
			addedToTunnel = false;
		}
	}
	
	@Override
	public void onChunkUnload() {
		super.onChunkUnload();
		if (addedToTunnel) {
			RedstoneTunnel.instance.remove(this);
			addedToTunnel = false;
		}
	}
	
	@Override
	public void onLoad() {
		super.onLoad();
		if (!addedToTunnel) {
			RedstoneTunnel.instance.add(this);
			addedToTunnel = true;
		}
	}
	
	@Override
	public EnumFacing[] isOutput() {
		return new EnumFacing[] { world.getBlockState(pos).getValue(BlockMultiBitWireConnection.FACING).getOpposite() };
	}
	
	@Override
	public EnumFacing[] isInput() {
		return new EnumFacing[] {};
	}
	
	@Override
	public Int2BooleanOpenHashMap getBits(EnumFacing side) {
		Int2BooleanOpenHashMap map = new Int2BooleanOpenHashMap();
		map.put(0, world.getBlockState(pos).getValue(BlockMultiBitWireConnection.POWERED).booleanValue());
		return map;
	}
	
	// We will never receive bits
	@Override
	public void setBits(EnumFacing side, Int2BooleanOpenHashMap bits) {
	}
	
	// Prevent breaking the tileentity when the powered state is updated
	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
		return oldState.getBlock() != newState.getBlock();
	}
	
}
