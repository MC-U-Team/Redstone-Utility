package info.u_team.redstone_utility.api;

import net.minecraft.util.EnumFacing;

/**
 * This interface should not be implemented by any consumer. It holds method to
 * add, remove and update the redstone network
 * 
 * @author HyCraftHD
 *
 */
public interface IRedstoneTunnelHandler {
	
	/**
	 * Adds an {@link ITunnel} and an {@link ITunnelConnection} to the network. E.g
	 * when its placed, or tileentity is loaded. This method must always be called
	 * when a new tunnel is added to the network.
	 * 
	 * @param tunnel
	 *            The tilentity that implements an ITunnel. Cannot be null
	 */
	void add(ITunnel tunnel);
	
	/**
	 * Removes an {@link ITunnel} and an {@link ITunnelConnection} from the network
	 * E.g when its destroyed, or the chunk is unloaded. When the world is unloaded
	 * the whole local tunnel handler implementation will be dereferenced so this
	 * method will not be called
	 * 
	 * @param tunnel
	 *            The tilentity that implements an {@link ITunnel}. Cannot be null
	 */
	void remove(ITunnel tunnel);
	
	/**
	 * Send this update to all facings that are marked as an input from
	 * {@link ITunnelConnection}
	 * 
	 * @see IRedstoneTunnelHandler.update(ITunnelConnection, EnumFacing)
	 * 
	 * @param tunnel
	 *            The tilentity that implements an {@link ITunnelConnection}. Cannot
	 *            be null
	 */
	default void update(ITunnelConnection tunnel) {
		for (EnumFacing facing : tunnel.isInput()) {
			update(tunnel, facing);
		}
	}
	
	/**
	 * Send an update request to the network. This will check if the
	 * {@link ITunnelConnection} has a valid input on the corresponding side. After
	 * that it will notify all connected {@link ITunnelConnection} to get their bits
	 * and or them together (Redstone mechanic). After that it will send the bits to
	 * all connected {@link ITunnelConnection} that have a connected output.
	 * 
	 * @param tunnel
	 *            The tilentity that implements an {@link ITunnelConnection}. Cannot
	 *            be null
	 * @param facing
	 *            The facing to send the update to
	 */
	void update(ITunnelConnection tunnel, EnumFacing facing);
	
}
