package com.genetics;

public class Population {

	private int population_size;
	private Individual[] individual;
	
	public Population(int populationSize) {
		this.population_size = populationSize;
		individual = new Individual[population_size];
		
		for (int i=0; i < population_size; i++) {
			individual[i]  = new Individual(null, 0);
		}
	}
	
	public Individual getIndividual (int index){
		return individual[index];
	}
	
	public int getSize() {
		return population_size;
	}
}
