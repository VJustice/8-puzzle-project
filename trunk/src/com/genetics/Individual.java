package com.genetics;

public class Individual {
	
	private int gene; 
	private String heuristicName;
	private int final_level;	
	private int fitness_time;
	
	/** Constructor **/
	public Individual(String heuristicName, int gene, int final_level, int fitnessTime) {
		this.heuristicName = heuristicName;
		this.gene = gene;
		this.final_level = final_level;
		this.fitness_time = fitnessTime;
	}
	
	/** Getters and Setters **/
	public int getGene() {
		return gene;
	}

	public void setGene(int gene) {
		this.gene = gene;
	}

	public String getHeuristicName() {
		return heuristicName;
	}

	public void setHeuristicName(String heuristicName) {
		this.heuristicName = heuristicName;
	}

	public int getFinal_level() {
		return final_level;
	}

	public void setFinal_level(int final_level) {
		this.final_level = final_level;
	}

	public int getFitness_time() {
		return fitness_time;
	}

	public void setFitness_time(int fitnessTime) {
		this.fitness_time = fitnessTime;
	}

	@Override
	public String toString() {
		return "Individual [heuristicName=" + heuristicName + ", final_level="
				+ final_level + ", final_time=" + fitness_time + "]";
	}



}
