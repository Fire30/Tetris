//TJ Corley
import java.awt.Color;
import java.awt.Graphics2D;

public class Block implements Comparable<Block>
{
	private int xWidth;
	private int yWidth;
	private double xValue;
	private double yValue;
	private Color myColor;

	public Block()
	{
		xWidth = 40;
		yWidth = 40;
		xValue = 0;
		yValue = 0;
		myColor = Color.RED;
	}

	public Block(int myXValue, int myYValue)
	{
		xWidth = 40;
		yWidth = 40;
		xValue = myXValue;
		yValue = myYValue;

	}

	public void setColor(Color c)
	{
		myColor = c;
	}

	public Color getColor()
	{
		return myColor;
	}

	public void setXValue(double x)
	{
		xValue = x;
	}

	public double getXValue()
	{
		return xValue;
	}

	public void setYValue(double y)
	{
		yValue = y;
	}

	public double getYValue()
	{
		return yValue;
	}

	public void draw(Graphics2D g)
	{
		g.setColor(myColor);
		g.fillRect((int) (getXValue()), (int) (getYValue()), xWidth, yWidth);
		g.setColor(Color.BLACK);
		g.drawRect((int) (getXValue()), (int) (getYValue()), xWidth, yWidth);
	}

	@Override
	public int compareTo(Block b)
	{
		return ((int) (b.getYValue() - yValue));
	}
}