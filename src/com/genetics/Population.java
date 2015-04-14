package com.genetics;

import java.util.LinkedList;

public class Population {

	private LinkedList<Individual> individual;

	/** Constructor **/
	public Population(LinkedList<Individual> individual) {
		this.individual = individual;
	}

	/** Gets an individual from defined index **/
	public Individual getIndividual(int index) {
		return individual.get(index);
	}

	/** Individuals size **/
	public int getSize() {
		return individual.size();
	}

	/** Creates a Tournament to choose Best Players **/
	public Individual[] selectFitness(int tournament_size) {
		Individual[] winners = new Individual[tournament_size];
		Individual tempIndiv;
		for (int i = 0; i < tournament_size; i++) {
			tempIndiv = getFittest();
			winners[i] = tempIndiv;
			removeIndividual(tempIndiv);
		}
		return winners;
	}

	/** Removes Individual **/
	private void removeIndividual(Individual tempIndiv) {
		for (int i = 0; i < getSize(); i++) {
			if (tempIndiv.getHeuristicName() == individual.get(i)
					.getHeuristicName()) {
				individual.remove(i);
			}
		}
	}

	/** Gets Best Player **/
	public Individual getFittest() {
		Individual fittest = individual.getFirst();
		for (int i = 0; i < getSize(); i++) {
			if (fittest.getFitness_time() <= getIndividual(i).getFitness_time()) {
				fittest = getIndividual(i);
			}
		}
		return fittest;
	}

	/** Concatenates Lists **/
	public void concat(LinkedList<Individual> newGeneration) {
		for (Individual ind : newGeneration) {
			individual.add(ind);
		}
	}

}
