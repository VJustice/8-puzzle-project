package com.algorithms;

import java.util.HashMap;
import java.util.LinkedList;

import com.board.GameBoard;

public class AStar extends Algorithm {

	private Node root_node;

	private LinkedList<Node> nodes_evaluated;
	private LinkedList<Node> nodes_list;
	private HashMap<Node, Node> nodes_history;

	private String heuristic;
	private String current_data_nodes = "";
	private String solution_nodes = "";

	public AStar(GameBoard game_board, String[] current_data,
			String[] solution, String heuristic) {
		super(game_board, current_data, solution);
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

	@Override
	public void searchAlgorithm() {
		int start_score = 0;
		int estimated_cost = start_score + Integer.parseInt(heuristic);
		root_node = new Node(false, current_data_nodes, estimated_cost);
		nodes_list = new LinkedList<Node>();
		nodes_list.add(root_node);
		while (!nodes_list.isEmpty() && !nodes_list.peek().isVisited()) {
			Node current_node = nodes_list.removeFirst();
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

	@Override
	protected void addNode(Node new_node, Node old_node) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void nodeUp(Node node_aux) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void nodeDown(Node node_aux) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void nodeLeft(Node node_aux) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void nodeRight(Node node_aux) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void checkSolutionFound(Node old_node, String current_data) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void getPlays(Node node_aux) {
		// TODO Auto-generated method stub

	}

}
