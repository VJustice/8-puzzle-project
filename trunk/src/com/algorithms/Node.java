package com.algorithms;

public class Node {

	private boolean visited;
	private String current_node_data;
	private int estimated_score;

	public Node(boolean visited, String current_node_data) {
		this.visited = visited;
		this.current_node_data = current_node_data;
	}

	public Node(boolean visited, String current_node_data, int estimated_score) {
		this.visited = visited;
		this.current_node_data = current_node_data;
		this.estimated_score = estimated_score;
	}

	public boolean isVisited() {
		return visited;
	}

	public String getCurrent_node_data() {
		return current_node_data;
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
				+ getCurrent_node_data() + "; Score: " + getEstimated_score()
				+ ")";
	}
}
