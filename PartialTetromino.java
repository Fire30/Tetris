import java.util.ArrayList;

//TJ Corley

public class PartialTetromino extends Tetromino
{

	public PartialTetromino(Block block)
	{
		blockArray = new ArrayList<Block>();
		blockArray.add(block);
		setColor(block.getColor());
	}

	public PartialTetromino(ArrayList<Block> theBlockArray)
	{
		blockArray = theBlockArray;
		setColor(theBlockArray.get(0).getColor());
	}

	// These will never be called, as it will never be the activeTetromino
	@Override
	public double getXRotation()
	{
		return 0.0;
	}

	@Override
	public double getYRotation()
	{
		return 0.0;
	}
}