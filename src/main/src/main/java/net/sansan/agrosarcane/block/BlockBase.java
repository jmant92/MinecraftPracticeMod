package net.sansan.agrosarcane.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.sansan.agrosarcane.Agros_Arcane;
import net.sansan.agrosarcane.item.ItemModelProvider;

public class BlockBase extends Block implements ItemModelProvider{
	
	protected String name;
	
	public BlockBase(Material material, String name) {
		super(material);
		
		this.name = name;
		
		setUnlocalizedName(name);
		setRegistryName(name);
		
		setCreativeTab(Agros_Arcane.creativeTab);
	}
	
	@Override
	public void registerItemModel(Item itemBlock, Block block) {
		
		if(block instanceof BlockWaystone)
		for(int meta = 0; meta <= block.getMetaFromState(block.getDefaultState()); meta++ )
			Agros_Arcane.proxy.registerItemRenderer(itemBlock, meta, name);
	}
	
	@Override
	public BlockBase setCreativeTab(CreativeTabs tab) {
		super.setCreativeTab(tab);
		return this;
	}

	@Override
	public void registerItemModel(Item item) {
		// TODO Auto-generated method stub
		
	}


}
