package com.genetics;

import com.board.GameBoard;

public class GeneticAlgorithm {
	
	private int generations_number;
	private GameBoard game_board;
	private String[] heuristic_data;
	private Individual[] individual;
	private Individual[] newGeneration;

	
	public GeneticAlgorithm(GameBoard game_board, String[] heuristic_data) {
		this.game_board = game_board;
		this.heuristic_data = heuristic_data;

		individual = new Individual[heuristic_data.length];
		
		for (int i=0; i < heuristic_data.length; i++) {
			individual[i]  = new Individual(heuristic_data[i], 0);
		}
		
		Population population = new Population(individual);

	}
	
	public void start(){
		newGeneration = new Individual[heuristic_data.length / 2];
		int j=0;
		for (int i = 0 ; i< individual.length; i+=2) {
			newGeneration[j] = crossover(individual[i], individual[i+1]);
			j++;
		}
	}
	
	private Individual crossover(Individual indiv1, Individual indiv2) {
		Individual newIndv;
		String newName = indiv1.getHeuristicName() + indiv2.getHeuristicName();
		int newGene = ( (indiv1.getGene() + indiv2.getGene()) / 2 );
		newIndv = new Individual(newName, newGene);
		return newIndv;
	}
	
	

}
