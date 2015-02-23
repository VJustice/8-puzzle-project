package com.board;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Collections;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.handlers.ButtonsHandler;

public class GameBoard extends JPanel implements Runnable {
	private static final long serialVersionUID = 1L;

	private static final int NUM_ROWS = 3;
	private static final int NUM_COLUMNS = 3;

	private ButtonsHandler button_handler;

	private LinkedList<JButton> current_buttons_list;

	public GameBoard() {
		button_handler = new ButtonsHandler();
		current_buttons_list = new LinkedList<JButton>();
		this.setLayout(new GridLayout(NUM_ROWS, NUM_COLUMNS));
		initPanelGUI();
		colorButtons();
		Thread thread = new Thread(this);
		thread.start();
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
		for (JButton button : current_buttons_list) {
			this.add(button);
		}
	}

	@Override
	public void run() {
	}
}
