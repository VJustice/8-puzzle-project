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
		checkAvailablePlays(root_node);
		while (!queue.isEmpty()) {
			Node aux_node = (Node) queue.remove();
			Node child_node = null;

		}
	}

	private void checkAvailablePlays(Node node) {
		String data = node.getCurrent_node_data();
		System.out.println("OLD DATA: " + data);
		int zero = data.indexOf('0');
		boolean done = false;
		char aux = '0';
		String aux_data = "";
		while (!done) {
			for (int i = 0; i < data.length(); i++) {
				if (i - 2 == zero) {
					aux = data.charAt(i - 1);
					break;
				} else if (i + 2 == zero) {
					aux = data.charAt(i + 1);
					break;
				} else if (i - 4 == zero) {
					aux = data.charAt(i - 3);
					break;
				} else if (i + 4 == zero) {
					aux = data.charAt(i + 3);
					break;
				} else {
					done = true;
				}
			}
			aux_data = data.replace(aux, 't');
			aux_data = aux_data.replace(data.charAt(zero), aux);
			aux_data = aux_data.replace('t', '0');
			System.out.println("NEW DATA: " + aux_data);
			Node n = new Node(false, aux_data);
			if (!nodes_list.contains(n)) {
				nodes_list.add(n);
			}
		}
		System.out.println("done");
		for(int i = 0; i < nodes_list.size(); i++) {
			System.out.println(nodes_list.get(i).getCurrent_node_data());
		}
	}

}
