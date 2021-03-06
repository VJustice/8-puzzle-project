package com.algorithms;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.logging.Level;

import com.board.GameBoard;
import com.logger.LoggerDebugger;

public class AStar implements Runnable {

	private static final int DEPTH = 25;

	private LinkedList<Node> open_queue = new LinkedList<Node>();
	private LinkedList<Node> closed_queue = new LinkedList<Node>();
	private LinkedList<String> nodes_plays_list = new LinkedList<String>();
	private HashMap<Node, Node> nodes_history = new HashMap<Node, Node>();

	private String heuristics;
	private String current_node_data = "";
	private String solution_node = "";
	private String aux_levenshtein = "";

	private long start_time = System.currentTimeMillis();

	private GameBoard game_board;
	private LoggerDebugger logger;

	private int final_time;
	private int final_level;

	public AStar(GameBoard game_board, String current_data, String solution,
			String heuristics, LoggerDebugger logger) {
		this.game_board = game_board;
		this.heuristics = heuristics;
		this.current_node_data = current_data;
		this.solution_node = solution;
		this.logger = logger;
	}

	@Override
	public void run() {
		int f_score = calculate_h_score(current_node_data, heuristics);
		Node root_node = new Node(false, current_node_data, 0, f_score);
		open_queue.add(root_node);
		while (!open_queue.isEmpty()) {
			sortList(open_queue);
			Node current_node = open_queue.removeFirst();
			print("Chosen " + current_node, "MOV");
			closed_queue.add(current_node);
			if (current_node.getCurrent_node_data().equals(solution_node)) {
				double sec = (int) (System.currentTimeMillis() - start_time);
				sec = sec * (0.001);
				print("Solution on level: " + current_node.getTree_level()
						+ " " + current_node + "; \n" + heuristics + ": " + sec
						+ "sec.", "RES");
				final_level = current_node.getTree_level();
				final_time = (int) (System.currentTimeMillis() - start_time);
				getFinalPlays(current_node);
				break;
			}
			if (current_node.getTree_level() == DEPTH) {
				print(heuristics + ": Solution not Found!!", "RES");
				break;
			}
			checkNeighbours(current_node);
		}

	}

	private LinkedList<Node> sortList(LinkedList<Node> list) {
		for (int i = 0; i < list.size() - 1; i++) {
			for (int j = i + 1; j < list.size(); j++) {
				if ((list.get(i).getEstimated_score() >= list.get(j)
						.getEstimated_score())) {
					Node temp_node = list.get(j);
					list.set(j, list.get(i));
					list.set(i, temp_node);
				}
			}
		}
		return list;
	}

	private void checkNeighbours(Node aux_node) {
		LinkedList<String> aux_list_temp = new LinkedList<String>();
		String aux_node_data = aux_node.getCurrent_node_data();
		String next_state = "";
		int zero_index = aux_node_data.indexOf("0");
		aux_list_temp.clear();
		// UP
		if (zero_index > 2) {
			next_state = aux_node_data.substring(0, zero_index - 3) + "0"
					+ aux_node_data.substring(zero_index - 2, zero_index)
					+ aux_node_data.charAt(zero_index - 3)
					+ aux_node_data.substring(zero_index + 1);
			aux_list_temp.add(next_state);
		}
		// DOWN
		if (zero_index < 6) {
			next_state = aux_node_data.substring(0, zero_index)
					+ aux_node_data.substring(zero_index + 3, zero_index + 4)
					+ aux_node_data.substring(zero_index + 1, zero_index + 3)
					+ "0" + aux_node_data.substring(zero_index + 4);
			aux_list_temp.add(next_state);
		}
		// LEFT
		if (zero_index != 0 && zero_index != 3 && zero_index != 6) {
			next_state = aux_node_data.substring(0, zero_index - 1) + "0"
					+ aux_node_data.charAt(zero_index - 1)
					+ aux_node_data.substring(zero_index + 1);
			aux_list_temp.add(next_state);
		}
		// RIGHT
		if (zero_index != 2 && zero_index != 5 && zero_index != 8) {
			next_state = aux_node_data.substring(0, zero_index)
					+ aux_node_data.charAt(zero_index + 1) + "0"
					+ aux_node_data.substring(zero_index + 2);
			aux_list_temp.add(next_state);
		}
		for (int i = 0; i < aux_list_temp.size(); i++) {
			if (!aux_node.getCurrent_node_data().equals(aux_list_temp.get(i))) {
				int g_score = aux_node.getTree_level() + 1;
				int h_score = calculate_h_score(aux_list_temp.get(i),
						heuristics);
				int f_score = g_score + h_score;
				Node n = new Node(false, aux_list_temp.get(i), g_score, f_score);
				if (!existsOnClosedQueue(n)) {
					if (!checkNode(n)) {
						nodes_history.put(n, aux_node);
						open_queue.add(n);
					}
				}
			}
		}
	}

	private boolean existsOnClosedQueue(Node check_node) {
		if (!closed_queue.isEmpty()) {
			Iterator<Node> iterator = closed_queue.iterator();
			while (iterator.hasNext()) {
				Node check_aux = iterator.next();
				if (check_aux.getCurrent_node_data().equals(
						check_node.getCurrent_node_data())) {
					return true;
				}
			}
			return false;
		}
		return false;
	}

	private boolean checkNode(Node check_node) {
		Iterator<Node> iterator = open_queue.iterator();
		while (iterator.hasNext()) {
			Node aux_check = iterator.next();
			if (aux_check.getCurrent_node_data().equals(
					check_node.getCurrent_node_data())) {
				if (check_node.getTree_level() < aux_check.getTree_level()) {
					return true;
				}
			}
			return false;
		}
		return false;
	}

	private void print(String print_string, String type) {
		switch (type) {
		case "MOV":
			game_board.getBoard().getPuzzle_movemment_log()
					.append(print_string + "\n");
			break;
		case "RES":
			game_board.getBoard().getPuzzle_results_log()
					.append(print_string + "\n");
			logger.saveLog(print_string, Level.INFO);
			break;
		}
	}

	public int calculate_h_score(String node_data_test, String heuristic_data) {
		char[] test_array = node_data_test.toCharArray();
		int temp_score = 0;
		String[] heuristics;
		if (heuristic_data.contains("+")) {
			heuristics = heuristic_data.split("\\+");
		} else {
			heuristics = new String[1];
			heuristics[0] = heuristic_data;
		}
		for (int h = 0; h < heuristics.length; h++) {
			switch (heuristics[h]) {
			case "Manhattan":
				for (int i = 0; i < test_array.length; i++) {
					temp_score += Math.abs(i
							- Integer.parseInt(test_array[i] + ""));
				}
				break;
			case "Euclidean":
				for (int i = 0; i < test_array.length; i++) {
					temp_score += Math.sqrt(Math.pow(
							(i - Integer.parseInt(test_array[i] + "")), 2.0)
							+ Math.pow(
									(i - Integer.parseInt(test_array[i] + "")),
									2.0));
				}
				break;
			case "Levenshtein":
				if (!aux_levenshtein.equals("")) {
					char[] aux_array_lev = aux_levenshtein.toCharArray();
					for (int i = 0; i < test_array.length; i++) {
						temp_score += Math
								.min(Math.abs(i
										- Integer.parseInt(test_array[i] + "")),
										Math.abs(i
												- Integer
														.parseInt(aux_array_lev[i]
																+ "")));
					}
				} else {
					temp_score = 0;
				}
				aux_levenshtein = node_data_test;
				break;
			case "X-Y":
				int[][] matrix_index = new int[3][3];
				for (int i = 0; i < 3; i++) {
					matrix_index[0][i] = Integer.parseInt(test_array[i] + "");
					matrix_index[1][i] = Integer.parseInt(test_array[i + 3]
							+ "");
					matrix_index[2][i] = Integer.parseInt(test_array[i + 6]
							+ "");
				}
				/** Still needs some thinking **/
				break;
			case "Test Different Heuristic":
				for (int i = 0; i < test_array.length; i++) {
					temp_score += Math.sqrt(Math.pow(
							(i - Integer.parseInt(test_array[i] + "")), 3.0)
							+ Math.pow(
									(i - Integer.parseInt(test_array[i] + "")),
									3.0));
				}
				break;
			default:
				break;
			}
		}
		temp_score = temp_score / heuristics.length;
		return temp_score;
	}

	private void getFinalPlays(Node node_aux) {
		Node temp_node = node_aux;
		nodes_plays_list.add(solution_node);
		while (!temp_node.getCurrent_node_data().equals(current_node_data)) {
			Node new_temp_node = nodes_history.get(temp_node);
			nodes_plays_list.add(new_temp_node.getCurrent_node_data());
			temp_node = new_temp_node;
		}
	}

	public LinkedList<String> getNew_node_data_list() {
		return nodes_plays_list;
	}

	public int getFinal_time() {
		return final_time;
	}

	public int getFinal_level() {
		return final_level;
	}

}
