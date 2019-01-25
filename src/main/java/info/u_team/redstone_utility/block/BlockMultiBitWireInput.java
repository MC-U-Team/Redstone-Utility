package info.u_team.redstone_utility.block;

import info.u_team.redstone_utility.RedstoneUtilityConstants;
import info.u_team.redstone_utility.init.RedstoneUtilityCreativeTabs;
import info.u_team.redstone_utility.tileentity.TileEntityMultiBitWireInput;
import info.u_team.u_team_core.block.UBlockTileEntity;
import info.u_team.u_team_core.tileentity.UTileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;

public class BlockMultiBitWireInput extends UBlockTileEntity {
	
	public BlockMultiBitWireInput(String name) {
		super(name, Material.CLOTH, RedstoneUtilityCreativeTabs.tab, new UTileEntityProvider(new ResourceLocation(RedstoneUtilityConstants.MODID, "multibitwireinput"), TileEntityMultiBitWireInput.class));
	}
	
}
