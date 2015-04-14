package com.board;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.util.Collections;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.algorithms.AStar;
import com.algorithms.BreadthFirstSearch;
import com.genetics.GeneticAlgorithm;
import com.logger.LoggerDebugger;

public class GameBoard extends JPanel {
	private static final long serialVersionUID = 1L;

	private static final int NUM_ROWS = 3;
	private static final int NUM_COLUMNS = 3;

	private Board board;

	private LinkedList<JButton> current_buttons_list = new LinkedList<JButton>();
	private LinkedList<String> plays = new LinkedList<String>();

	private String solution = "012345678";
	private String current_data = "";

	private LoggerDebugger logger;

	public GameBoard(Board board, LoggerDebugger logger) {
		this.board = board;
		this.logger = logger;
		this.setLayout(new GridLayout(NUM_ROWS, NUM_COLUMNS));
		initPanelGUI();
		this.repaint();
		this.validate();
	}

	private void generateInitialState(int max_depth) {
		String c_list = solution;
		int last_move = 0;
		for (int i = 0; i < max_depth; i++) {
			int zn = c_list.indexOf('0');
			int rand = (int) (Math.random() * 4);
			while (true) {
				if (rand == 0) {
					if ((zn - 1 >= 0 && zn - 1 != 2 && zn - 1 != 5)
							&& last_move != 1) {
						Collections.swap(current_buttons_list, zn, zn - 1);
						last_move = 2;
						break;
					}
				} else if (rand == 1) {
					if ((zn + 1 != 3 && zn + 1 != 6 && zn + 1 <= 8)
							&& last_move != 2) {
						Collections.swap(current_buttons_list, zn, zn + 1);
						last_move = 1;
						break;
					}
				} else if (rand == 2) {
					if (zn - 3 >= 0 && last_move != 3) {
						Collections.swap(current_buttons_list, zn, zn - 3);
						last_move = 4;
						break;
					}
				} else if (rand == 3) {
					if (zn + 3 <= 8 && last_move != 4) {
						Collections.swap(current_buttons_list, zn, zn + 3);
						last_move = 3;
						break;
					}
				}
				rand = (int) (Math.random() * 4);
			}
			c_list = "";
			for (int a = 0; a < current_buttons_list.size(); a++) {
				c_list += current_buttons_list.get(a).getText();
			}
		}
	}

	private void initPanelGUI() {
		for (int i = 0; i < NUM_COLUMNS * NUM_ROWS; i++) {
			JButton aux_button = new JButton("" + i);
			aux_button.setEnabled(false);
			current_buttons_list.add(aux_button);
		}
		generateInitialState(17);
		for (JButton button : current_buttons_list) {
			this.add(button);
		}
		for (int i = 0; i < current_buttons_list.size(); i++) {
			current_data += current_buttons_list.get(i).getText();
		}
	}

	public void makeMoves() {
		if (plays.isEmpty()) {
			JOptionPane.showMessageDialog(null, "No More Moves");
		} else {
			String temp = plays.removeLast();
			for (int i = 0; i < current_buttons_list.size(); i++) {
				current_buttons_list.get(i).setText(temp.toCharArray()[i] + "");
			}
			board.getPuzzle_movemment_log().append(
					"Moved Tiles: " + temp + "\n");
			logger.saveLog("Tiles have been moved to: " + temp, "info");
			repaint();
		}
	}

	public void startAlgorithm(String algorithm, String heuristic) {
		switch (algorithm) {
		case "BreadthFirstSearch":
			BreadthFirstSearch bfs = new BreadthFirstSearch(this, current_data,
					solution, logger);
			bfs.searchAlgorithm();
			plays.clear();
			plays = bfs.getNew_node_data_list();
			break;
		case "A*":
			AStar a_star = new AStar(this, current_data, solution, heuristic, logger);
			Thread t = new Thread(a_star);
			t.start();
			plays.clear();
			plays = a_star.getNew_node_data_list();
			break;
		case "Genetic":
			GeneticAlgorithm GA = new GeneticAlgorithm(this, current_data,
					solution, board.getHeuristics_array(), logger);
			GA.init();
			break;
		default:
			break;
		}
	}

	public void restart() {
		plays.clear();
		board.getPuzzle_movemment_log().setText("");
	}

	@Override
	protected void paintComponent(Graphics g) {
		for (JButton current_button : current_buttons_list) {
			if (!current_button.getText().equals("" + 0)) {
				current_button.setFont(new Font("Century Gothic", Font.PLAIN,
						40));
				current_button.setForeground(Color.WHITE);
				current_button.setBackground(Color.BLACK);
			} else {
				current_button
						.setFont(new Font("Century Gothic", Font.PLAIN, 0));
				current_button.setBackground(Color.WHITE);
			}
		}
	}

	public Board getBoard() {
		return board;
	}
}
