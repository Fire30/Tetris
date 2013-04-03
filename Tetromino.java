//TJ Corley
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

public abstract class Tetromino implements Comparable<Tetromino>
{

	public ArrayList<Block> blockArray;// Should be private, set it as public
										// for ease of use.
	private Color myColor;
	private boolean isFinished;
	private double rotationAngle;

	public abstract double getXRotation();

	public abstract double getYRotation();

	public double getRotationAngle()
	{
		return rotationAngle;
	}

	public void setRotationAngle(double angle)
	{
		rotationAngle = angle;
	}

	public double getLowestBlock()
	{
		double total = TetrisPanel.YFRAME;
		for (Block block : blockArray)
		{
			if (block.getYValue() < total)
				total = block.getYValue();
		}
		return total;
	}

	public void setColor(Color c)
	{
		myColor = c;
		for (Block block : blockArray)
		{
			block.setColor(myColor);
		}
	}

	public Color getColor()
	{
		return myColor;
	}

	public boolean isFinished()
	{
		return isFinished;
	}

	public void setFinished(boolean isFinished)
	{
		this.isFinished = isFinished;
	}

	public boolean getCollision(ArrayList<Tetromino> tetrominoArray)
	{
		// Can't use TetrisPanel's getArray()'s, because that will include
		// itself.
		ArrayList<Double> xArray = new ArrayList<Double>();
		ArrayList<Double> yArray = new ArrayList<Double>();

		for (Tetromino tetr : tetrominoArray)
			if (tetr != this)
			{
				for (Block block : tetr.blockArray)
				{
					xArray.add(block.getXValue());
					yArray.add(block.getYValue());
				}
			}
		for (Block block : blockArray)
		{
			for (int i = 0; i < xArray.size(); i++)
			{
				if ((xArray.get(i) == block.getXValue())
						&& (yArray.get(i) == (block.getYValue() + 40)))
				{
					return true;
				}
			}

		}
		for (Block block : blockArray)
			if (block.getYValue() == 760)
				return true;
		return false;
	}

	public boolean sideCollision(ArrayList<Double> xArray,
			ArrayList<Double> yArray)
	{
		for (Block block : blockArray)
		{
			for (int i = 0; i < xArray.size(); i++)
			{
				if ((xArray.get(i) == block.getXValue())
						&& (yArray.get(i) == (block.getYValue())))
				{
					return true;
				}
			}

		}
		return false;
	}

	public void doMoveDown()
	{
		for (Block block : blockArray)
		{
			block.setYValue(block.getYValue() + 40);
		}

	}

	public void doMoveLeft()
	{
		for (Block block : blockArray)
		{
			block.setXValue(block.getXValue() - 40);
		}

	}

	public void doMoveRight()
	{
		for (Block block : blockArray)
		{
			block.setXValue(block.getXValue() + 40);
		}
	}

	// Need to clean up, and make all the moving decisions in each individual
	// method, such as doMoveDown(), instead of in this method.

	public void draw(Graphics2D g, int rotation)
	{

		if (!isFinished())
		{
			setRotationAngle(getRotationAngle() + rotation);

			double xRotation = getXRotation();
			double yRotation = getYRotation();
			boolean canDraw = true;
			boolean leftTurn = false;

			for (Block block : blockArray)
			{
				if (rotation > 0)
				{
					// trig rotation stuff
					// since 90 degree rotation
					// sin(theta) = 1 and cos(theta) = 0

					double newX = (yRotation - block.getYValue()) + xRotation;
					double newY = -xRotation + block.getXValue() + yRotation;

					block.setXValue(newX);
					block.setYValue(newY);
				}
				if (block.getXValue() < 0)
					canDraw = false;
				else if ((block.getXValue() + 40) > TetrisPanel.XFRAME)
				{
					canDraw = false;
					leftTurn = true;
				}
				block.draw(g);
			}
			if (!canDraw)
			{
				g.setColor(TetrisPanel.BACKGROUND);
				g.fillRect(0, 0, TetrisPanel.XFRAME, TetrisPanel.YFRAME);
				if (!leftTurn)
					doMoveRight();
				else
					doMoveLeft();
				draw(g, 0);
			}
			if (getRotationAngle() == 360)
				setRotationAngle(0);
		}
	}

	@Override
	public int compareTo(Tetromino tetr)
	{
		return ((int) (tetr.getLowestBlock() - getLowestBlock()));
	}
}
