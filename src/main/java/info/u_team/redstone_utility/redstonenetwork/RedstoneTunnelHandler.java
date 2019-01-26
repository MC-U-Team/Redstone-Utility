package info.u_team.redstone_utility.redstonenetwork;

import java.util.*;

import info.u_team.redstone_utility.api.*;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class RedstoneTunnelHandler implements IRedstoneTunnelHandler {
	
	private Map<Integer, IRedstoneTunnelHandler> local = new HashMap<>();
	
	@Override
	public void add(ITunnel tunnel) {
		LocalRedstoneTunnelHandler handler = getLocalHandler(tunnel.getWorld());
		if (handler != null) {
			handler.add(tunnel);
		}
	}
	
	@Override
	public void remove(ITunnel tunnel) {
		LocalRedstoneTunnelHandler handler = getLocalHandler(tunnel.getWorld());
		if (handler != null) {
			handler.remove(tunnel);
		}
	}
	
	@Override
	public void update(ITunnelConnection tunnel, EnumFacing facing) {
		LocalRedstoneTunnelHandler handler = getLocalHandler(tunnel.getWorld());
		if (handler != null) {
			handler.update(tunnel, facing);
		}
	}
	
	@SubscribeEvent
	public static void on(WorldEvent.Unload event) {
		World world = event.getWorld();
		RedstoneTunnelHandler globalhandler = ((RedstoneTunnelHandler) RedstoneTunnel.instance);
		if (!world.isRemote) {
			globalhandler.local.remove(world.provider.getDimension());
		}
	}
	
	public synchronized LocalRedstoneTunnelHandler getLocalHandler(World world) {
		if (world == null || world.isRemote) {
			return null;
		}
		LocalRedstoneTunnelHandler handler = (LocalRedstoneTunnelHandler) local.get(world.provider.getDimension());
		if (handler == null) {
			handler = new LocalRedstoneTunnelHandler();
			local.put(world.provider.getDimension(), handler);
		}
		handler.setWorld(world);
		return handler;
	}
	
}
