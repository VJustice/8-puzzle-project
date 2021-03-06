package com.algorithms;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Level;

import com.board.GameBoard;
import com.logger.LoggerDebugger;

public class BreadthFirstSearch {
	private Node root_node;

	private HashMap<Node, Integer> nodes_list;
	private HashMap<Node, Node> nodes_history;
	private LinkedList<String> new_node_data_list;
	private Queue<Node> queue;

	private String current_data_nodes = "";
	private String solution_nodes = "";
	private boolean done = false;

	private LoggerDebugger logger;
	private GameBoard game_board;

	/** Constructor **/
	public BreadthFirstSearch(GameBoard game_board, String current_data,
			String solution, LoggerDebugger logger) {
		this.game_board = game_board;
		nodes_list = new HashMap<Node, Integer>();
		nodes_history = new HashMap<Node, Node>();
		new_node_data_list = new LinkedList<String>();
		this.current_data_nodes = current_data;
		this.solution_nodes = solution;
		this.logger = logger;
	}

	/** Start Search Algorithm **/
	public void searchAlgorithm() {
		root_node = new Node(false, current_data_nodes);
		queue = new LinkedList<Node>();
		addNode(root_node, null);
		while (!queue.isEmpty() && !queue.peek().isVisited() && !done) {
			Node aux_node = (Node) queue.remove();
			aux_node.setVisited(true);
			nodeDown(aux_node);
			nodeUp(aux_node);
			nodeRight(aux_node);
			nodeLeft(aux_node);
		}
	}

	/** Adds Node to List **/
	protected void addNode(Node new_node, Node old_node) {
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

	/** Checks Node Up **/
	private void nodeUp(Node node_aux) {
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
				game_board.getBoard().getPuzzle_movemment_log()
						.append("U => " + next_state + "\n");
				checkSolutionFound(node_aux, next_state);
			}
		}
	}

	/** Checks Node Down **/
	private void nodeDown(Node node_aux) {
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
				game_board.getBoard().getPuzzle_movemment_log()
						.append("D => " + next_state + "\n");
				checkSolutionFound(node_aux, next_state);
			}
		}
	}

	/** Checks Node Left **/
	private void nodeLeft(Node node_aux) {
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
				game_board.getBoard().getPuzzle_movemment_log()
						.append("L => " + next_state + "\n");
				checkSolutionFound(node_aux, next_state);
			}
		}
	}

	/** Checks Node Right **/
	private void nodeRight(Node node_aux) {
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
				game_board.getBoard().getPuzzle_movemment_log()
						.append("R => " + next_state + "\n");
				checkSolutionFound(node_aux, next_state);
			}
		}
	}

	/** Checks if Solution has been Found **/
	protected void checkSolutionFound(Node old_node, String new_node_data) {
		Node n = new Node(false, new_node_data);
		addNode(n, old_node);
		if (new_node_data.equals(solution_nodes)) {
			game_board.getBoard().getPuzzle_movemment_log()
					.append("Solution at Level " + nodes_list.get(n));
			logger.saveLog("Solution at Level " + nodes_list.get(n),
					Level.INFO);
			done = true;
			getPlays(n);
		}
	}

	/** Checks if Solution has been Found **/
	protected void checkSolutionFound(Node old_node, String current_data,
			int heuristic) {
	}

	/** Gets All Plays **/
	protected void getPlays(Node aux_node) {
		Node temp_node = aux_node;
		new_node_data_list.add(solution_nodes);
		while (!temp_node.getCurrent_node_data().equals(current_data_nodes)) {
			Node new_temp_node = nodes_history.get(temp_node);
			new_node_data_list.add(new_temp_node.getCurrent_node_data());
			temp_node = new_temp_node;
		}
	}

	/** Getter **/
	public LinkedList<String> getNew_node_data_list() {
		return new_node_data_list;
	}
}
