package com.algorithms;

import com.board.GameBoard;

public abstract class Algorithm {

	protected GameBoard game_board;
	protected String[] current_data;
	protected String[] solution;

	public Algorithm(GameBoard game_board, String[] current_data,
			String[] solution) {
		this.game_board = game_board;
		this.current_data = current_data;
		this.solution = solution;
	}

	public abstract void searchAlgorithm();

	protected abstract void addNode(Node new_node, Node old_node);
	
	protected abstract void checkNeighbours(Node aux_node);

	protected abstract void checkSolutionFound(Node old_node,
			String current_data);
	
	protected abstract void checkSolutionFound(Node old_node,
			String current_data, int heuristic);

	protected abstract void getFinalPlays(Node node_aux);
}
