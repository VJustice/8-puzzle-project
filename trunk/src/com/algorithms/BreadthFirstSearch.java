package com.algorithms;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class BreadthFirstSearch {

	@SuppressWarnings("unused")
	private String[] current_data;
	@SuppressWarnings("unused")
	private String[] solution;
	private Node root_node;
	private HashMap<Node, Integer> nodes_list;
	private HashMap<Node, Node> nodes_history;
	private LinkedList<String> new_node_data_list;

	private String current_data_nodes = "";
	private String solution_nodes = "";
	private boolean done = false;

	public BreadthFirstSearch(String[] current_data, String[] solution) {
		nodes_list = new HashMap<Node, Integer>();
		nodes_history = new HashMap<Node, Node>();
		new_node_data_list = new LinkedList<String>();
		this.current_data = current_data;
		this.solution = solution;
		for (int i = 0; i < current_data.length; i++) {
			current_data_nodes += current_data[i];
		}
		for (int i = 0; i < solution.length; i++) {
			solution_nodes += solution[i];
		}
	}

	public void getTreeStructure() {
		root_node = new Node(false, current_data_nodes);
		Queue<Node> queue = new LinkedList<Node>();
		addNode(root_node, null, queue);
		while (!queue.isEmpty() && !queue.peek().isVisited() && !done) {
			Node aux_node = (Node) queue.remove();
			aux_node.setVisited(true);
			nodeUp(aux_node, queue);
			nodeDown(aux_node, queue);
			nodeLeft(aux_node, queue);
			nodeRight(aux_node, queue);
		}
	}

	private void addNode(Node new_node, Node old_node, Queue<Node> queue) {
		if (!nodes_list.containsKey(new_node)) {
			int new_node_value;
			if (old_node == null) {
				new_node_value = 0;
			} else {
				new_node_value = nodes_list.get(old_node) + 1;
			}
			nodes_list.put(new_node, new_node_value);
			queue.add(new_node);
			nodes_history.put(new_node, old_node);
		}
	}

	private void nodeUp(Node node_aux, Queue<Node> queue) {
		String node_aux_string = node_aux.getCurrent_node_data();
		int a = node_aux_string.indexOf("0");
		if (a > 2 && !done) {
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
				System.out.println("U => " + next_state);
				checkSolutionFound(node_aux, next_state, queue);
			}
		}
	}

	private void nodeDown(Node node_aux, Queue<Node> queue) {
		String node_aux_string = node_aux.getCurrent_node_data();
		int a = node_aux_string.indexOf("0");
		if (a < 6 && !done) {
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
				System.out.println("D => " + next_state);
				checkSolutionFound(node_aux, next_state, queue);
			}
		}
	}

	private void nodeLeft(Node node_aux, Queue<Node> queue) {
		String node_aux_string = node_aux.getCurrent_node_data();
		int a = node_aux_string.indexOf("0");
		if ((a != 0 && a != 3 && a != 6) && !done) {
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
				System.out.println("L => " + next_state);
				checkSolutionFound(node_aux, next_state, queue);
			}
		}
	}

	private void nodeRight(Node node_aux, Queue<Node> queue) {
		String node_aux_string = node_aux.getCurrent_node_data();
		int a = node_aux_string.indexOf("0");
		if ((a != 2 && a != 5 && a != 8) && !done) {
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
				System.out.println("R => " + next_state);
				checkSolutionFound(node_aux, next_state, queue);
			}
		}
	}

	private void checkSolutionFound(Node old_node, String new_node_data,
			Queue<Node> queue) {
		Node n = new Node(false, new_node_data);
		addNode(n, old_node, queue);
		if (new_node_data.equals(solution_nodes)) {
			System.out.println("Solution Exists at Level " + nodes_list.get(n)
					+ " Of The Tree");
			System.out.println("Solution With Parent "
					+ nodes_history.get(n).getCurrent_node_data());
			done = true;
			getPlays(n);
		}
	}
	
	private void getPlays(Node aux_node) {
		Node temp_node = aux_node;
		while(!temp_node.getCurrent_node_data().equals(current_data_nodes)) {
			Node new_temp_node = nodes_history.get(temp_node);
			new_node_data_list.add(new_temp_node.getCurrent_node_data());
			temp_node = new_temp_node;
		}
	}
}
