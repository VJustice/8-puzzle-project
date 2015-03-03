package com.algorithms;

import java.util.HashMap;
import java.util.LinkedList;

import com.board.GameBoard;

public class AStar extends Algorithm {

	private Node root_node;

	private HashMap<Node, Integer> explored_nodes;
	private LinkedList<Node> nodes_queue_to_evaluate;
	private HashMap<Node, Node> nodes_history;

	private LinkedList<Node> temp_list;
	private LinkedList<String> new_node_data_list;

	private String heuristic_state;
	private String current_data_nodes = "";
	private String solution_nodes = "";

	private int DEPTH = 500;

	public AStar(GameBoard game_board, String[] current_data,
			String[] solution, String heuristic_state) {
		super(game_board, current_data, solution);
		this.heuristic_state = heuristic_state;
		temp_list = new LinkedList<Node>();
		new_node_data_list = new LinkedList<String>();
		nodes_history = new HashMap<Node, Node>();
		for (int i = 0; i < current_data.length; i++) {
			current_data_nodes += current_data[i];
		}
		for (int i = 0; i < solution.length; i++) {
			solution_nodes += solution[i];
		}
	}

	@Override
	public void searchAlgorithm() {
		explored_nodes = new HashMap<Node, Integer>();
		nodes_queue_to_evaluate = new LinkedList<Node>();
		int h_score = calculate_h_score(current_data_nodes);
		int f_score = h_score;
		new_node_data_list.clear();
		root_node = new Node(false, current_data_nodes, f_score);
		addNode(root_node, null);
		while (!nodes_queue_to_evaluate.isEmpty()) {
			Node current_node = nodes_queue_to_evaluate.remove();
			checkNeighbours(current_node);
			sortList(temp_list);
			for (int i = 0; i < temp_list.size(); i++) {
				System.out.println(temp_list.get(i).toString());
			}
			Node node_aux_final = temp_list.removeFirst();
			addNode(node_aux_final, current_node);
			if (node_aux_final.getCurrent_node_data()
					.equals(solution_nodes)) {
				game_board
						.getBoard()
						.getPuzzle_results_log()
						.append("Solution at Level "
								+ explored_nodes.get(node_aux_final)
								+ " cenas:  "
								+ node_aux_final.getCurrent_node_data()
								+ "\n");
				getFinalPlays(node_aux_final);
				clearAll();
			}
			if (!temp_list.isEmpty()
					&& explored_nodes.get(node_aux_final) == DEPTH) {
				clearAll();
				game_board.getBoard().getPuzzle_results_log()
						.append("No Solution Found \n");
			}
			temp_list.clear();
		}
	}

	private void clearAll() {
		nodes_queue_to_evaluate.clear();
		temp_list.clear();
		explored_nodes.clear();
		nodes_history.clear();
	}

	@Override
	protected void checkNeighbours(Node aux_node) {
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
		Node parent_node_aux;
		if (nodes_history.get(aux_node) == null) {
			parent_node_aux = aux_node;
		} else {
			parent_node_aux = nodes_history.get(aux_node);
		}
		for (int i = 0; i < aux_list_temp.size(); i++) {
			if (!parent_node_aux.getCurrent_node_data().equals(
					aux_list_temp.get(i))) {
				int g_score = explored_nodes.get(aux_node) + 1;
				int h_score = calculate_h_score(aux_list_temp.get(i));
				int f_score = g_score + h_score;
				Node n = new Node(false, aux_list_temp.get(i), f_score);
				temp_list.add(n);
			}
		}
	}

	private int calculate_h_score(String node_data_test) {
		char[] test_array = node_data_test.toCharArray();
		char[] s = solution_nodes.toCharArray();
		int temp_score = 0;
		for (int i = 0; i < test_array.length; i++) {
			temp_score += Math.abs(i - Integer.parseInt(test_array[i] + ""));
		}
		return temp_score;
	}

	@Override
	protected void addNode(Node new_node, Node old_node) {
		if (!explored_nodes.containsKey(new_node)
				&& !nodes_queue_to_evaluate.contains(new_node)) {
			int new_node_value;
			if (old_node == null) {
				new_node_value = 0;
			} else {
				new_node_value = explored_nodes.get(old_node) + 1;
			}
			new_node.setVisited(true);
			nodes_queue_to_evaluate.add(new_node);
			explored_nodes.put(new_node, new_node_value);
			nodes_history.put(new_node, old_node);
			System.out.println("LOWEST=> " + new_node);
		}
	}

	@Override
	protected void checkSolutionFound(Node old_node, String current_data) {

	}

	@Override
	protected void checkSolutionFound(Node old_node, String nodes_data,
			int heuristic) {
	}

	@Override
	protected void getFinalPlays(Node node_aux) {
		Node temp_node = node_aux;
		new_node_data_list.add(solution_nodes);
		while (!temp_node.getCurrent_node_data().equals(current_data_nodes)) {
			Node new_temp_node = nodes_history.get(temp_node);
			new_node_data_list.add(new_temp_node.getCurrent_node_data());
			temp_node = new_temp_node;
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

	public LinkedList<String> getNew_node_data_list() {
		return new_node_data_list;
	}

}
