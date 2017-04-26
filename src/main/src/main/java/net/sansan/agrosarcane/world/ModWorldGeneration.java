package net.sansan.agrosarcane.world;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenIceSpike;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.IWorldGenerator;
import net.sansan.agrosarcane.block.ModBlocks;
import net.sansan.agrosarcane.structures.BlockMound;

import java.util.Random;

public class ModWorldGeneration implements IWorldGenerator {

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world,
			IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
		
		int blockX = chunkX * 16;
		int blockZ = chunkZ * 16;
		
		switch(world.provider.getDimension()) {
		case -1: 
			generateNether(world, random, blockX, blockZ, chunkGenerator, chunkProvider);
			break;
		case 0:
			generateOverworld(world, random, blockX, blockZ, chunkGenerator, chunkProvider);
			break;
		case 1:
			generateEnd(world, random, blockX, blockZ, chunkGenerator, chunkProvider);
			break;
		}
		
	}
	
	/** Generate the Nether **/
	private void generateNether(World world, Random rand, int blockX, int blockZ, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
		// Temporarily blank
	}
	
	/** Generate the Overworld **/
	private void generateOverworld(World world, Random rand, int blockX, int blockZ, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
		
		// Ideal values: 
		// 50% to spawn a mound,
		// Mounds at least 4 chunks apart,
		// Maximum of 5 mounds within the chunks
		
		/*if(rand.nextInt(9) < 5) { // 50% chance to spawn a mound
			generateMounds(ModBlocks.blockWaystone.getDefaultState(), world, rand, blockX * 4, blockZ * 4, rand.nextInt(4));
		}*/
		// For Mound Testing
		if(rand.nextInt(10) < 3) {
			generateMounds(ModBlocks.waystone.getDefaultState(), world, rand, blockX, blockZ, 2);
		}
	}
	
	/** Generate the End **/
	private void generateEnd(World world, Random rand, int blockX, int blockZ, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
		// Temporarily blank
	}
	
	/** HELPER METHODS **/
	// generate rock mounds
	private void generateMounds(IBlockState block, World world, Random random, int x, int z, int chances) {
		
		for(int i = 0; i < chances; i++) {
			x = x + random.nextInt(16);
			z = z + random.nextInt(16);
			int minY = groundLvl(world, x, z);
			//The central base block
			BlockPos pos = new BlockPos(x, minY, z);
			
			// Create the Mound
			new BlockMound(world, pos, block).createMound();
		}
	}
	
	private int groundLvl(World world, int x, int z) {
		int y = 255; // Start at the world height
		boolean foundGround = false;
		
		while(!foundGround && y-- >= 0) {
			Block blockAt = world.getBlockState(new BlockPos(x, y, z)).getBlock();
			foundGround = ((blockAt == Blocks.DIRT) || 
						   (blockAt == Blocks.GRASS_PATH) || 
						   (blockAt == Blocks.STONE) ||
						   (blockAt == Blocks.SAND)); // This will set found ground to TRUE if ANY of these are found
			
			if((blockAt == Blocks.LEAVES) ||
				(blockAt == Blocks.LEAVES2) ||
				(blockAt == Blocks.WATER) ||
				(blockAt == Blocks.LAVA)) { 
					y = -1; // Prevents an origin block from being created directly under leaves/water/lava
					// -1 breaks it out of the loop so the method thinks no ground exists there
			}
		}
		return y;
	}
}
