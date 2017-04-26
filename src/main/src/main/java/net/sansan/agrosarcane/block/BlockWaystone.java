package net.sansan.agrosarcane.block;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.sansan.agrosarcane.item.ItemShard;

public class BlockWaystone extends BlockBase {

	public static final PropertyEnum<EnumType> TYPE = PropertyEnum.create("type", EnumType.class);
	
	private BlockPos moundOrigin; // the block keeps track of its mound's origin
	private ItemShard drop; // the dropped shard
	
	private boolean canDestroy = true; // determines if the waystone block can be destroyed at this point or not
	
	private AxisAlignedBB boundingBox[] = new AxisAlignedBB[EnumType.values().length];
	
	public BlockWaystone(String blockName, int meta, Item drop) {
		super(Material.CLAY, blockName);
		
		this.setHardness(0.1f);
		this.setResistance(18000000f);
		this.setHarvestLevel(null, 0);
		
		this.drop = (ItemShard)drop;
		
		this.setDefaultState(this.blockState.getBaseState().withProperty(TYPE, EnumType.valueOf(meta)));
		
		for(int i = 0; i < EnumType.values().length; i++) {
			boundingBox[i] = new AxisAlignedBB(0.0f, 0.0f, 0.0f, 1.0f, (i+1)/8f, 1.0f);
		}
	}

	/**
	 * Set Creative Tab
	 * 
	 * @param tab
	 * returns a waystone
	 */
	@Override
	public BlockWaystone setCreativeTab(CreativeTabs tab) {
		super.setCreativeTab(tab);
		return this;
	}
	
	/**
	 * isFullBlock
	 * 
	 * Returns true if any shards have been taken from it. False otherwise
	 * @param state
	 * @return
	 */
	@Override
	public boolean isFullBlock(IBlockState state) {
		return ((this.getMetaFromState(state) + 1) == EnumType.values().length);
		//return false; // for testing purposes only
	}
	
	/**
	 * isFullCube
	 * 
	 * Returns whatever isFullBlock returns
	 * @param state
	 * @return
	 */
	@Override
	public boolean isFullCube(IBlockState state) {
		return this.isFullBlock(state);
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
	/** ***** BLOCK HARVESTING SECTION ***** **/
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return this.drop;
	}
	
	@Override
	public boolean canHarvestBlock(IBlockAccess world, BlockPos pos, EntityPlayer player) {			
		this.canDestroy = (player.getHeldItemMainhand() == null); // if the player isn't holding anything, then an item can be harvested
		return canDestroy;
	}
	
	@Override
	public void onBlockDestroyedByPlayer(World worldIn, BlockPos pos, IBlockState state) {	
		int meta = this.getMetaFromState(state);
		
		if((meta != 0) && (canDestroy)) { // as long as the block isn't on it's last section, and can be destroyed
			worldIn.setBlockState(pos, state.withProperty(TYPE, EnumType.valueOf(meta-1)));
		} else if(!canDestroy) { // if it can't be destroyed
			worldIn.setBlockState(pos, state); // place a block of the same size back in it's place
		}
	}
	
	/** ***** BLOCK BOUNDS SECTION ***** **/
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		int meta = this.getMetaFromState(state);
		
		return boundingBox[meta];
	}
	
	@Override
	public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, Entity entityIn) {
		int meta = this.getMetaFromState(state); // Get the meta of this particular waystone
		super.addCollisionBoxToList(pos, entityBox, collidingBoxes, boundingBox[meta]); // Add this particular waystone block (including it's state) to the collison list
	}
	
	/** ***** BLOCK METADATA SECTION ***** **/
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { TYPE });
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(TYPE, EnumType.values()[meta]);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		EnumType type = (EnumType) state.getValue(TYPE);
		return type.getID();
	}
	
	@Override
	public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list) {
		for(int i = 0; i < EnumType.values().length; i++) {
			list.add(new ItemStack(itemIn, 1, i));
		}
	}
	
	@Override
	public int damageDropped(IBlockState state) {
		return getMetaFromState(state);
	}

	@Override
	public boolean canSilkHarvest(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
		return false;
	}


	public enum EnumType implements IStringSerializable {
		SEVEN(7, "7"),
		SIX(6, "6"),
		FIVE(5, "5"),
		FOUR(4, "4"),
		THREE(3, "3"),
		TWO(2, "2"),
		ONE(1, "1"),
		ZERO(0, "0");

		private int ID;
		private String name;
		
		private static Map<Integer, EnumType> map = new HashMap<Integer, EnumType>();

		static {
			for(EnumType left : EnumType.values()) {
				map.put(left.ID, left);
			}
		}
		
		private EnumType(int ID, String name) {
			this.ID = ID;
			this.name = name;
		}

		@Override
		public String getName() {
			return name;
		}

		public int getID() {
			return ID;
		}

		@Override
		public String toString() {
			return getName();
		}
		
	    public static EnumType valueOf(int id) {
	        return map.get(id);
	    }
	}
}


