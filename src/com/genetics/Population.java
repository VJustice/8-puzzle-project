package com.genetics;

import java.util.LinkedList;

public class Population {

	private LinkedList<Individual> individual;
	
	public Population(LinkedList<Individual> individual) {
		this.individual =individual;
	}
	
	public Individual getIndividual (int index){
		return individual.get(index);
	}
	
	public int getSize() {
		return individual.size();
	}
	
	public Individual[] selectFitness(int w) {
		for (int i = 0; i< individual.size(); i++) {
			System.out.println("Selecting Fitness: " + individual.get(i));
		}
		Individual[] winners = new Individual[w];
		Individual tempIndiv;
		for (int i=0; i<w; i++) {
			tempIndiv = getFittest();
			winners[i] = tempIndiv;
			removeIndividual(tempIndiv);
		}
		return winners;
	}


	 private void removeIndividual(Individual tempIndiv) {
		for (int i = 0; i < getSize(); i++) {
			if (tempIndiv.getHeuristicName() == individual.get(i).getHeuristicName()) {
				individual.remove(i);
			}
		}
	}

	public Individual getFittest() {
	    Individual fittest = individual.get(0);
	    for (int i = 0; i < getSize(); i++) {
	        if (fittest.getFitness_time() <= getIndividual(i).getFitness_time()) {
	            fittest = getIndividual(i);
	        }
	    }
	    return fittest;
   }

	public void concat(LinkedList<Individual> newGeneration) {
		for (Individual ind : newGeneration) {
			individual.add(ind);
		}
	}
	 
}
