package net.sansan.agrosarcane.item;

import java.util.LinkedList;

import net.minecraft.util.math.BlockPos;

public class ItemShard extends ItemBase {

	private String shardName;
	
	// Shard origin coordinates
	private LinkedList<BlockPos> origins;
	
	public ItemShard(String name) {
		super(name);
		
		this.setMaxStackSize(8);
		this.shardName = name;
		origins = new LinkedList<BlockPos>();
	}
	
	public void addOrigin(BlockPos origin) {
		origins.add(origin);
	}
}
