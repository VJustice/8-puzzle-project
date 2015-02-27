package com.algorithms;

import java.util.HashMap;
import java.util.LinkedList;

import com.board.GameBoard;

public class AStar extends Algorithm {

	private Node root_node;

	private HashMap<Node, Integer> explored_nodes;
	private LinkedList<Node> nodes_queue;
	private HashMap<Node, Node> nodes_history;
	private LinkedList<String> new_node_data_list;

	private String heuristic_state;
	private String current_data_nodes = "";
	private String solution_nodes = "";
	private LinkedList<Node> temp_list;

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
		nodes_queue = new LinkedList<Node>();
		int g_score = 0;
		int f_score = g_score + calculateNodeHeuristics(current_data_nodes);
		root_node = new Node(false, current_data_nodes, g_score);
		addNode(root_node, null);
		new_node_data_list.clear();
		while (!nodes_queue.isEmpty()) {
			Node current_node = nodes_queue.remove();
			current_node.setVisited(true);
			checkNodeDirection(current_node, f_score);
			sortList(temp_list);
			addNode(temp_list.getFirst(), current_node);
			if (temp_list.getFirst().getCurrent_node_data()
					.equals(solution_nodes)) {
				game_board
						.getBoard()
						.getPuzzle_results_log()
						.append("Solution at Level "
								+ explored_nodes.get(temp_list.getFirst())
								+ " cenas:  "
								+ temp_list.getFirst().getCurrent_node_data()
								+ "\n");
				getPlays(temp_list.getFirst());
				clearAll();
			}
			if (!temp_list.isEmpty()
					&& explored_nodes.get(temp_list.getFirst()) == DEPTH) {
				clearAll();
				game_board.getBoard().getPuzzle_results_log()
						.append("No Solution Found \n");
			}
			temp_list.clear();
		}
	}

	private void clearAll() {
		nodes_queue.clear();
		temp_list.clear();
		explored_nodes.clear();
		nodes_history.clear();
	}

	@Override
	protected void checkNodeDirection(Node aux_node, int f_score) {
		LinkedList<String> aux_list_temp = new LinkedList<String>();
		String aux_node_data = aux_node.getCurrent_node_data();
		String next_state = "";
		int zero_index = aux_node_data.indexOf("0");
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
				int new_score = calculateNodeHeuristics(aux_list_temp
						.get(i));
				Node n = new Node(false, aux_list_temp.get(i),
						f_score + new_score);
				temp_list.add(n);
			}
		}
	}

	@Override
	protected void addNode(Node new_node, Node old_node) {
		if (!explored_nodes.containsKey(new_node)
				&& !nodes_queue.contains(new_node)) {
			int new_node_value;
			if (old_node == null) {
				new_node_value = 0;
			} else {
				new_node_value = explored_nodes.get(old_node) + 1;
			}
			explored_nodes.put(new_node, new_node_value);
			nodes_queue.add(new_node);
			nodes_history.put(new_node, old_node);
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
	protected void getPlays(Node node_aux) {
		Node temp_node = node_aux;
		new_node_data_list.add(solution_nodes);
		while (!temp_node.getCurrent_node_data().equals(current_data_nodes)) {
			Node new_temp_node = nodes_history.get(temp_node);
			new_node_data_list.add(new_temp_node.getCurrent_node_data());
			temp_node = new_temp_node;
		}
	}

	private int calculateNodeHeuristics(String data) {
		int h_score = 0;
		switch(heuristic_state) {
		case "Distance_Between":
			
			break;
		default:
			break;
		}
		return h_score;
		
	}

	private LinkedList<Node> sortList(LinkedList<Node> list) {
		for (int i = 0; i < list.size() - 1; i++) {
			for (int j = i + 1; j < list.size(); j++) {
				if (list.get(i).getEstimated_score() >= list.get(j)
						.getEstimated_score()) {
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
