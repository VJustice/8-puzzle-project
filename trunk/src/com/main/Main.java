package com.main;

import javax.swing.SwingUtilities;

import com.board.Board;

public class Main {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				Board board = new Board();
				board.initGUI();
			}

		});
	}
}
