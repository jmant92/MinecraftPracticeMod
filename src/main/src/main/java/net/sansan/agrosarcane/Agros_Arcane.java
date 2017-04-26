package net.sansan.agrosarcane;

import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.sansan.agrosarcane.block.ModBlocks;
import net.sansan.agrosarcane.client.AgrosArcaneTab;
import net.sansan.agrosarcane.item.ModItems;
import net.sansan.agrosarcane.proxy.CommonProxy;
import net.sansan.agrosarcane.world.ModWorldGeneration;

@Mod(modid = Agros_Arcane.modId,
		name = Agros_Arcane.name,
		version = Agros_Arcane.version,
		acceptedMinecraftVersions = "[1.10.2]")
public class Agros_Arcane {

	// Mod Info
	public static final String modId = "agrosarcane";
	public static final String name = "Agros Arcane";
	public static final String version = "1.0.0";
	
	// Creative Tabs
	public static final AgrosArcaneTab creativeTab = new AgrosArcaneTab();
	
	// Mod Items
	
	
	@Mod.Instance(modId)
	public static Agros_Arcane instance;
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		System.out.println(name + " is loading!");
		
		ModItems.init();
		ModBlocks.init();
		
		GameRegistry.registerWorldGenerator(new ModWorldGeneration(), 3);
	}
	
	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
	}
	
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		
	}
	
	@SidedProxy(serverSide = "net.sansan.agrosarcane.proxy.CommonProxy",
				clientSide = "net.sansan.agrosarcane.proxy.ClientProxy")
	public static CommonProxy proxy;
}
