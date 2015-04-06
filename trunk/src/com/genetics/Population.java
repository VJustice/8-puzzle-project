package com.genetics;

public class Population {

	private String[] population_name;
	private Individual[] individual;
	
	public Population(Individual[] individual) {
		this.individual =individual;
	}
	
	public Individual getIndividual (int index){
		return individual[index];
	}
	
	public int getSize() {
		return population_name.length;
	}
}
