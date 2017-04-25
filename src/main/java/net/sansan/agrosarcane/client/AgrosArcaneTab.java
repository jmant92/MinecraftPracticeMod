package net.sansan.agrosarcane.client;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.sansan.agrosarcane.Agros_Arcane;
import net.sansan.agrosarcane.item.ModItems;

public class AgrosArcaneTab extends CreativeTabs {

	public AgrosArcaneTab() {
		super(Agros_Arcane.modId);
	}
	
	@Override
	public Item getTabIconItem() {
		return ModItems.shardWaystone;
	}
	
	@Override
	public boolean hasSearchBar() {
		return false;
	}
}
