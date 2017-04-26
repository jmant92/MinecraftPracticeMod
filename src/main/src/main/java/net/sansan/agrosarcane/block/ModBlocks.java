package net.sansan.agrosarcane.block;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.sansan.agrosarcane.block.BlockWaystone.EnumType;
import net.sansan.agrosarcane.item.ItemModelProvider;
import net.sansan.agrosarcane.item.ItemOreDict;
import net.sansan.agrosarcane.item.ModItems;

public class ModBlocks {
	
	public static Block waystone;
	
	public static void init() {
		waystone = register(new BlockWaystone("blockWaystone", EnumType.values().length-1, ModItems.shardWaystone)); // add Waystone Block
	}
	
	// Return T. <T extends Block> means: T is something that extends block
	private  static <T extends Block> T register(T block, ItemBlock itemBlock) {
		GameRegistry.register(block);
		if(itemBlock != null) {
			GameRegistry.register(itemBlock);
			
			if(block instanceof ItemModelProvider) {
				((ItemModelProvider)block).registerItemModel(itemBlock, block);
			}
			if (block instanceof ItemOreDict) {
				((ItemOreDict)block).initOreDict();
			}
			if (itemBlock instanceof ItemOreDict) {
				((ItemOreDict)itemBlock).initOreDict();
			}
			if (block instanceof BlockTileEntity) {
				GameRegistry.registerTileEntity(((BlockTileEntity<?>)block).getTileEntityClass(), block.getRegistryName().toString());
			}
		}
		
		return block;
	}
	
	private static <T extends Block> T register(T block) {
		ItemBlock itemBlock = new ItemBlock(block);
		itemBlock.setRegistryName(block.getRegistryName());
		return register(block, itemBlock);
	}
}
