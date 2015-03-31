package com.genetics;

import com.board.GameBoard;

public class GeneticAlgorithm {
	
	private int generations_number;
	private GameBoard game_board;
	private String[] heuristic_data;
	
	public GeneticAlgorithm(GameBoard game_board, String[] heuristic_data) {
		this.game_board = game_board;
		this.heuristic_data = heuristic_data;
		
//		for (int i = 0; i < heuristic_data.length; i++) {
//			
//		}
		
	}
	
	private Individual crossover(Individual indiv1, Individual indiv2) {
		Individual newIndv;
		String newName = indiv1.getHeuristicName() + indiv2.getHeuristicName();
		int newGene = ( (indiv1.getGene() + indiv2.getGene()) / 2 );
		newIndv = new Individual(newName, newGene);
		return newIndv;
	}
	
	

}
