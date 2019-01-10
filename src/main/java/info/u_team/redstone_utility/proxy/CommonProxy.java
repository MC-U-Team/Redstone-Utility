package info.u_team.redstone_utility.proxy;

import info.u_team.redstone_utility.init.*;
import net.minecraftforge.fml.common.event.*;

public class CommonProxy {
	
	public void preinit(FMLPreInitializationEvent event) {
		RedstoneUtilityBlocks.preinit();
	}
	
	public void init(FMLInitializationEvent event) {
		RedstoneUtilityCreativeTabs.init();
	}
	
	public void postinit(FMLPostInitializationEvent event) {
	}
	
}
