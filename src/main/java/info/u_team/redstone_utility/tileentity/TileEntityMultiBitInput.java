package info.u_team.redstone_utility.tileentity;

import java.util.ArrayList;

import info.u_team.u_team_core.tileentity.UTileEntity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;

public class TileEntityMultiBitInput extends UTileEntity implements ITickable {
	
	boolean bit;
	
	int bitheight = 0;
	
	public TileEntityMultiBitInput() {
	}
	
	@Override
	public void readNBT(NBTTagCompound compound) {
	}
	
	@Override
	public void writeNBT(NBTTagCompound compound) {
	}
	
	@Override
	public void update() {
		if (world.isRemote) {
			return;
		}
		boolean powered = world.isBlockPowered(pos);
		if (powered != bit) {
			bit = powered;
			getTileEntityAround().forEach(tile -> tile.setBitFromTe(0, powered, pos));
		}
	}
	
	public ArrayList<TileEntityMultiBitWire> getTileEntityAround() {
		final ArrayList<TileEntityMultiBitWire> tiles = new ArrayList<>();
		
		for (final EnumFacing facing : EnumFacing.VALUES) {
			final TileEntity tile = world.getTileEntity(pos.offset(facing));
			if (tile != null && tile instanceof TileEntityMultiBitWire) {
				tiles.add((TileEntityMultiBitWire) tile);
			}
			
		}
		
		return tiles;
	}
	
}
