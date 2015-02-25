package com.algorithms;

import java.util.HashMap;
import java.util.LinkedList;

import com.board.GameBoard;

public class AStar extends Algorithm {

	private Node root_node;

	private LinkedList<Node> nodes_evaluated;
	private HashMap<Node, Integer> nodes_list;
	private LinkedList<String> new_node_data_list;
	private HashMap<Node, Node> nodes_history;

	private String heuristic_state;
	private String current_data_nodes = "";
	private String solution_nodes = "";

	public AStar(GameBoard game_board, String[] current_data,
			String[] solution, String heuristic_state) {
		super(game_board, current_data, solution);
		this.heuristic_state = heuristic_state;
		new_node_data_list = new LinkedList<String>();
		nodes_evaluated = new LinkedList<Node>();
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
		int start_score = 0;
		int estimated_cost = start_score; // valor
											// da
											// heuristica
		root_node = new Node(false, current_data_nodes, estimated_cost);
		nodes_list = new HashMap<Node, Integer>();
		nodes_evaluated.add(root_node);
		while (!nodes_evaluated.isEmpty()
				&& !nodes_evaluated.peek().isVisited()) {
			sortList(nodes_evaluated);
			Node current_node = nodes_evaluated.removeFirst();
			current_node.setVisited(true);
			for (int i = 0; i < 3; i++) {
				switch (i) {
				case 0:
					nodeUp(current_node);
					break;
				case 1:
					nodeDown(current_node);
					break;
				case 2:
					nodeLeft(current_node);
					break;
				case 3:
					nodeRight(current_node);
					break;
				}
			}
		}
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

	@Override
	protected void addNode(Node new_node, Node old_node) {
		if (!nodes_list.containsKey(new_node)) {
			int new_node_value;
			if (old_node == null) {
				new_node_value = 0;
			} else {
				new_node_value = nodes_list.get(old_node) + 1;
			}
			nodes_list.put(new_node, new_node_value);
			nodes_evaluated.add(new_node);
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
			// checks if the parent_node is the root_node
			if (nodes_history.get(node_aux) == null) {
				parent_node_aux = node_aux;
			} else {
				parent_node_aux = nodes_history.get(node_aux);
			}
			int node_heuristic = calculateNodeHeuristics(next_state);
			// So it won't go back
			if (!parent_node_aux.getCurrent_node_data().equals(next_state)) {
				game_board.getBoard().getPuzzle_movemment_log()
						.append("U => " + next_state + "\n");
				checkSolutionFound(node_aux, next_state, node_heuristic);
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
			int node_heuristic = calculateNodeHeuristics(next_state);
			if (!parent_node_aux.getCurrent_node_data().equals(next_state)) {
				game_board.getBoard().getPuzzle_movemment_log()
						.append("D => " + next_state + "\n");
				checkSolutionFound(node_aux, next_state, node_heuristic);
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
			int node_heuristic = calculateNodeHeuristics(next_state);
			if (!parent_node_aux.getCurrent_node_data().equals(next_state)) {
				game_board.getBoard().getPuzzle_movemment_log()
						.append("L => " + next_state + "\n");
				checkSolutionFound(node_aux, next_state, node_heuristic);
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
			int node_heuristic = calculateNodeHeuristics(next_state);
			if (!parent_node_aux.getCurrent_node_data().equals(next_state)) {
				game_board.getBoard().getPuzzle_movemment_log()
						.append("R => " + next_state + "\n");
				checkSolutionFound(node_aux, next_state, node_heuristic);
			}
		}
	}

	@Override
	protected void checkSolutionFound(Node old_node, String current_data) {
	}

	@Override
	protected void checkSolutionFound(Node old_node, String new_node_data,
			int node_heuristic) {
		Node n = new Node(false, new_node_data, node_heuristic);
//		addNode(n, old_node);
		if (new_node_data.equals(solution_nodes)) {
			// game_board.getBoard().getPuzzle_movemment_log()
			// .append("Solution at Level " + nodes_list.get(n));
			game_board.getBoard().getPuzzle_movemment_log()
					.append("Test Successfull");
			getPlays(n);
		}

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
		System.out.println(value_heuristic);
		return value_heuristic;
	}

	public LinkedList<String> getNew_node_data_list() {
		return new_node_data_list;
	}

}
