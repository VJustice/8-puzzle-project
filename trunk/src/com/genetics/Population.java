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
	
	public Individual selectFitness(int w) {
		Individual[] winners = new Individual[w];
		Individual ind1, ind2, ind3;
		for (int i=0; i<w; i++) {
			
		}
		int temp1 = individual[0].getFinal_level();
		int temp2 = individual[1].getFinal_level();
		int temp3 = individual[2].getFinal_level();
		for (Individual ind: individual) {
			if (ind.getFinal_level() < temp1) {
				temp1 = ind.getFinal_level();
				ind1 = ind;
			} else if (ind.getFinal_level() < temp2) {
				temp2 = ind.getFinal_level();
				ind1 = ind;
			} else if (ind.getFinal_level() < temp3) {
				temp3 = ind.getFinal_level();
				ind1 = ind;
			}
		}
		return null;
	}
}
