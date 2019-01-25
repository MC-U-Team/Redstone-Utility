package info.u_team.redstone_utility.tileentity;

import info.u_team.redstone_utility.api.*;
import info.u_team.u_team_core.tileentity.UTileEntity;
import it.unimi.dsi.fastutil.ints.Int2BooleanOpenHashMap;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;

public class TileEntityMultiBitWireOutput extends UTileEntity implements ITunnelConnection {
	
	private boolean addedToTunnel;
	
	@Override
	public void readNBT(NBTTagCompound compound) {
	}
	
	@Override
	public void writeNBT(NBTTagCompound compound) {
	}
	
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
		return new EnumFacing[] {};
	}
	
	@Override
	public EnumFacing[] isInput() {
		return EnumFacing.VALUES;
	}
	
	@Override
	public Int2BooleanOpenHashMap getBits(EnumFacing side) {
		return null;
	}
	
	// We will never receive bits
	@Override
	public void setBits(EnumFacing side, Int2BooleanOpenHashMap bits) {
		System.out.println(side);
		System.out.println(bits.toString());
	}
	
}
