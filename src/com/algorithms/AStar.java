package com.algorithms;

import java.util.HashMap;
import java.util.LinkedList;

import com.board.GameBoard;

public class AStar extends Algorithm {

	private HashMap<Node, Integer> explored_nodes = new HashMap<Node, Integer>();
	private LinkedList<Node> nodes_queue_to_evaluate = new LinkedList<Node>();
	private HashMap<Node, Node> nodes_history = new HashMap<Node, Node>();
	private LinkedList<Node> temp_list = new LinkedList<Node>();
	private LinkedList<String> nodes_plays_list = new LinkedList<String>();

	@SuppressWarnings("unused")
	private String heuristic_state;
	private String current_data_nodes = "";
	private String solution_nodes = "";

	private int DEPTH = 500;

	public AStar(GameBoard game_board, String[] current_data,
			String[] solution, String heuristic_state) {
		super(game_board, current_data, solution);
		this.heuristic_state = heuristic_state;
		for (int i = 0; i < current_data.length; i++) {
			current_data_nodes += current_data[i];
		}
		for (int i = 0; i < solution.length; i++) {
			solution_nodes += solution[i];
		}
	}

	@Override
	public void searchAlgorithm() {
		int h_score = calculate_h_score(current_data_nodes);
		int f_score = h_score;
		nodes_plays_list.clear();
		Node root_node = new Node(false, current_data_nodes, 0, f_score);
		addNode(root_node, null);
		while (!nodes_queue_to_evaluate.isEmpty()) {
			Node testing_node = nodes_queue_to_evaluate.removeFirst();
			checkNeighbours(testing_node);
			sortList(temp_list);
			Node node_aux_final = temp_list.removeFirst();
			addNode(node_aux_final, testing_node);
			if (node_aux_final.getCurrent_node_data().equals(solution_nodes)) {
				print("Solution at Level " + explored_nodes.get(node_aux_final)
						+ " cenas:  " + node_aux_final.getCurrent_node_data()
						+ "\n", "RES");
				getFinalPlays(node_aux_final);
				clearAll();
			}
			if (explored_nodes.get(node_aux_final) == DEPTH) {
				clearAll();
				print("No Solution Found \n", "RES");
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
		for (int i = 0; i < aux_list_temp.size(); i++) {
			if (!aux_node.getCurrent_node_data().equals(aux_list_temp.get(i))) {
				int g_score = aux_node.getTree_level() + 1;
				int h_score = calculate_h_score(aux_list_temp.get(i));
				int f_score = (g_score + h_score) - aux_node.getTree_level();
				Node n = new Node(false, aux_list_temp.get(i), g_score, f_score);
				System.out.println(n);
				temp_list.add(n);
			}
		}
	}

	private int calculate_h_score(String node_data_test) {
		char[] test_array = node_data_test.toCharArray();
		int temp_score = 0;
		for (int i = 0; i < test_array.length; i++) {
			temp_score += Math.abs(i - Integer.parseInt(test_array[i] + ""));
		}
		return temp_score;
	}

	@Override
	protected void addNode(Node child_node, Node parent_node) {
		if (!explored_nodes.containsKey(child_node)) {
			int tree_node_level;
			if (parent_node == null) {
				tree_node_level = 0;
			} else {
				tree_node_level = parent_node.getTree_level() + 1;
			}
			child_node.setVisited(true);
			nodes_queue_to_evaluate.add(child_node);
			explored_nodes.put(child_node, tree_node_level);
			nodes_history.put(child_node, parent_node);
			System.out.println("CHILD NODE => " + child_node);
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
		nodes_plays_list.add(solution_nodes);
		while (!temp_node.getCurrent_node_data().equals(current_data_nodes)) {
			Node new_temp_node = nodes_history.get(temp_node);
			nodes_plays_list.add(new_temp_node.getCurrent_node_data());
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

	private void print(String print_string, String type) {
		switch (type) {
		case "MOV":
			game_board.getBoard().getPuzzle_movemment_log().append(print_string);
			break;
		case "RES":
			game_board.getBoard().getPuzzle_results_log().append(print_string);
			break;
		}
	}

	public LinkedList<String> getNew_node_data_list() {
		return nodes_plays_list;
	}

}
