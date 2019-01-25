package info.u_team.redstone_utility.redstonenetwork;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.*;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.graph.*;

import info.u_team.redstone_utility.api.*;
import it.unimi.dsi.fastutil.ints.Int2BooleanOpenHashMap;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class LocalRedstoneTunnelHandler implements IRedstoneTunnelHandler {
	
	private final MutableGraph<Pair<BlockPos, Boolean>> graph;
	
	private World world;
	
	public LocalRedstoneTunnelHandler() {
		graph = GraphBuilder.undirected().build();
	}
	
	// We update the world everytime we access this, cause there might be other
	// instances of the world. This is not the best way, but otherwise it wont work
	public void setWorld(World world) {
		this.world = world;
	}
	
	@Override
	public void add(ITunnel tunnel) {
		BlockPos pos = tunnel.getPos().toImmutable();
		boolean connection = tunnel instanceof ITunnelConnection;
		Pair<BlockPos, Boolean> ourPair = Pair.of(pos, connection);
		graph.addNode(ourPair);
		
		// Add edges
		
		BlockPos[] sides = new BlockPos[] { pos.up(), pos.down(), pos.north(), pos.east(), pos.south(), pos.west() };
		
		graph.nodes().forEach(otherPair -> {
			BlockPos otherPos = otherPair.getLeft();
			for (BlockPos side : sides) {
				if (side.equals(otherPos)) {
					graph.putEdge(ourPair, otherPair);
				}
			}
		});
	}
	
	@Override
	public void remove(ITunnel tunnel) {
		BlockPos pos = tunnel.getPos().toImmutable();
		boolean connection = tunnel instanceof ITunnelConnection;
		graph.removeNode(Pair.of(pos, connection));
	}
	
	@Override
	public void update(ITunnelConnection tunnel, EnumFacing facing) {
		
		BlockPos pos = tunnel.getPos().toImmutable();
		BlockPos startPos = pos.offset(facing);
		
		Int2BooleanOpenHashMap bits = tunnel.getBits(facing);
		
		graph.nodes().forEach(otherPair -> {
			BlockPos otherPos = otherPair.getLeft();
			if (startPos.equals(otherPos)) {
				
				if (!otherPair.getRight()) { // If its not an ITunnelConnector
					
					reachableNodesWithConnection(otherPair).forEach((destination) -> {
						BlockPos previousPos = destination.getLeft().getLeft();
						BlockPos finalPos = destination.getRight().getLeft();
						
						if (!finalPos.equals(pos)) { // We don not want update to start position
							if (world.isBlockLoaded(finalPos)) {
								TileEntity tileentity = world.getTileEntity(finalPos);
								if (tileentity instanceof ITunnelConnection) {
									ITunnelConnection connection = (ITunnelConnection) tileentity;
									for (EnumFacing finalFacing : connection.isInput()) {
										if (finalPos.offset(finalFacing).equals(previousPos)) {
											connection.setBits(finalFacing, bits);
											break;
										}
									}
								}
							}
						}
					});
				} else {
					// If its an ITunnelConnector //TODO should we allow direct connections?
				}
				
				return;
			}
		});
	}
	
	public Set<Pair<Pair<BlockPos, Boolean>, Pair<BlockPos, Boolean>>> reachableNodesWithConnection(Pair<BlockPos, Boolean> node) {
		checkArgument(graph.nodes().contains(node), "ERROR", node);
		Set<Pair<BlockPos, Boolean>> visitedNodes = new LinkedHashSet<>();
		Set<Pair<Pair<BlockPos, Boolean>, Pair<BlockPos, Boolean>>> finalNodePairs = new LinkedHashSet<>();
		Queue<Pair<BlockPos, Boolean>> queuedNodes = new ArrayDeque<Pair<BlockPos, Boolean>>();
		visitedNodes.add(node);
		queuedNodes.add(node);
		
		while (!queuedNodes.isEmpty()) {
			Pair<BlockPos, Boolean> currentNode = queuedNodes.remove();
			if (!currentNode.getRight()) { // Skip paths after one ITunnelConnector
				for (Pair<BlockPos, Boolean> successor : graph.successors(currentNode)) {
					if (visitedNodes.add(successor)) {
						if (successor.getRight()) {
							finalNodePairs.add(Pair.of(currentNode, successor)); // Only add them if they are an ITunnelConenctor and not the node from where we
																					// queued
						}
						queuedNodes.add(successor);
					}
				}
			}
		}
		return Collections.unmodifiableSet(finalNodePairs);
	}
	
}
