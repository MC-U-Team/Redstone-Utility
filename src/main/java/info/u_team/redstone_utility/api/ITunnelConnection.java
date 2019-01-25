package info.u_team.redstone_utility.api;

import it.unimi.dsi.fastutil.ints.Int2BooleanOpenHashMap;
import net.minecraft.util.EnumFacing;

public interface ITunnelConnection extends ITunnel {
	
	public EnumFacing[] isOutput();
	
	public EnumFacing[] isInput();
	
	public Int2BooleanOpenHashMap getBits(EnumFacing side);
	
	public void setBits(EnumFacing side, Int2BooleanOpenHashMap bits);
	
}
