package net.sansan.agrosarcane.item;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.sansan.agrosarcane.Agros_Arcane;

public class ItemBase extends Item implements ItemModelProvider {

	protected String name;
	
	public ItemBase(String name) {
		this.name = name;
		setUnlocalizedName(name);
		setRegistryName(name);
		
		setCreativeTab(Agros_Arcane.creativeTab);
	}
	
	@Override
	public void registerItemModel(Item item) {
		Agros_Arcane.proxy.registerItemRenderer(this, 0, name);
	}
	
	@Override
	public ItemBase setCreativeTab(CreativeTabs tab) {
		super.setCreativeTab(tab);
		return this;
	}

	@Override
	public void registerItemModel(Item itemBlock, Block block) {
		// TODO Auto-generated method stub
		
	}
}
