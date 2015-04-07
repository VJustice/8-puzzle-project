package com.genetics;

import java.util.LinkedList;

import com.algorithms.AStar;
import com.board.GameBoard;

public class GeneticAlgorithm {
	
	private int generations_number;
	private GameBoard game_board;
	private String[] heuristic_data;
	private Individual[] individual;
	private Individual[] newGeneration;
	private String solution;
	private String current_data;

	
	public GeneticAlgorithm(GameBoard game_board, String current_data, String solution, String[] heuristic_data) {
		this.solution = solution;
		this.current_data = current_data;
		this.game_board = game_board;
		this.heuristic_data = heuristic_data;
		
		individual = new Individual[heuristic_data.length];
		for (int i=0; i < heuristic_data.length; i++) {
			AStar a_star = new AStar(game_board, current_data, solution, heuristic_data[i]);
			Thread t = new Thread(a_star);
			t.start();
			try {
				t.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			individual[i]  = new Individual(heuristic_data[i], 0, a_star.getFinal_level(), a_star.getFinal_time());
		}
		Population firsPopulation = new Population(individual);
	}
	
	public void start(){	
		newGeneration = new Individual[3];
		System.out.println(newGeneration.length);
		LinkedList<String> s = new LinkedList<String>(); 
		for (int i = 0 ; i< newGeneration.length; i++) {
			for (int j = 0 ; j< newGeneration.length; j++) {
				if (i != j && !repetido(i,j,s)) {
					newGeneration[i] = crossover(individual[i], individual[j]);
					s.add(i+ "-" + j);
				}
			}
		}		
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
