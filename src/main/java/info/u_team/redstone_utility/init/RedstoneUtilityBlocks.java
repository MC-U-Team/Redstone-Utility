package info.u_team.redstone_utility.init;

import info.u_team.redstone_utility.RedstoneUtilityConstants;
import info.u_team.u_team_core.registry.BlockRegistry;
import info.u_team.u_team_core.util.RegistryUtil;
import net.minecraft.block.Block;

public class RedstoneUtilityBlocks {
	
	public static void preinit() {
		BlockRegistry.register(RedstoneUtilityConstants.MODID, RegistryUtil.getRegistryEntries(Block.class, RedstoneUtilityBlocks.class));
	}
	
}
