package com.board;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Board {
	
	private static final int WIDTH = 600;
	private static final int HEIGHT = 600;

	private JFrame puzzle_frame;
	private JPanel puzzle_panel;
	
	public Board() {
		puzzle_frame = new JFrame("Puzzle");
		puzzle_frame.setSize(WIDTH, HEIGHT);
		puzzle_frame.setResizable(false);
		puzzle_frame.add(fullPanel());
		puzzle_frame.setLocationRelativeTo(null);
		puzzle_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void initGUI() {
		puzzle_frame.setVisible(true);
	}
	
	public JPanel fullPanel() {
		puzzle_panel = new JPanel();
		return puzzle_panel;
	}
}
