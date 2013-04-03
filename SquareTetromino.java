import java.awt.Color;
import java.util.ArrayList;

//TJ Corley

public class SquareTetromino extends Tetromino
{
	public SquareTetromino()
	{

		blockArray = new ArrayList<Block>();
		blockArray.add(new Block(160, 40));
		blockArray.add(new Block(200, 40));
		blockArray.add(new Block(160, 80));
		blockArray.add(new Block(200, 80));
		setColor(Color.MAGENTA);

	}

	@Override
	public double getXRotation()
	{
		double lowest = 9001;
		double highest = 0;
		for (Block block : blockArray)
		{
			if (block.getXValue() > highest)
				highest = block.getXValue();
			if (block.getXValue() < lowest)
				lowest = block.getXValue();

		}
		return (highest + lowest) / 2;
	}

	@Override
	public double getYRotation()
	{
		double lowest = 9001;
		double highest = 0;
		for (Block block : blockArray)
		{
			if (block.getYValue() > highest)
				highest = block.getYValue();
			if (block.getYValue() < lowest)
				lowest = block.getYValue();
		}
		return (highest + lowest) / 2;
	}
}