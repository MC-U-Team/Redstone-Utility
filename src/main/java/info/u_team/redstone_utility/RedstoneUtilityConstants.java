package info.u_team.redstone_utility;

import org.apache.logging.log4j.*;

public class RedstoneUtilityConstants {
	
	public static final String MODID = "redstoneutility";
	public static final String NAME = "Redstone Utility";
	public static final String VERSION = "${version}";
	public static final String MCVERSION = "${mcversion}";
	public static final String DEPENDENCIES = "required:forge@[14.23.4.2745,);required-after:uteamcore@[2.2.4.94,)";
	public static final String UPDATEURL = "https://api.u-team.info/update/redstoneutility.json";
	
	public static final String COMMONPROXY = "info.u_team.redstone_utility.proxy.CommonProxy";
	public static final String CLIENTPROXY = "info.u_team.redstone_utility.proxy.ClientProxy";
	
	public static final Logger LOGGER = LogManager.getLogger(NAME);
	
}
