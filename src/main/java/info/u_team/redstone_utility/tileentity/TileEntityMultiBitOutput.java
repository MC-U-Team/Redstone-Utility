package info.u_team.redstone_utility.tileentity;

import info.u_team.u_team_core.tileentity.UTileEntity;
import net.minecraft.nbt.NBTTagCompound;

public class TileEntityMultiBitOutput extends UTileEntity {
	
	private boolean bit;
	
	public TileEntityMultiBitOutput() {
	}
	
	@Override
	public void readNBT(NBTTagCompound compound) {
	}
	
	@Override
	public void writeNBT(NBTTagCompound compound) {
	}
	
	public void updateBit(boolean value) {
		System.out.println("force; " + value);
		bit = value;
		world.notifyNeighborsOfStateChange(pos, blockType, true);
	}
	
	public int getBit() {
		return bit ? 15 : 0;
	}
	
}
