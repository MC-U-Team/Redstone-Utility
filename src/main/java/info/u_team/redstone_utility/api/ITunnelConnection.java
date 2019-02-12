package info.u_team.redstone_utility.api;

import java.util.*;

import it.unimi.dsi.fastutil.ints.Int2BooleanOpenHashMap;
import net.minecraft.util.EnumFacing;

public interface ITunnelConnection extends ITunnel {
	
	public EnumFacing[] isOutput();
	
	public EnumFacing[] isInput();
	
	public default EnumFacing[] isConnection() {
		Set<EnumFacing> set = new HashSet<EnumFacing>();
		for (EnumFacing facing : isOutput()) {
			set.add(facing);
		}
		for (EnumFacing facing : isInput()) {
			set.add(facing);
		}
		EnumFacing[] facing = new EnumFacing[set.size()];
		return set.toArray(facing);
	}
	
	public Int2BooleanOpenHashMap getBits(EnumFacing side);
	
	public void setBits(EnumFacing side, Int2BooleanOpenHashMap bits);
	
}
