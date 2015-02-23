package com.algorithms;

import java.util.LinkedList;
import java.util.Queue;

public class BreadthFirstSearch {

	private String[] current_data;
	private String[] solution;
	private Node root_node;
	private LinkedList<Node> nodes_list;

	private String current_data_nodes = "";

	public BreadthFirstSearch(String[] current_data, String[] solution) {
		nodes_list = new LinkedList<Node>();
		this.current_data = current_data;
		this.solution = solution;
		for (int i = 0; i < current_data.length; i++) {
			current_data_nodes += current_data[i];
		}
	}

	public void getTreeStructure() {
		root_node = new Node(true, current_data_nodes);
		nodes_list.add(root_node);
		Queue<Node> queue = new LinkedList<Node>();
		queue.add(root_node);
		while (!queue.isEmpty()) {
			Node aux_node = (Node) queue.remove();
			Node child_node = null;
			checkAvailablePlays(aux_node);

		}
	}

	private void checkAvailablePlays(Node node) {
		String data = node.getCurrent_node_data();

		for (Node existing_node : nodes_list) {

		}
	}

}
