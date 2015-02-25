package com.algorithms;

import java.util.HashMap;
import java.util.LinkedList;

import com.board.GameBoard;

public class AStar {

	private GameBoard game_board;
	private Node root_node;

	private LinkedList<Node> nodes_evaluated;
	private LinkedList<Node> nodes_list;
	private HashMap<Node, Node> nodes_history;

	private String heuristic;
	private String current_data_nodes = "";
	private String solution_nodes = "";

	public AStar(GameBoard game_board, String[] current_data,
			String[] solution, String heuristic) {
		this.game_board = game_board;
		this.heuristic = heuristic;
		nodes_evaluated = new LinkedList<Node>();
		nodes_history = new HashMap<Node, Node>();
		for (int i = 0; i < current_data.length; i++) {
			current_data_nodes += current_data[i];
		}
		for (int i = 0; i < solution.length; i++) {
			solution_nodes += solution[i];
		}
	}

	public void searchAStar() {
		int start_score = 0;
		int estimated_cost = start_score + Integer.parseInt(heuristic);
		root_node = new Node(false, current_data_nodes, estimated_cost);
		nodes_list = new LinkedList<Node>();
		nodes_list.add(root_node);
		while (!nodes_list.isEmpty() && !nodes_list.peek().isVisited()) {
			Node current_node = nodes_list.removeFirst();
			current_node.setVisited(true);

			if (current_node.getCurrent_node_data().equals(solution_nodes)) {
				// done xD
			}
			nodes_evaluated.add(current_node);

			sortList(nodes_list);
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

}
