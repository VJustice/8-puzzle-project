package com.algorithms;

public class Node {

	private boolean visited;
	private String current_node_data;
	private int tree_level;
	private int estimated_score;

	/** Constructor **/
	public Node(boolean visited, String current_node_data) {
		this.visited = visited;
		this.current_node_data = current_node_data;
	}

	/** Constructor **/
	public Node(boolean visited, String current_node_data, int tree_level,
			int estimated_score) {
		this.visited = visited;
		this.current_node_data = current_node_data;
		this.tree_level = tree_level;
		this.estimated_score = estimated_score;
	}

	/** Getters and Setters **/
	public boolean isVisited() {
		return visited;
	}

	public String getCurrent_node_data() {
		return current_node_data;
	}

	public int getTree_level() {
		return tree_level;
	}

	public void setTree_level(int tree_level) {
		this.tree_level = tree_level;
	}

	public int getEstimated_score() {
		return estimated_score;
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
	}

	public void setCurrent_node_data(String current_node_data) {
		this.current_node_data = current_node_data;
	}

	public void setEstimated_score(int estimated_score) {
		this.estimated_score = estimated_score;
	}

	public String toString() {
		return "Node (Visited?: " + isVisited() + "; Data: "
				+ getCurrent_node_data() + "; Nível: " + getTree_level()
				+ "; Score: " + getEstimated_score() + ")";
	}
}
