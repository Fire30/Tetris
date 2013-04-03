import java.awt.Color;
import java.util.ArrayList;

//TJ Corley

public class LineTetromino extends Tetromino
{

	public LineTetromino()
	{
		blockArray = new ArrayList<Block>();
		for (int i = 0; i < 4; i++)
		{
			blockArray.add(new Block(120 + (40 * i), 40));
		}
		setColor(Color.RED);
	}

	@Override
	public double getXRotation()
	{
		double lowest = 9001;
		double highest = 0;
		int y = 0;

		for (Block block : blockArray)
		{
			if (block.getXValue() > highest)
				highest = block.getXValue();
			if (block.getXValue() < lowest)
				lowest = block.getXValue();

		}
		return ((highest + lowest) / 2) + y;
	}

	@Override
	public double getYRotation()
	{
		double lowest = 9001;
		double highest = 0;
		int y = -20;
		if ((((getRotationAngle() / 90)) % 2) != 0)
			y = 20;

		for (Block block : blockArray)
		{
			if (block.getYValue() > highest)
				highest = block.getYValue();
			if (block.getYValue() < lowest)
				lowest = block.getYValue();
		}
		return ((highest + lowest) / 2) + y;
	}
}