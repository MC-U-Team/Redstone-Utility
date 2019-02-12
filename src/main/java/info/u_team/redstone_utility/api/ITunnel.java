package info.u_team.redstone_utility.api;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Implement this to mark a tileentity as a tunnel. This makes your tileentity
 * able to be inserted into the redstone tunnel network
 * 
 * @see ITunnelConnection
 * @see RedstoneTunnel
 * @author HyCraftHD
 *
 */
public interface ITunnel {
	
	/**
	 * Use the {@link TileEntity.getPos} from {@link TileEntity}
	 * 
	 * @return Current BlockPos of this tunnel
	 */
	BlockPos getPos();
	
	/**
	 * Use the {@link TileEntity.getWorld} from {@link TileEntity}
	 * 
	 * @return Current World this tunnel is in
	 */
	World getWorld();
	
}
