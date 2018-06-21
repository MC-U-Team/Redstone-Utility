package info.u_team.redstone_utility.block.gate;

import info.u_team.redstone_utility.RedstoneUtilityConstants;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.ResourceLocation;

public class BlockAndGate extends BlockGates {

	public BlockAndGate() {
		super();
		setUnlocalizedName("andgate");
		setRegistryName(new ResourceLocation(RedstoneUtilityConstants.MODID, "andgate"));
	}
}
