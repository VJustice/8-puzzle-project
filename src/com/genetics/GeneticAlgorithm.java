package com.genetics;

import java.util.LinkedList;

import com.algorithms.AStar;
import com.board.GameBoard;

public class GeneticAlgorithm {
	
	private static final int tournament_size = 3;
	
	private GameBoard game_board;
	private String[] heuristic_data;
	private LinkedList<Individual> individual;
	private LinkedList<Individual> newGeneration;
	private String solution;
	private String current_data;
	private Population population;

	
	public GeneticAlgorithm(GameBoard game_board, String current_data, String solution, String[] heuristic_data) {
		this.solution = solution;
		this.current_data = current_data;
		this.game_board = game_board;
		this.heuristic_data = heuristic_data;
		
		individual = new LinkedList<Individual>();
		for (int i=0; i < heuristic_data.length; i++) {
			AStar a_star = new AStar(game_board, current_data, solution, heuristic_data[i]);
			Thread t = new Thread(a_star);
			t.start();
			try {
				t.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			individual.add(new Individual(heuristic_data[i], 0, a_star.getFinal_level(), a_star.getFinal_time()));
		}
		population = new Population(individual);
	}
	public void init() {
		start();
		population.selectFitness(tournament_size);
		start();
	}
	
	private void start(){	
		System.out.println("Start");
		newGeneration = new LinkedList<Individual>();
		LinkedList<String> temp = new LinkedList<String>(); 
		for (int i = 0 ; i< 3; i++) {
			for (int j = 0 ; j< 3; j++) {
//				System.out.println();
				if (i != j && !repetido(i,j,temp)) {
					newGeneration.add(crossover(individual.get(i), individual.get(j)));
					temp.add(i+ "-" + j);
				}
			}
		}
		population.concat(newGeneration);
	}
	
	private boolean repetido(int i, int j, LinkedList<String> s) {
		String b = j + "-" + i;
		if (s.contains(b)) {
			return true;
		} else 
			return false;
	}

	private Individual crossover(Individual indiv1, Individual indiv2) {
		String newName = indiv1.getHeuristicName() + "+" + indiv2.getHeuristicName();
		AStar a_star = new AStar(game_board, current_data, solution, newName);
		Thread t = new Thread(a_star);
		t.start();
		try {
			t.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Individual newIndv;
		newIndv = new Individual(newName, 0, a_star.getFinal_level(), a_star.getFinal_time());
		return newIndv;
	}

	
}
