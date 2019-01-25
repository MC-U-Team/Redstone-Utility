package info.u_team.redstone_utility.api;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface ITunnel {
	
	BlockPos getPos(); // Use the getPos in TileEntity
	
	World getWorld(); // Use the getWorld in TileEntity
	
}
