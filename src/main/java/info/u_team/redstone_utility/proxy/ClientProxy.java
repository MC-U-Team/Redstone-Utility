package info.u_team.redstone_utility.proxy;

import info.u_team.redstone_utility.block.gate.BlockDFlipflopGate;
import info.u_team.redstone_utility.init.RedstoneUtilityBlocks;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.event.*;

public class ClientProxy extends CommonProxy {
	
	@Override
	public void preinit(FMLPreInitializationEvent event) {
		super.preinit(event);
		ModelLoader.setCustomStateMapper(RedstoneUtilityBlocks.dflipflop, new StateMap.Builder().ignore(BlockDFlipflopGate.CONTROL).build());
	}
	
	@Override
	public void init(FMLInitializationEvent event) {
		super.init(event);
	}
	
	@Override
	public void postinit(FMLPostInitializationEvent event) {
		super.postinit(event);
	}
	
}
