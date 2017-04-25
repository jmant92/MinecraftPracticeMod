package net.sansan.agrosarcane.item;

import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModItems {
	
	// Materials
	
	// Tools
	
	// Armor
	
	// Misc Items
	public static Item shardWaystone;
	
	public static void init() {
		shardWaystone = register(new ItemShard("shardWaystone"));
	}
	
	private static <T extends Item> T register(T item) {
		GameRegistry.register(item);
		
		if(item instanceof ItemModelProvider) {
			((ItemModelProvider)item).registerItemModel(item);
		}
		if (item instanceof ItemOreDict) {
			((ItemOreDict)item).initOreDict();
		}
		
		return item;
	}
}
