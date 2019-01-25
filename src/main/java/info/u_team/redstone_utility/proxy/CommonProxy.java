package info.u_team.redstone_utility.proxy;

import info.u_team.redstone_utility.api.*;
import info.u_team.redstone_utility.init.*;
import info.u_team.redstone_utility.redstonenetwork.RedstoneTunnelHandler;
import info.u_team.u_team_core.registry.CommonRegistry;
import net.minecraftforge.fml.common.event.*;

public class CommonProxy {
	
	public void preinit(FMLPreInitializationEvent event) {
		RedstoneUtilityBlocks.preinit();
	}
	
	public void init(FMLInitializationEvent event) {
		RedstoneUtilityCreativeTabs.init();
		RedstoneTunnel.instance = new RedstoneTunnelHandler(); // Init api
		CommonRegistry.registerEventHandler(RedstoneTunnelHandler.class);
	}
	
	public void postinit(FMLPostInitializationEvent event) {
	}
	
}
