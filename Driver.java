//TJ Corley

//TODO:
//Really buggy, clearing the blocks is still pretty far off. 
//Sometimes the pink block won't fall down as a partial... hmm 
//I also should use constants instead of just hardcoding values in.
//Need to also make ending for the game, right now it just spazzes out.

import javax.swing.JFrame;

public class Driver
{
	public static void main(String[] args)
	{
		JFrame frame = new JFrame("Tetris");
		frame.setSize(TetrisPanel.XFRAME, TetrisPanel.YFRAME);
		frame.setLocation(0, 0);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(new TetrisPanel());
		frame.setVisible(true);
	}
}