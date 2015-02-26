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
		int initial_score = 0;
		root_node = new Node(false, current_data_nodes, initial_score);
		addNode(root_node, null);
		new_node_data_list.clear();
		while (!nodes_queue.isEmpty()) {
			Node current_node = nodes_queue.remove();
			current_node.setVisited(true);
			nodeUp(current_node);
			nodeDown(current_node);
			nodeLeft(current_node);
			nodeRight(current_node);
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
								+ temp_list.getFirst().getCurrent_node_data() + "\n");
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
	protected void nodeUp(Node node_aux) {
		String node_aux_string = node_aux.getCurrent_node_data();
		int a = node_aux_string.indexOf("0");
		if (a > 2) {
			String next_state = node_aux_string.substring(0, a - 3) + "0"
					+ node_aux_string.substring(a - 2, a)
					+ node_aux_string.charAt(a - 3)
					+ node_aux_string.substring(a + 1);
			Node parent_node_aux;
			if (nodes_history.get(node_aux) == null) {
				parent_node_aux = node_aux;
			} else {
				parent_node_aux = nodes_history.get(node_aux);
			}
			if (!parent_node_aux.getCurrent_node_data().equals(next_state)) {
				int current_node_score = calculateNodeHeuristics(next_state);
				Node n = new Node(false, next_state, current_node_score);
				temp_list.add(n);
				// System.out.println("U => " + next_state);
			}
		}
	}

	@Override
	protected void nodeDown(Node node_aux) {
		String node_aux_string = node_aux.getCurrent_node_data();
		int a = node_aux_string.indexOf("0");
		if (a < 6) {
			String next_state = node_aux_string.substring(0, a)
					+ node_aux_string.substring(a + 3, a + 4)
					+ node_aux_string.substring(a + 1, a + 3) + "0"
					+ node_aux_string.substring(a + 4);
			Node parent_node_aux;
			if (nodes_history.get(node_aux) == null) {
				parent_node_aux = node_aux;
			} else {
				parent_node_aux = nodes_history.get(node_aux);
			}
			if (!parent_node_aux.getCurrent_node_data().equals(next_state)) {
				int current_node_score = calculateNodeHeuristics(next_state);
				Node n = new Node(false, next_state, current_node_score);
				temp_list.add(n);
				// System.out.println("D => " + next_state);
			}
		}
	}

	@Override
	protected void nodeLeft(Node node_aux) {
		String node_aux_string = node_aux.getCurrent_node_data();
		int a = node_aux_string.indexOf("0");
		if ((a != 0 && a != 3 && a != 6)) {
			String next_state = node_aux_string.substring(0, a - 1) + "0"
					+ node_aux_string.charAt(a - 1)
					+ node_aux_string.substring(a + 1);
			Node parent_node_aux;
			if (nodes_history.get(node_aux) == null) {
				parent_node_aux = node_aux;
			} else {
				parent_node_aux = nodes_history.get(node_aux);
			}
			if (!parent_node_aux.getCurrent_node_data().equals(next_state)) {
				int current_node_score = calculateNodeHeuristics(next_state);
				Node n = new Node(false, next_state, current_node_score);
				temp_list.add(n);
				// System.out.println("L => " + next_state);
			}
		}
	}

	@Override
	protected void nodeRight(Node node_aux) {
		String node_aux_string = node_aux.getCurrent_node_data();
		int a = node_aux_string.indexOf("0");
		if ((a != 2 && a != 5 && a != 8)) {
			String next_state = node_aux_string.substring(0, a)
					+ node_aux_string.charAt(a + 1) + "0"
					+ node_aux_string.substring(a + 2);
			Node parent_node_aux;
			if (nodes_history.get(node_aux) == null) {
				parent_node_aux = node_aux;
			} else {
				parent_node_aux = nodes_history.get(node_aux);
			}
			if (!parent_node_aux.getCurrent_node_data().equals(next_state)) {
				int current_node_score = calculateNodeHeuristics(next_state);
				Node n = new Node(false, next_state, current_node_score);
				temp_list.add(n);
				// System.out.println("R => " + next_state);

			}
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
		int value_heuristic = 0;
		switch (heuristic_state) {
		case "Batatas":
			char[] c = data.toCharArray();
			char[] s = solution_nodes.toCharArray();
			for (int i = 0; i < c.length; i++) {
				if (c[i] != s[i]) {
					value_heuristic++;
				}
			}
			break;
		default:
			break;
		}
		return value_heuristic;
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
