package net.sansan.agrosarcane.structures;

import java.util.LinkedList;
import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.sansan.agrosarcane.block.ModBlocks;

public class BlockMound {

	private World world;

	private BlockPos pos;
	private BlockPos origin;

	private IBlockState moundBlock;

	private int xDiam;
	private int yDiam;
	private int zDiam;
	
	//private LinkedList<BlockPos> blockPos;

	public BlockMound(World world, BlockPos pos, IBlockState moundBlock) {
		this.world = world;
		origin = pos;
		this.moundBlock = moundBlock;

		xDiam = 2 + new Random().nextInt(4);
		yDiam = 2 + new Random().nextInt(8);
		zDiam = 2 + new Random().nextInt(4);

		this.pos = pos.add(xDiam, yDiam, zDiam); // initialize the origin block at a top corner
		
		//blockPos = new LinkedList<BlockPos>();
		//blockPos.add(origin); // This ensures that the first block in the list is the origin. This will serve as a reference point for the entire mound
		
		// Debug
		//System.out.println("The origin of this mound is: " + origin);
	}

	public void createMound() {
		this.moundBase();		
		
		//blockPos.removeLast(); // ensures that the origin is removed from the bottom of the list
		// (Because it was already added at the beginning)
		
		int numBlocks = (xDiam*yDiam + xDiam*zDiam + yDiam*zDiam)/2;
		editMound(numBlocks);
		
		/*if(moundBlock.getBlock().equals(ModBlocks.blockWaystone)) {
			//ModBlocks.blockWaystone.receiveNeighbors(blockPos);
			ModBlocks.blockWaystone.setOrigin(origin);
			
		}*/
		
		/*for(BlockPos position : blockPos) {
			 // Should tell each of the blocks in a mound where the origin of that mound is
		}*/
	}


	/** Helper Methods **/
	public void moundBase() {

		world.setBlockState(pos, moundBlock); // set a block down at pos
		//this.blockPos.add(pos);
		
		if((pos.getZ() - origin.getZ() == 0) && (pos.getY() - origin.getY() == 0) && (pos.getX() - origin.getX() == 0)) { // if pos is back at its initial z position
			return; // we're done, so just return the pos
		} else if((pos.getY() - origin.getY() == 0) && (pos.getX() - origin.getX() == 0)) { // if pos is back at its initial y position
			pos = pos.add(xDiam, yDiam, -1);// bring the depth over one and reset the height
		} else if(pos.getX() - origin.getX() == 0) { // if pos is back at its initial x position
			pos = pos.add(xDiam, -1, 0);  // bring the height down one and reset the row
		} else { // otherwise
			pos = pos.add(-1, 0, 0); // bring the block over one
		}
		
		moundBase(); 
	}

	public void editMound(int numBlocks) {
		
		BlockPos newBlock = origin;

		int startPos = 0;
		boolean lower = new Random().nextBoolean();
		boolean addBlock = new Random().nextBoolean();
		
		int xyzChoice = new Random().nextInt(3);
		boolean xConst = false;
		boolean yConst = false;
		boolean zConst = false;
		
		switch(xyzChoice) {
		case 0:
			xConst = true;
			startPos = xDiam + 1;
			break;
		case 1:
			yConst = true;
			startPos = yDiam + 1;
			break;
		case 2:
			zConst = true;
			startPos = zDiam + 1;
			break;
		}

		// Upper section means going in the positive xyz direction
		// Lower section means going in the negative xyz direction
		if(lower) { // if it's on the lower section
			if(addBlock) { // and we are adding a solid block
				startPos = -1; // set the starting position to be one less than the origin
			} else {
				startPos = 0; // make sure it's starting at the origin
			}
		} else if(!addBlock) { // if it's on the upper section and we are not adding a solid block
			startPos--; // we only need to subtract 1 from the position the block was going to be placed at
		}
		
			// Adds blocks on the upper half addedBlocks times
			if(xConst) { // if an x edge is chosen to be constant
				newBlock = newBlock.add(startPos, new Random().nextInt(yDiam + 1), new Random().nextInt(zDiam + 1));
				// move the x up to the xDiam and add one so it goes on the edge of the mound
				// make the possibility of a y block being chosen anywhere from 0 to the yDiam
				// (it has to be yDiam + 1 because otherwise the block will have a range of yDiam numbers.
				// If set to 3, it will only go up to 2 rather than at the edge of yDiam)
				// same with z.
			} else if(yConst) { // if a y edge is chosen to be constant
				newBlock = newBlock.add(new Random().nextInt(xDiam + 1), startPos, new Random().nextInt(zDiam + 1));
				// same as previous but with known y
			} else if(zConst) { // if a z edge is chosen to be constant
				newBlock = newBlock.add(new Random().nextInt(xDiam + 1), new Random().nextInt(yDiam + 1), startPos);
				// same as previous but with known z
			}
			
		// Used for identifying the blocks in a mound
		if(!newBlock.equals(origin)) { // The origin will NEVER have blocks that aren't part of the base attached to it
			if(!addBlock) { // If we're subtracting a block
				world.setBlockToAir(newBlock); // make a block at newBlock position air
			} else { // otherwise
				world.setBlockState(newBlock, moundBlock);  // make a block at newBlock position moundBlock
				
				
				//this.blockPos.add(newBlock); // only add the position if a block is added
				// the air block positions are already taken care of since they were added earlier.
			}
		}

		if(numBlocks <= 0) { // If we added all the blocks we said we were going to
			return; // we're done
		}
		else {
			editMound(numBlocks-1);
		}

	}
	
	/*public LinkedList<BlockPos> getPosList() {
		return blockPos;
	}*/

}
