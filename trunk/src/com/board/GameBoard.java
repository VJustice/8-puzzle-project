package com.board;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

public class GameBoard extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private static final int NUM_ROWS = 3;
	private static final int NUM_COLUMNS = 3;
	

	public GameBoard() {
		this.setLayout(new GridLayout(NUM_ROWS, NUM_COLUMNS));
		for(int i = 0; i < NUM_COLUMNS*NUM_ROWS; i++) {
			this.add(new JButton("" + i));
		}
	}
}
