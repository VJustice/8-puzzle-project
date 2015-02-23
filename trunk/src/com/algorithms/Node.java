package com.algorithms;

public class Node {

	private boolean visited;
	private String current_node_data;

	public Node(boolean visited, String current_node_data) {
		this.visited = visited;
		this.current_node_data = current_node_data;
	}

	public boolean isVisited() {
		return visited;
	}

	public String getCurrent_node_data() {
		return current_node_data;
	}
	
	public void setVisited(boolean visited) {
		this.visited = visited;
	}
	
	public void setCurrent_node_data(String current_node_data) {
		this.current_node_data = current_node_data;
	}

}
