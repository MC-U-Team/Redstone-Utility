package info.u_team.redstone_utility.tileentity;

import info.u_team.redstone_utility.api.*;
import info.u_team.u_team_core.tileentity.UTileEntity;
import net.minecraft.nbt.NBTTagCompound;

public class TileEntityMultiBitWire extends UTileEntity implements ITunnel {
	
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
}
