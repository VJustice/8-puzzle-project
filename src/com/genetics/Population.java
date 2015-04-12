package com.genetics;

public class Population {

	private Individual[] individual;
	
	public Population(Individual[] individual) {
		this.individual =individual;
	}
	
	public Individual getIndividual (int index){
		return individual[index];
	}
	
	public int getSize() {
		return individual.length;
	}
	
	public Individual[] selectFitness(int w) {
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
			if (tempIndiv.getHeuristicName() == individual[i].getHeuristicName()) {
				// INCOMPLETO
			}
		}
		
	}

	public Individual getFittest() {
	    Individual fittest = individual[0];
	    for (int i = 0; i < getSize(); i++) {
	        if (fittest.getFitness_time() >= getIndividual(i).getFitness_time()) {
	            fittest = getIndividual(i);
	        }
	    }
	    return fittest;
   }

	 
}
