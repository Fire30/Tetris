// TJ Corley
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JPanel;
import javax.swing.Timer;

//Need to break down, too many lines of code.

public class TetrisPanel extends JPanel
{

	public static final int XFRAME = 400;
	public static final int YFRAME = 800;
	public static final Color BACKGROUND = Color.BLUE;
	private BufferedImage myImage;
	private Graphics2D myBuffer;
	private ArrayList<Tetromino> tetrominoArray;
	private Tetromino activeTetromino;
	private Timer t;

	public TetrisPanel()
	{

		myImage = new BufferedImage(XFRAME, YFRAME, BufferedImage.TYPE_INT_RGB);
		myBuffer = (Graphics2D) myImage.getGraphics();
		myBuffer.setColor(BACKGROUND);
		myBuffer.fillRect(0, 0, XFRAME, YFRAME);

		activeTetromino = spawnRandomTetromino();
		activeTetromino.draw(myBuffer, 0);

		tetrominoArray = new ArrayList<Tetromino>();

		t = new Timer(1000, new Listener());
		t.start();

		addKeyListener(new Key());
		setFocusable(true);

	}

	private class Listener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			activeTetromino.doMoveDown();
			doCollisionChecksAndPaint(0);
		}
	}

	private class Key extends KeyAdapter
	{
		@Override
		public void keyPressed(KeyEvent e)
		{
			int rotation = 0;
			if (e.getKeyCode() == KeyEvent.VK_DOWN)
				activeTetromino.doMoveDown();
			if (e.getKeyCode() == KeyEvent.VK_LEFT)
			{
				activeTetromino.doMoveLeft();
				if (activeTetromino.sideCollision(getXArray(), getYArray()))
					activeTetromino.doMoveRight();
			}
			if (e.getKeyCode() == KeyEvent.VK_RIGHT)
				activeTetromino.doMoveRight();
			{
				if (activeTetromino.sideCollision(getXArray(), getYArray()))
					activeTetromino.doMoveLeft();
			}
			if (e.getKeyCode() == KeyEvent.VK_UP)
				rotation = 90;
			doCollisionChecksAndPaint(rotation);

		}
	}

	public void doCollisionChecksAndPaint(int rotation)
	{
		if (activeTetromino.getCollision(tetrominoArray))
		{
			tetrominoArray.add(activeTetromino);
			checkExplosion();
			activeTetromino = spawnRandomTetromino();
		} else
		{

			myBuffer.setColor(BACKGROUND);
			myBuffer.fillRect(0, 0, XFRAME, YFRAME);
			for (Tetromino tetr : tetrominoArray)
				tetr.draw(myBuffer, 0);
		}
		activeTetromino.draw(myBuffer, rotation);
		repaint();
	}

	@Override
	public void paintComponent(Graphics g)
	{
		g.drawImage(myImage, 0, 0, getWidth(), getHeight(), null);
	}

	public void checkExplosion()
	{
		ArrayList<Block> delArray = new ArrayList<Block>();

		ArrayList<Double> yArray = getYArray();
		Collections.sort(yArray);
		Collections.reverse(yArray);
		for (Double y : yArray)
		{
			int freq = Collections.frequency(getYArray(), y);
			if (freq >= 10)
			{
				for (Tetromino tetr : tetrominoArray)
				{
					for (Block block : tetr.blockArray)
					{
						if (block.getYValue() == y)
							delArray.add(block);
					}
				}
				for (Block delBlock : delArray)
				{
					for (int k = 0; k < tetrominoArray.size(); k++)
					{
						tetrominoArray.get(k).blockArray.remove(delBlock);
					}
				}
				Collections.sort(tetrominoArray);
				createPartialTetrominos();
				Collections.sort(tetrominoArray);
				for (int a = 0; a < tetrominoArray.size(); a++)
				{
					Tetromino tetr = tetrominoArray.get(a);
					if (tetr.blockArray.isEmpty())
					{
						tetrominoArray.remove(tetr);
					}
					System.out.println(tetr.blockArray);
					if (!tetr.getCollision(tetrominoArray))
					{
						tetr.doMoveDown();
						//checkExplosion();
					}
				}
			}

		}
		//System.out.println(tetrominoArray);

	}

	public void createPartialTetrominos()
	{
		for (int i = 0; i < tetrominoArray.size(); i++)
		{
			Tetromino tetr = tetrominoArray.get(i);
			for (int j = 0; j < tetr.blockArray.size(); j++)
			{
				Block block = tetr.blockArray.get(j);
				boolean isSeperate = false;
				for (int k = 0; k < tetr.blockArray.size(); k++)
				{
					Block otherBlock = tetr.blockArray.get(k);
					if (block != otherBlock)
					{
						// Checks to see if there is another block next to
						// itself
						boolean down = (block.getXValue() == otherBlock
								.getXValue())
								&& (block.getYValue() == (otherBlock
										.getYValue() - 40));
						boolean up = (block.getXValue() == otherBlock
								.getXValue())
								&& (block.getYValue() == (otherBlock
										.getYValue() + 40));
						boolean left = (block.getXValue() == (otherBlock
								.getXValue() + 40))
								&& (block.getYValue() == otherBlock.getYValue());
						boolean right = (block.getXValue() == (otherBlock
								.getXValue() - 40))
								&& (block.getYValue() == otherBlock.getYValue());
						if (!up && !down && !left && !right)
						{
							isSeperate = true;
						}
					}
				}
				if (isSeperate)
				{
					tetrominoArray.add(new PartialTetromino(block));
					tetr.blockArray.remove(block);
				}

			}
		}
	}

	// Should maybe make these static so I can access them from other classes?
	public ArrayList<Double> getXArray()
	{
		ArrayList<Double> xArray = new ArrayList<Double>();

		for (Tetromino tetr : tetrominoArray)
		{
			for (Block block : tetr.blockArray)
			{
				xArray.add(block.getXValue());
			}
		}
		return xArray;
	}

	public ArrayList<Double> getYArray()
	{
		ArrayList<Double> yArray = new ArrayList<Double>();

		for (Tetromino tetr : tetrominoArray)
		{
			for (Block block : tetr.blockArray)
			{
				yArray.add(block.getYValue());
			}
		}
		return yArray;
	}

	public static Tetromino spawnRandomTetromino()
	{
		double rand = Math.random();
		if (rand > .86)
			return new SquareTetromino();
		else if (rand > .72)
			return new LineTetromino();
		else if (rand > .58)
			return new RightLTetromino();
		else if (rand > .44)
			return new LeftLTetromino();
		else if (rand > .30)
			return new LeftZigZagTetromino();
		else if (rand > .15)
			return new RightZigZagTetromino();
		else
			return new IntersectTetromino();

	}
}