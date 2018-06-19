package info.u_team.redstone_utility.tab;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class CreativeTab extends CreativeTabs{

	public CreativeTab() {
		super("maintab");
	}

	@Override
	public ItemStack getTabIconItem() {
		return new ItemStack(Items.REDSTONE);
	}

}
