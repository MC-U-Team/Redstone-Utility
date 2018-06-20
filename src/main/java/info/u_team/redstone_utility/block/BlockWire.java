package info.u_team.redstone_utility.block;

import java.util.*;

import javax.annotation.Nullable;

import com.google.common.collect.*;

import info.u_team.redstone_utility.RedstoneUtilityConstants;
import info.u_team.redstone_utility.tileentity.TileEntityWire;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.*;
import net.minecraft.block.state.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.*;

public class BlockWire extends Block {
	
	public static final PropertyEnum<BlockWire.EnumAttachPosition> NORTH = PropertyEnum.<BlockWire.EnumAttachPosition> create("north", BlockWire.EnumAttachPosition.class);
	public static final PropertyEnum<BlockWire.EnumAttachPosition> EAST = PropertyEnum.<BlockWire.EnumAttachPosition> create("east", BlockWire.EnumAttachPosition.class);
	public static final PropertyEnum<BlockWire.EnumAttachPosition> SOUTH = PropertyEnum.<BlockWire.EnumAttachPosition> create("south", BlockWire.EnumAttachPosition.class);
	public static final PropertyEnum<BlockWire.EnumAttachPosition> WEST = PropertyEnum.<BlockWire.EnumAttachPosition> create("west", BlockWire.EnumAttachPosition.class);
	public static final PropertyInteger POWER = PropertyInteger.create("power", 0, 15);
	protected static final AxisAlignedBB[] REDSTONE_WIRE_AABB = new AxisAlignedBB[] { new AxisAlignedBB(0.1875D, 0.0D, 0.1875D, 0.8125D, 0.0625D, 0.8125D), new AxisAlignedBB(0.1875D, 0.0D, 0.1875D, 0.8125D, 0.0625D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.1875D, 0.8125D, 0.0625D, 0.8125D), new AxisAlignedBB(0.0D, 0.0D, 0.1875D, 0.8125D, 0.0625D, 1.0D), new AxisAlignedBB(0.1875D, 0.0D, 0.0D, 0.8125D, 0.0625D, 0.8125D), new AxisAlignedBB(0.1875D, 0.0D, 0.0D, 0.8125D, 0.0625D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.8125D, 0.0625D, 0.8125D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.8125D, 0.0625D, 1.0D), new AxisAlignedBB(0.1875D, 0.0D, 0.1875D, 1.0D, 0.0625D, 0.8125D), new AxisAlignedBB(0.1875D, 0.0D, 0.1875D, 1.0D, 0.0625D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.1875D, 1.0D, 0.0625D, 0.8125D), new AxisAlignedBB(0.0D, 0.0D, 0.1875D, 1.0D, 0.0625D, 1.0D), new AxisAlignedBB(0.1875D, 0.0D, 0.0D, 1.0D, 0.0625D, 0.8125D), new AxisAlignedBB(0.1875D, 0.0D, 0.0D, 1.0D, 0.0625D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.0625D, 0.8125D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.0625D, 1.0D) };
	private boolean canProvidePower = true;
	/** List of blocks to update with redstone. */
	private final Set<BlockPos> blocksNeedingUpdate = Sets.<BlockPos> newHashSet();
	
	public BlockWire() {
		super(Material.CIRCUITS);
		this.setDefaultState(this.blockState.getBaseState().withProperty(NORTH, BlockWire.EnumAttachPosition.NONE).withProperty(EAST, BlockWire.EnumAttachPosition.NONE).withProperty(SOUTH, BlockWire.EnumAttachPosition.NONE).withProperty(WEST, BlockWire.EnumAttachPosition.NONE).withProperty(POWER, Integer.valueOf(0)));
		setRegistryName(new ResourceLocation(RedstoneUtilityConstants.MODID, "wire"));
		setUnlocalizedName("wire");
		GameRegistry.registerTileEntity(TileEntityWire.class, new ResourceLocation(RedstoneUtilityConstants.MODID, "wire"));
	}
	
	/**
	 * @deprecated call via
	 *             {@link IBlockState#getBoundingBox(IBlockAccess,BlockPos)}
	 *             whenever possible. Implementing/overriding is fine.
	 */
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return REDSTONE_WIRE_AABB[getAABBIndex(state.getActualState(source, pos))];
	}
	
	private static int getAABBIndex(IBlockState state) {
		int i = 0;
		boolean flag = state.getValue(NORTH) != BlockWire.EnumAttachPosition.NONE;
		boolean flag1 = state.getValue(EAST) != BlockWire.EnumAttachPosition.NONE;
		boolean flag2 = state.getValue(SOUTH) != BlockWire.EnumAttachPosition.NONE;
		boolean flag3 = state.getValue(WEST) != BlockWire.EnumAttachPosition.NONE;
		
		if (flag || flag2 && !flag && !flag1 && !flag3) {
			i |= 1 << EnumFacing.NORTH.getHorizontalIndex();
		}
		
		if (flag1 || flag3 && !flag && !flag1 && !flag2) {
			i |= 1 << EnumFacing.EAST.getHorizontalIndex();
		}
		
		if (flag2 || flag && !flag1 && !flag2 && !flag3) {
			i |= 1 << EnumFacing.SOUTH.getHorizontalIndex();
		}
		
		if (flag3 || flag1 && !flag && !flag2 && !flag3) {
			i |= 1 << EnumFacing.WEST.getHorizontalIndex();
		}
		
		return i;
	}
	
	/**
	 * Get the actual Block state of this Block at the given position. This applies
	 * properties not visible in the metadata, such as fence connections.
	 */
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		state = state.withProperty(WEST, this.getAttachPosition(worldIn, pos, EnumFacing.WEST));
		state = state.withProperty(EAST, this.getAttachPosition(worldIn, pos, EnumFacing.EAST));
		state = state.withProperty(NORTH, this.getAttachPosition(worldIn, pos, EnumFacing.NORTH));
		state = state.withProperty(SOUTH, this.getAttachPosition(worldIn, pos, EnumFacing.SOUTH));
		return state;
	}
	
	private BlockWire.EnumAttachPosition getAttachPosition(IBlockAccess worldIn, BlockPos pos, EnumFacing direction) {
		BlockPos blockpos = pos.offset(direction);
		IBlockState iblockstate = worldIn.getBlockState(pos.offset(direction));
		
		if (!canConnectTo(worldIn.getBlockState(blockpos), direction, worldIn, blockpos) && (iblockstate.isNormalCube() || !canConnectUpwardsTo(worldIn, blockpos.down()))) {
			IBlockState iblockstate1 = worldIn.getBlockState(pos.up());
			
			if (!iblockstate1.isNormalCube()) {
				boolean flag = worldIn.getBlockState(blockpos).isSideSolid(worldIn, blockpos, EnumFacing.UP) || worldIn.getBlockState(blockpos).getBlock() == Blocks.GLOWSTONE;
				
				if (flag && canConnectUpwardsTo(worldIn, blockpos.up())) {
					if (iblockstate.isBlockNormalCube()) {
						return BlockWire.EnumAttachPosition.UP;
					}
					
					return BlockWire.EnumAttachPosition.SIDE;
				}
			}
			
			return BlockWire.EnumAttachPosition.NONE;
		} else {
			return BlockWire.EnumAttachPosition.SIDE;
		}
	}
	
	/**
	 * @deprecated call via
	 *             {@link IBlockState#getCollisionBoundingBox(IBlockAccess,BlockPos)}
	 *             whenever possible. Implementing/overriding is fine.
	 */
	@Nullable
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
		return NULL_AABB;
	}
	
	/**
	 * Used to determine ambient occlusion and culling when rebuilding chunks for
	 * render
	 * 
	 * @deprecated call via {@link IBlockState#isOpaqueCube()} whenever possible.
	 *             Implementing/overriding is fine.
	 */
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
	/**
	 * @deprecated call via {@link IBlockState#isFullCube()} whenever possible.
	 *             Implementing/overriding is fine.
	 */
	public boolean isFullCube(IBlockState state) {
		return false;
	}
	
	/**
	 * Checks if this block can be placed exactly at the given position.
	 */
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		return worldIn.getBlockState(pos.down()).isSideSolid(worldIn, pos, EnumFacing.UP) || worldIn.getBlockState(pos.down()).getBlock() == Blocks.GLOWSTONE;
	}
	
	private IBlockState updateSurroundingRedstone(World worldIn, BlockPos pos, IBlockState state) {
		state = this.calculateCurrentChanges(worldIn, pos, pos, state);
		List<BlockPos> list = Lists.newArrayList(this.blocksNeedingUpdate);
		this.blocksNeedingUpdate.clear();
		
		for (BlockPos blockpos : list) {
			worldIn.notifyNeighborsOfStateChange(blockpos, this, false);
		}
		
		return state;
	}
	
	private IBlockState calculateCurrentChanges(World worldIn, BlockPos pos1, BlockPos pos2, IBlockState state) {
		IBlockState iblockstate = state;
		int i = ((Integer) state.getValue(POWER)).intValue();
		int j = 0;
		j = this.getMaxCurrentStrength(worldIn, pos2, j);
		this.canProvidePower = false;
		int k = worldIn.isBlockIndirectlyGettingPowered(pos1);
		this.canProvidePower = true;
		
		if (k > 0 && k > j - 1) {
			j = k;
		}
		
		int l = 0;
		
		for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
			BlockPos blockpos = pos1.offset(enumfacing);
			boolean flag = blockpos.getX() != pos2.getX() || blockpos.getZ() != pos2.getZ();
			
			if (flag) {
				l = this.getMaxCurrentStrength(worldIn, blockpos, l);
			}
			
			if (worldIn.getBlockState(blockpos).isNormalCube() && !worldIn.getBlockState(pos1.up()).isNormalCube()) {
				if (flag && pos1.getY() >= pos2.getY()) {
					l = this.getMaxCurrentStrength(worldIn, blockpos.up(), l);
				}
			} else if (!worldIn.getBlockState(blockpos).isNormalCube() && flag && pos1.getY() <= pos2.getY()) {
				l = this.getMaxCurrentStrength(worldIn, blockpos.down(), l);
			}
		}
		
		if (l > j) {
			j = l - 1;
		} else if (j > 0) {
			--j;
		} else {
			j = 0;
		}
		
		if (k > j - 1) {
			j = k;
		}
		
		if (i != j) {
			state = state.withProperty(POWER, Integer.valueOf(j));
			
			if (worldIn.getBlockState(pos1) == iblockstate) {
				worldIn.setBlockState(pos1, state, 2);
			}
			
			this.blocksNeedingUpdate.add(pos1);
			
			for (EnumFacing enumfacing1 : EnumFacing.values()) {
				this.blocksNeedingUpdate.add(pos1.offset(enumfacing1));
			}
		}
		
		return state;
	}
	
	/**
	 * Calls World.notifyNeighborsOfStateChange() for all neighboring blocks, but
	 * only if the given block is a redstone wire.
	 */
	private void notifyWireNeighborsOfStateChange(World worldIn, BlockPos pos) {
		if (worldIn.getBlockState(pos).getBlock() == this) {
			worldIn.notifyNeighborsOfStateChange(pos, this, false);
			
			for (EnumFacing enumfacing : EnumFacing.values()) {
				worldIn.notifyNeighborsOfStateChange(pos.offset(enumfacing), this, false);
			}
		}
	}
	
	/**
	 * Called after the block is set in the Chunk data, but before the Tile Entity
	 * is set
	 */
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
		if (!worldIn.isRemote) {
			this.updateSurroundingRedstone(worldIn, pos, state);
			
			for (EnumFacing enumfacing : EnumFacing.Plane.VERTICAL) {
				worldIn.notifyNeighborsOfStateChange(pos.offset(enumfacing), this, false);
			}
			
			for (EnumFacing enumfacing1 : EnumFacing.Plane.HORIZONTAL) {
				this.notifyWireNeighborsOfStateChange(worldIn, pos.offset(enumfacing1));
			}
			
			for (EnumFacing enumfacing2 : EnumFacing.Plane.HORIZONTAL) {
				BlockPos blockpos = pos.offset(enumfacing2);
				
				if (worldIn.getBlockState(blockpos).isNormalCube()) {
					this.notifyWireNeighborsOfStateChange(worldIn, blockpos.up());
				} else {
					this.notifyWireNeighborsOfStateChange(worldIn, blockpos.down());
				}
			}
		}
	}
	
	/**
	 * Called serverside after this block is replaced with another in Chunk, but
	 * before the Tile Entity is updated
	 */
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		super.breakBlock(worldIn, pos, state);
		
		if (!worldIn.isRemote) {
			for (EnumFacing enumfacing : EnumFacing.values()) {
				worldIn.notifyNeighborsOfStateChange(pos.offset(enumfacing), this, false);
			}
			
			this.updateSurroundingRedstone(worldIn, pos, state);
			
			for (EnumFacing enumfacing1 : EnumFacing.Plane.HORIZONTAL) {
				this.notifyWireNeighborsOfStateChange(worldIn, pos.offset(enumfacing1));
			}
			
			for (EnumFacing enumfacing2 : EnumFacing.Plane.HORIZONTAL) {
				BlockPos blockpos = pos.offset(enumfacing2);
				
				if (worldIn.getBlockState(blockpos).isNormalCube()) {
					this.notifyWireNeighborsOfStateChange(worldIn, blockpos.up());
				} else {
					this.notifyWireNeighborsOfStateChange(worldIn, blockpos.down());
				}
			}
		}
	}
	
	private int getMaxCurrentStrength(World worldIn, BlockPos pos, int strength) {
		if (worldIn.getBlockState(pos).getBlock() != this) {
			return strength;
		} else {
			int i = ((Integer) worldIn.getBlockState(pos).getValue(POWER)).intValue();
			return i > strength ? i : strength;
		}
	}
	
	/**
	 * Called when a neighboring block was changed and marks that this state should
	 * perform any checks during a neighbor change. Cases may include when redstone
	 * power is updated, cactus blocks popping off due to a neighboring solid block,
	 * etc.
	 */
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		if (!worldIn.isRemote) {
			if (this.canPlaceBlockAt(worldIn, pos)) {
				this.updateSurroundingRedstone(worldIn, pos, state);
			} else {
				this.dropBlockAsItem(worldIn, pos, state, 0);
				worldIn.setBlockToAir(pos);
			}
		}
	}
	
	/**
	 * Get the Item that this Block should drop when harvested.
	 */
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return Items.REDSTONE;
	}
	
	/**
	 * @deprecated call via
	 *             {@link IBlockState#getStrongPower(IBlockAccess,BlockPos,EnumFacing)}
	 *             whenever possible. Implementing/overriding is fine.
	 */
	public int getStrongPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		return !this.canProvidePower ? 0 : blockState.getWeakPower(blockAccess, pos, side);
	}
	
	/**
	 * @deprecated call via
	 *             {@link IBlockState#getWeakPower(IBlockAccess,BlockPos,EnumFacing)}
	 *             whenever possible. Implementing/overriding is fine.
	 */
	public int getWeakPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		if (!this.canProvidePower) {
			return 0;
		} else {
			int i = ((Integer) blockState.getValue(POWER)).intValue();
			
			if (i == 0) {
				return 0;
			} else if (side == EnumFacing.UP) {
				return i;
			} else {
				EnumSet<EnumFacing> enumset = EnumSet.<EnumFacing> noneOf(EnumFacing.class);
				
				for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
					if (this.isPowerSourceAt(blockAccess, pos, enumfacing)) {
						enumset.add(enumfacing);
					}
				}
				
				if (side.getAxis().isHorizontal() && enumset.isEmpty()) {
					return i;
				} else if (enumset.contains(side) && !enumset.contains(side.rotateYCCW()) && !enumset.contains(side.rotateY())) {
					return i;
				} else {
					return 0;
				}
			}
		}
	}
	
	private boolean isPowerSourceAt(IBlockAccess worldIn, BlockPos pos, EnumFacing side) {
		BlockPos blockpos = pos.offset(side);
		IBlockState iblockstate = worldIn.getBlockState(blockpos);
		boolean flag = iblockstate.isNormalCube();
		boolean flag1 = worldIn.getBlockState(pos.up()).isNormalCube();
		
		if (!flag1 && flag && canConnectUpwardsTo(worldIn, blockpos.up())) {
			return true;
		} else if (canConnectTo(iblockstate, side, worldIn, pos)) {
			return true;
		} else if (iblockstate.getBlock() == Blocks.POWERED_REPEATER && iblockstate.getValue(BlockRedstoneDiode.FACING) == side) {
			return true;
		} else {
			return !flag && canConnectUpwardsTo(worldIn, blockpos.down());
		}
	}
	
	protected static boolean canConnectUpwardsTo(IBlockAccess worldIn, BlockPos pos) {
		return canConnectTo(worldIn.getBlockState(pos), null, worldIn, pos);
	}
	
	protected static boolean canConnectTo(IBlockState blockState, @Nullable EnumFacing side, IBlockAccess world, BlockPos pos) {
		Block block = blockState.getBlock();
		
		if (block == Blocks.REDSTONE_WIRE) {
			return true;
		} else if (Blocks.UNPOWERED_REPEATER.isSameDiode(blockState)) {
			EnumFacing enumfacing = (EnumFacing) blockState.getValue(BlockRedstoneRepeater.FACING);
			return enumfacing == side || enumfacing.getOpposite() == side;
		} else if (Blocks.OBSERVER == blockState.getBlock()) {
			return side == blockState.getValue(BlockObserver.FACING);
		} else {
			return blockState.getBlock().canConnectRedstone(blockState, world, pos, side);
		}
	}
	
	/**
	 * Can this block provide power. Only wire currently seems to have this change
	 * based on its state.
	 * 
	 * @deprecated call via {@link IBlockState#canProvidePower()} whenever possible.
	 *             Implementing/overriding is fine.
	 */
	public boolean canProvidePower(IBlockState state) {
		return this.canProvidePower;
	}
	
	@SideOnly(Side.CLIENT)
	public static int colorMultiplier(int p_176337_0_) {
		float f = (float) p_176337_0_ / 15.0F;
		float f1 = f * 0.6F + 0.4F;
		
		if (p_176337_0_ == 0) {
			f1 = 0.3F;
		}
		
		float f2 = f * f * 0.7F - 0.5F;
		float f3 = f * f * 0.6F - 0.7F;
		
		if (f2 < 0.0F) {
			f2 = 0.0F;
		}
		
		if (f3 < 0.0F) {
			f3 = 0.0F;
		}
		
		int i = MathHelper.clamp((int) (f1 * 255.0F), 0, 255);
		int j = MathHelper.clamp((int) (f2 * 255.0F), 0, 255);
		int k = MathHelper.clamp((int) (f3 * 255.0F), 0, 255);
		return -16777216 | i << 16 | j << 8 | k;
	}
	
	/**
	 * Called periodically clientside on blocks near the player to show effects
	 * (like furnace fire particles). Note that this method is unrelated to
	 * {@link randomTick} and {@link #needsRandomTick}, and will always be called
	 * regardless of whether the block can receive random update ticks
	 */
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		int i = ((Integer) stateIn.getValue(POWER)).intValue();
		
		if (i != 0) {
			double d0 = (double) pos.getX() + 0.5D + ((double) rand.nextFloat() - 0.5D) * 0.2D;
			double d1 = (double) ((float) pos.getY() + 0.0625F);
			double d2 = (double) pos.getZ() + 0.5D + ((double) rand.nextFloat() - 0.5D) * 0.2D;
			float f = (float) i / 15.0F;
			float f1 = f * 0.6F + 0.4F;
			float f2 = Math.max(0.0F, f * f * 0.7F - 0.5F);
			float f3 = Math.max(0.0F, f * f * 0.6F - 0.7F);
			worldIn.spawnParticle(EnumParticleTypes.REDSTONE, d0, d1, d2, (double) f1, (double) f2, (double) f3);
		}
	}
	
	public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
		return new ItemStack(Items.REDSTONE);
	}
	
	/**
	 * Convert the given metadata into a BlockState for this Block
	 */
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(POWER, Integer.valueOf(meta));
	}
	
	/**
	 * Gets the render layer this block will render on. SOLID for solid blocks,
	 * CUTOUT or CUTOUT_MIPPED for on-off transparency (glass, reeds), TRANSLUCENT
	 * for fully blended transparency (stained glass)
	 */
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT;
	}
	
	/**
	 * Convert the BlockState into the correct metadata value
	 */
	public int getMetaFromState(IBlockState state) {
		return ((Integer) state.getValue(POWER)).intValue();
	}
	
	/**
	 * Returns the blockstate with the given rotation from the passed blockstate. If
	 * inapplicable, returns the passed blockstate.
	 * 
	 * @deprecated call via {@link IBlockState#withRotation(Rotation)} whenever
	 *             possible. Implementing/overriding is fine.
	 */
	public IBlockState withRotation(IBlockState state, Rotation rot) {
		switch (rot) {
		case CLOCKWISE_180:
			return state.withProperty(NORTH, state.getValue(SOUTH)).withProperty(EAST, state.getValue(WEST)).withProperty(SOUTH, state.getValue(NORTH)).withProperty(WEST, state.getValue(EAST));
		case COUNTERCLOCKWISE_90:
			return state.withProperty(NORTH, state.getValue(EAST)).withProperty(EAST, state.getValue(SOUTH)).withProperty(SOUTH, state.getValue(WEST)).withProperty(WEST, state.getValue(NORTH));
		case CLOCKWISE_90:
			return state.withProperty(NORTH, state.getValue(WEST)).withProperty(EAST, state.getValue(NORTH)).withProperty(SOUTH, state.getValue(EAST)).withProperty(WEST, state.getValue(SOUTH));
		default:
			return state;
		}
	}
	
	/**
	 * Returns the blockstate with the given mirror of the passed blockstate. If
	 * inapplicable, returns the passed blockstate.
	 * 
	 * @deprecated call via {@link IBlockState#withMirror(Mirror)} whenever
	 *             possible. Implementing/overriding is fine.
	 */
	public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
		switch (mirrorIn) {
		case LEFT_RIGHT:
			return state.withProperty(NORTH, state.getValue(SOUTH)).withProperty(SOUTH, state.getValue(NORTH));
		case FRONT_BACK:
			return state.withProperty(EAST, state.getValue(WEST)).withProperty(WEST, state.getValue(EAST));
		default:
			return super.withMirror(state, mirrorIn);
		}
	}
	
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { NORTH, EAST, SOUTH, WEST, POWER });
	}
	
	/**
	 * Get the geometry of the queried face at the given position and state. This is
	 * used to decide whether things like buttons are allowed to be placed on the
	 * face, or how glass panes connect to the face, among other things.
	 * <p>
	 * Common values are {@code SOLID}, which is the default, and {@code UNDEFINED},
	 * which represents something that does not fit the other descriptions and will
	 * generally cause other things not to connect to the face.
	 * 
	 * @return an approximation of the form of the given face
	 * @deprecated call via
	 *             {@link IBlockState#getBlockFaceShape(IBlockAccess,BlockPos,EnumFacing)}
	 *             whenever possible. Implementing/overriding is fine.
	 */
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
		return BlockFaceShape.UNDEFINED;
	}
	
	static enum EnumAttachPosition implements IStringSerializable {
		UP("up"),
		SIDE("side"),
		NONE("none");
		
		private final String name;
		
		private EnumAttachPosition(String name) {
			this.name = name;
		}
		
		public String toString() {
			return this.getName();
		}
		
		public String getName() {
			return this.name;
		}
	}
}