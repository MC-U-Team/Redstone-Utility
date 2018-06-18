package info.u_team.redstone_utility;

import static info.u_team.redstone_utility.RedstoneUtilityConstants.*;

import info.u_team.redstone_utility.proxy.CommonProxy;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.common.Mod.*;
import net.minecraftforge.fml.common.event.*;

@Mod(modid = MODID, name = NAME, version = VERSION)
public class RedstoneUtilityMod {
	
	@Instance
	private static RedstoneUtilityMod instance;
	
	public static RedstoneUtilityMod getInstance() {
		return instance;
	}
	
	@SidedProxy(serverSide = COMMONPROXY, clientSide = CLIENTPROXY)
	private static CommonProxy proxy;
	
	@EventHandler
	public void preinit(FMLPreInitializationEvent event) {
		proxy.preinit(event);
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.init(event);
	}
	
	@EventHandler
	public void postinit(FMLPostInitializationEvent event) {
		proxy.postinit(event);
	}
	
}
