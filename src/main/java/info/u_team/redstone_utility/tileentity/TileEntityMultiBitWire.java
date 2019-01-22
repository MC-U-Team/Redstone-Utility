package info.u_team.redstone_utility.tileentity;

import java.util.ArrayList;

import info.u_team.u_team_core.tileentity.UTileEntity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class TileEntityMultiBitWire extends UTileEntity {
	
	private boolean[] bits;
	
	public TileEntityMultiBitWire() {
		bits = new boolean[4];
	}
	
	@Override
	public void readNBT(NBTTagCompound compound) {
		NBTTagCompound bitsTag = compound.getCompoundTag("bits");
		for (int i = 0; i < bits.length; i++) {
			if (bitsTag.hasKey("" + i)) {
				bits[i] = bitsTag.getBoolean("" + i);
			} else {
				bits[i] = false;
			}
		}
	}
	
	@Override
	public void writeNBT(NBTTagCompound compound) {
		NBTTagCompound bitsTag = new NBTTagCompound();
		for (int i = 0; i < bits.length; i++) {
			bitsTag.setBoolean("" + i, bits[i]);
		}
		compound.setTag("bits", bitsTag);
	}
	
	public void setBit(int index, boolean value) {
		bits[index] = value;
	}
	
	public void setBitFromTe(int index, boolean value, BlockPos loc) {
		setBit(index, value);
		
		getTileEntityAroundWire(loc).forEach(t -> System.out.println(t + " - " + t.getPos()));
		
		getTileEntityAroundWire(loc).forEach(tile -> tile.setBitFromTe(0, value, getPos()));
		getTileEntityAroundOutput().forEach(t -> t.updateBit(value));
	}
	
	public boolean getBit(int index) {
		return bits[index];
	}
	
	public ArrayList<TileEntityMultiBitWire> getTileEntityAroundWire(BlockPos expect) {
		final ArrayList<TileEntityMultiBitWire> tiles = new ArrayList<>();
		
		for (final EnumFacing facing : EnumFacing.VALUES) {
			BlockPos loc = pos.offset(facing);
			if (!loc.equals(expect)) {
				final TileEntity tile = world.getTileEntity(loc);
				if (tile != null && tile instanceof TileEntityMultiBitWire) {
					tiles.add((TileEntityMultiBitWire) tile);
				}
			}
			
		}
		
		return tiles;
	}
	
	public ArrayList<TileEntityMultiBitOutput> getTileEntityAroundOutput() {
		final ArrayList<TileEntityMultiBitOutput> tiles = new ArrayList<>();
		
		for (final EnumFacing facing : EnumFacing.VALUES) {
			final TileEntity tile = world.getTileEntity(pos.offset(facing));
			if (tile != null && tile instanceof TileEntityMultiBitOutput) {
				tiles.add((TileEntityMultiBitOutput) tile);
			}
			
		}
		
		return tiles;
	}
}
