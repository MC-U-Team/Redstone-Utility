package info.u_team.redstone_utility.api;

import net.minecraft.util.EnumFacing;

public interface IRedstoneTunnelHandler {
	
	void add(ITunnel tunnel);
	
	void remove(ITunnel tunnel);
	
	default void update(ITunnelConnection tunnel) {
		for (EnumFacing facing : tunnel.isOutput()) {
			update(tunnel, facing);
		}
	}
	
	void update(ITunnelConnection tunnel, EnumFacing facing);
	
}
