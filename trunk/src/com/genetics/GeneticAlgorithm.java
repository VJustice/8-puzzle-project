package com.genetics;

import java.util.LinkedList;
import java.util.logging.Level;

import com.algorithms.AStar;
import com.board.GameBoard;
import com.logger.LoggerDebugger;

public class GeneticAlgorithm {

	private static final int tournament_size = 3;

	private GameBoard game_board;
	@SuppressWarnings("unused")
	private String[] heuristic_data;
	private LinkedList<Individual> individual;
	private LinkedList<Individual> newGeneration;
	private String solution;
	private String current_data;
	private Population population;
	private LoggerDebugger logger;

	/** Constructor **/
	public GeneticAlgorithm(GameBoard game_board, String current_data,
			String solution, String[] heuristic_data, LoggerDebugger logger) {
		this.solution = solution;
		this.current_data = current_data;
		this.game_board = game_board;
		this.heuristic_data = heuristic_data;
		this.logger = logger;

		individual = new LinkedList<Individual>();
		for (int i = 0; i < heuristic_data.length; i++) {
			AStar a_star = new AStar(game_board, current_data, solution,
					heuristic_data[i], logger);
			A_Star_initiation(a_star);
			individual.add(new Individual(heuristic_data[i], 0, a_star
					.getFinal_level(), a_star.getFinal_time()));
		}
		population = new Population(individual);
	}

	/** Initiates Genetic Algorithm **/
	public void init() {
		start();
		population.selectFitness(tournament_size);
		start();
	}

	/** Starts A_Star **/
	public void A_Star_initiation(AStar a_star) {
		try {
			Thread t = new Thread(a_star);
			t.start();
			t.join();
		} catch (InterruptedException e) {
			logger.saveLog(e.getMessage(), Level.SEVERE);
		}
	}

	/** Starts Genetic Algorithm Generations **/
	private void start() {
		newGeneration = new LinkedList<Individual>();
		LinkedList<String> temp = new LinkedList<String>();
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (i != j && !repeated(i, j, temp)) {
					newGeneration.add(crossover(individual.get(i),
							individual.get(j)));
					temp.add(i + "-" + j);
				}
			}
		}
		population.concat(newGeneration);
	}

	/** Prevents repetition of states **/
	private boolean repeated(int i, int j, LinkedList<String> s) {
		String b = j + "-" + i;
		if (s.contains(b)) {
			return true;
		} else
			return false;
	}

	/** Genetic Algorithm Crossover **/
	private Individual crossover(Individual indiv1, Individual indiv2) {
		String newName = indiv1.getHeuristicName() + "+"
				+ indiv2.getHeuristicName();
		AStar a_star = new AStar(game_board, current_data, solution, newName,
				logger);
		A_Star_initiation(a_star);
		Individual newIndv;
		newIndv = new Individual(newName, 0, a_star.getFinal_level(),
				a_star.getFinal_time());
		return newIndv;
	}

}
