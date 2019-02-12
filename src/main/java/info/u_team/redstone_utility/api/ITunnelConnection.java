package info.u_team.redstone_utility.api;

import java.util.*;

import it.unimi.dsi.fastutil.ints.Int2BooleanOpenHashMap;
import net.minecraft.util.EnumFacing;

/**
 * Implement this to mark your tileentity to be able to receive and send
 * messages to the redstone tunnel network
 * 
 * @see ITunnelConnection
 * @see RedstoneTunnel
 * @author HyCraftHD
 *
 */
public interface ITunnelConnection extends ITunnel {
	
	/**
	 * Which facing this connection is able to receive bits from the network
	 * 
	 * @see ITunnelConenction.setBits
	 * @return EnumFacing Array with all possible connections. Cannot be null but
	 *         can be an empty array
	 */
	EnumFacing[] isOutput();
	
	/**
	 * Will be called when this connection receives a new message from the network
	 * 
	 * @param side
	 *            From which side the message came from. Cannot be null
	 * @param bits
	 *            The hashmap with the bits. Cannot be null
	 */
	void setBits(EnumFacing side, Int2BooleanOpenHashMap bits);
	
	/**
	 * Which facing this connection is able to send bits from to the network
	 * 
	 * @see ITunnelConenction.getBits
	 * @return EnumFacing Array with all possible connections. Cannot be null but
	 *         can be an empty array
	 */
	EnumFacing[] isInput();
	
	/**
	 * Will be called if the network is updated and want to get new bits
	 * 
	 * @param side
	 *            From which side the update is requested. Cannot be null
	 * @return The hashmap that holds all bits. Cannot be null
	 */
	Int2BooleanOpenHashMap getBits(EnumFacing side);
	
	/**
	 * Which facing is an input or an output of the network. Used for
	 * {@link ITunnel} basic blocks to determine if they can connect to this
	 * tileenity
	 * 
	 * @return EnumFacing Array with all possible connections. Cannot be null but
	 *         can be an empty array
	 */
	default EnumFacing[] isConnection() {
		Set<EnumFacing> set = new HashSet<EnumFacing>();
		for (EnumFacing facing : isOutput()) {
			set.add(facing);
		}
		for (EnumFacing facing : isInput()) {
			set.add(facing);
		}
		EnumFacing[] facing = new EnumFacing[set.size()];
		return set.toArray(facing);
	}
	
}
