package com.algorithms;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;

import com.board.GameBoard;

public class AStar {

	private GameBoard game_board;
	private Node root_node;

	private LinkedList<Node> nodes_evaluated;
	private PriorityQueue<Node> nodes_list;
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
		nodes_list = new PriorityQueue<Node>(new Comparator<Node>() {

			@Override
			public int compare(Node node_one, Node node_two) {
				if(node_one.getEstimated_score() >= node_two.getEstimated_score()){
					return node_one.getEstimated_score();
				}
				else {
					return node_two.getEstimated_score();
				}
			}
			
		});
		nodes_list.add(root_node);
		nodes_list.add(new Node(false, solution_nodes, estimated_cost+3));
		while (!nodes_list.isEmpty()) {
			Node current_node = nodes_list.poll();
			System.out.println("ahahahaahh: " + current_node.getCurrent_node_data());
		}
	}
}
