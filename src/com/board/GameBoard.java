package com.board;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Collections;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.algorithms.BreadthFirstSearch;
import com.handlers.ButtonsHandler;

public class GameBoard extends JPanel {
	private static final long serialVersionUID = 1L;

	private static final int NUM_ROWS = 3;
	private static final int NUM_COLUMNS = 3;

	private ButtonsHandler button_handler;

	private LinkedList<JButton> current_buttons_list;

	private String[] solution = { "0", "1", "2", "3", "4", "5", "6", "7", "8" };
	private String[] current_data = new String[solution.length];

	public GameBoard() {
		button_handler = new ButtonsHandler();
		current_buttons_list = new LinkedList<JButton>();
		this.setLayout(new GridLayout(NUM_ROWS, NUM_COLUMNS));
		initPanelGUI();
		colorButtons();
	}

	private void colorButtons() {
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

	private void initPanelGUI() {
		for (int i = 0; i < NUM_COLUMNS * NUM_ROWS; i++) {
			JButton aux_button = new JButton("" + i);
			aux_button.setEnabled(false);
			aux_button.setActionCommand("" + i);
			aux_button.addActionListener(button_handler);
			current_buttons_list.add(aux_button);
		}
		Collections.shuffle(current_buttons_list);
		// Collections.swap(current_buttons_list, 0, 1);
		// Collections.swap(current_buttons_list, 1, 4);
		// Collections.swap(current_buttons_list, 4, 7);
		// Collections.swap(current_buttons_list, 7, 8);
		for (JButton button : current_buttons_list) {
			this.add(button);
		}
		for (int i = 0; i < current_buttons_list.size(); i++) {
			current_data[i] = current_buttons_list.get(i).getText();
		}
	}

	public void startAlgorithm(String algorithm) {
		switch (algorithm) {
		case "BreadthFirstSearch":
			BreadthFirstSearch bfs = new BreadthFirstSearch(current_data,
					solution);
			bfs.getTreeStructure();
			break;
		default:
			break;
		}
	}

}
