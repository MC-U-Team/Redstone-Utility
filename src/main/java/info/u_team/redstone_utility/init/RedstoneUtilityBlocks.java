package info.u_team.redstone_utility.init;

import info.u_team.redstone_utility.RedstoneUtilityConstants;
import info.u_team.redstone_utility.block.*;
import info.u_team.u_team_core.registry.BlockRegistry;
import info.u_team.u_team_core.util.RegistryUtil;
import net.minecraft.block.Block;

public class RedstoneUtilityBlocks {
	
	public static final BlockMultiBitWire multibitwire = new BlockMultiBitWire("multibitwire");
	
	public static final BlockMultiBitWireInput multibitwireinput = new BlockMultiBitWireInput("multibitwireinput");
	public static final BlockMultiBitWireOutput multibitwireoutput = new BlockMultiBitWireOutput("multibitwireoutput");
	
	public static void preinit() {
		BlockRegistry.register(RedstoneUtilityConstants.MODID, RegistryUtil.getRegistryEntries(Block.class, RedstoneUtilityBlocks.class));
	}
	
}
