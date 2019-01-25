package info.u_team.redstone_utility.tileentity;

import info.u_team.redstone_utility.api.*;
import info.u_team.u_team_core.tileentity.UTileEntity;
import it.unimi.dsi.fastutil.ints.Int2BooleanOpenHashMap;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;

public class TileEntityMultiBitWireInput extends UTileEntity implements ITunnelConnection, ITickable {
	
	private boolean addedToTunnel;
	
	private Int2BooleanOpenHashMap map;
	
	public TileEntityMultiBitWireInput() {
		map = new Int2BooleanOpenHashMap();
	}
	
	private int time;
	
	private int counter;
	
	@Override
	public void update() {
		if(world.isRemote) {
			return;
		}
		if (time >= 100) {
			time = 0;
			map.put(counter, true);
			RedstoneTunnel.instance.update(this);
			counter++;
		} else {
			time++;
		}
	}
	
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
		return EnumFacing.VALUES;
	}
	
	@Override
	public EnumFacing[] isInput() {
		return new EnumFacing[] {};
	}
	
	@Override
	public Int2BooleanOpenHashMap getBits(EnumFacing side) {
		return map;
	}
	
	// We will never receive bits
	@Override
	public void setBits(EnumFacing side, Int2BooleanOpenHashMap bits) {
	}
	
}
