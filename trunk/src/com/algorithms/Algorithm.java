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

	protected abstract void searchAlgorithm();

	protected abstract void addNode(Node new_node, Node old_node);

	protected abstract void nodeUp(Node node_aux);

	protected abstract void nodeDown(Node node_aux);

	protected abstract void nodeLeft(Node node_aux);

	protected abstract void nodeRight(Node node_aux);

	protected abstract void checkSolutionFound(Node old_node,
			String current_data);

	protected abstract void getPlays(Node node_aux);
}
