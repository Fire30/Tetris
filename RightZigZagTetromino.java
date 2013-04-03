import java.awt.Color;
import java.util.ArrayList;

//TJ Corley

public class RightZigZagTetromino extends Tetromino
{

	public RightZigZagTetromino()
	{
		blockArray = new ArrayList<Block>();
		blockArray.add(new Block(160, 0));
		blockArray.add(new Block(200, 0));
		blockArray.add(new Block(200, 40));
		blockArray.add(new Block(240, 40));
		setColor(Color.GREEN);
	}

	@Override
	public double getXRotation()
	{
		double lowest = 9001;
		double highest = 0;
		int y = 0;
		if (getRotationAngle() == 180)
			y = -20;
		if (getRotationAngle() == 360)
			y = 20;
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
		int y = 0;
		if (getRotationAngle() == 90)
			y = 20;
		if (getRotationAngle() == 270)
			y = -20;
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