package com.board;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

import com.logger.LoggerDebugger;

public class Board {

	private static final int WIDTH = 1000;
	private static final int HEIGHT = 600;
	private static final double VERSION = 1.0;

	private JFrame puzzle_frame;
	private JPanel puzzle_panel;
	private JPanel puzzle_right_panel;
	private JPanel puzzle_right_up_panel;
	private JPanel puzzle_right_down_panel;
	private JPanel aux_panel_one;
	private JPanel aux_panel_two;

	private JTextArea puzzle_movemment_log;
	private JTextArea puzzle_results_log;

	private JComboBox<String> choose_algorithm;
	private JComboBox<String> choose_heuristic;

	private JButton start_algorithm;
	private JButton show_results;
	private JButton reset_puzzle;

	private TitledBorder options_border;
	private TitledBorder logs_border;
	private TitledBorder movemment_border;
	private TitledBorder results_border;

	private String[] algorithms = { "A*", "BreadthFirstSearch", "Genetic" };
	private String[] heuristics_array = { "Manhattan", "Euclidean",
			"Levenshtein" };

	private LoggerDebugger logger = new LoggerDebugger();
	private GameBoard game_board;

	/** Constructor **/
	public Board() {
		puzzle_frame = new JFrame("Client Puzzle (Version " + VERSION + ")");
		game_board = new GameBoard(this, logger);
		puzzle_frame.setSize(WIDTH, HEIGHT);
		puzzle_frame.setResizable(false);
		puzzle_frame.add(fullPanel());
		puzzle_frame.setLocationRelativeTo(null);
		puzzle_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/** Initiates GUI **/
	public void initGUI() {
		puzzle_frame.setVisible(true);
	}

	/** GUI Full Panel **/
	public JPanel fullPanel() {
		puzzle_panel = new JPanel();
		puzzle_panel.setLayout(new BorderLayout());
		puzzle_panel.add(game_board, BorderLayout.CENTER);
		puzzle_panel.add(rightPanel(), BorderLayout.EAST);
		return puzzle_panel;
	}

	/** GUI Right Panel **/
	public JPanel rightPanel() {
		puzzle_right_panel = new JPanel();
		puzzle_right_panel.setLayout(new BorderLayout());
		puzzle_right_panel.setPreferredSize(new Dimension(WIDTH / 3 + 100,
				HEIGHT));
		puzzle_right_panel.add(rightUpPanel(), BorderLayout.NORTH);
		puzzle_right_panel.add(rightDownPanel(), BorderLayout.CENTER);
		return puzzle_right_panel;
	}

	/** GUI Right Up Panel **/
	private JPanel rightUpPanel() {
		puzzle_right_up_panel = new JPanel();
		options_border = new TitledBorder("Options");
		choose_algorithm = new JComboBox<String>();
		choose_heuristic = new JComboBox<String>();
		for (int i = 0; i < algorithms.length; i++) {
			choose_algorithm.addItem(algorithms[i]);
		}
		for (int i = 0; i < heuristics_array.length; i++) {
			choose_heuristic.addItem(heuristics_array[i]);
		}
		start_algorithm = new JButton("Start Algorithm");
		start_algorithm.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				game_board.startAlgorithm(choose_algorithm.getSelectedItem()
						.toString(), choose_heuristic.getSelectedItem()
						.toString());
			}

		});
		show_results = new JButton("Show Results");
		show_results.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				game_board.makeMoves();
			}

		});
		reset_puzzle = new JButton("Clear Visual Log");
		reset_puzzle.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				game_board.restart();

			}
		});
		puzzle_right_up_panel.setLayout(new FlowLayout());
		puzzle_right_up_panel.setPreferredSize(new Dimension(WIDTH / 3,
				(HEIGHT / 5)));
		puzzle_right_up_panel.setBorder(options_border);
		puzzle_right_up_panel.add(choose_algorithm);
		puzzle_right_up_panel.add(start_algorithm);
		puzzle_right_up_panel.add(reset_puzzle);
		puzzle_right_up_panel.add(show_results);
		puzzle_right_up_panel.add(choose_heuristic);
		return puzzle_right_up_panel;
	}

	/** GUI Right Down Panel **/
	private JPanel rightDownPanel() {
		puzzle_right_down_panel = new JPanel();
		logs_border = new TitledBorder("Logs");
		puzzle_right_down_panel.setBorder(logs_border);
		puzzle_right_down_panel.setLayout(new BorderLayout());
		puzzle_right_down_panel.add(auxOne(), BorderLayout.NORTH);
		puzzle_right_down_panel.add(auxTwo(), BorderLayout.CENTER);
		return puzzle_right_down_panel;
	}

	/** GUI Auxiliary Panel One **/
	private JPanel auxOne() {
		aux_panel_one = new JPanel();
		puzzle_movemment_log = new JTextArea();
		movemment_border = new TitledBorder("Movemments Log");
		puzzle_movemment_log.setEditable(false);
		JScrollPane scroll = new JScrollPane(puzzle_movemment_log,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		aux_panel_one.setBorder(movemment_border);
		aux_panel_one.setPreferredSize(new Dimension(WIDTH / 3, HEIGHT / 2));
		aux_panel_one.setLayout(new BorderLayout());
		aux_panel_one.add(scroll, BorderLayout.CENTER);
		return aux_panel_one;
	}

	/** GUI Auxiliary Panel Two **/
	private JPanel auxTwo() {
		aux_panel_two = new JPanel();
		puzzle_results_log = new JTextArea();
		results_border = new TitledBorder("Results Log");
		puzzle_results_log.setEditable(false);
		JScrollPane scroll = new JScrollPane(puzzle_results_log,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		aux_panel_two.setBorder(results_border);
		aux_panel_two.setLayout(new BorderLayout());
		aux_panel_two.add(scroll, BorderLayout.CENTER);
		return aux_panel_two;
	}

	/** Getters **/
	public JTextArea getPuzzle_movemment_log() {
		return puzzle_movemment_log;
	}

	public JTextArea getPuzzle_results_log() {
		return puzzle_results_log;
	}

	public String[] getHeuristics_array() {
		return heuristics_array;
	}

}
