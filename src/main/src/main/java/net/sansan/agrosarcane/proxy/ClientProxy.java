package net.sansan.agrosarcane.proxy;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.sansan.agrosarcane.Agros_Arcane;

public class ClientProxy extends CommonProxy {
	
	@Override
	public void registerItemRenderer(Item item, int meta, String id) {
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(Agros_Arcane.modId + ":" + id, "inventory"));
	}
	
	@Override
	public String localize(String unlocalized, Object... args) {
		return I18n.format(unlocalized, args);
	}
}
