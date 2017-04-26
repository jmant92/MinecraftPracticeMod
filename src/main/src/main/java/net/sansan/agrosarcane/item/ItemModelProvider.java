package net.sansan.agrosarcane.item;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

public interface ItemModelProvider {
	
	void registerItemModel(Item item);

	void registerItemModel(Item itemBlock, Block block);
	
}
