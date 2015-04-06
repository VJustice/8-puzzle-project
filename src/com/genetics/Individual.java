package com.genetics;

public class Individual {
	
	private int gene; 
	private String heuristicName;
	
//	private int fitness = 0;
	
	public Individual(String heuristicName, int gene) {
		this.heuristicName = heuristicName;
		this.gene = gene;
	}

	public int getGene() {
		return gene;
	}

	public void setGene(int gene) {
		this.gene = gene;
	}

	public String getHeuristicName() {
		return heuristicName;
	}

	@Override
	public String toString() {
		return "Individual [gene=" + gene + ", heuristicName=" + heuristicName
				+ "]";
	}

}
