package info.u_team.redstone_utility;

import static info.u_team.redstone_utility.RedstoneUtilityConstants.CLIENTPROXY;
import static info.u_team.redstone_utility.RedstoneUtilityConstants.COMMONPROXY;
import static info.u_team.redstone_utility.RedstoneUtilityConstants.MODID;
import static info.u_team.redstone_utility.RedstoneUtilityConstants.NAME;
import static info.u_team.redstone_utility.RedstoneUtilityConstants.VERSION;

import info.u_team.redstone_utility.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

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
