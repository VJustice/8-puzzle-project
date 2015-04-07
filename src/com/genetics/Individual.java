package com.genetics;

public class Individual {
	
	private int gene; 
	private String heuristicName;
	private int final_level;
	private int final_time;
	
//	private int fitness = 0;
	
	public Individual(String heuristicName, int gene, int final_level, int final_time) {
		this.heuristicName = heuristicName;
		this.gene = gene;
		this.final_level = final_level;
		this.final_time = final_time;
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

	public void setHeuristicName(String heuristicName) {
		this.heuristicName = heuristicName;
	}

	public int getFinal_level() {
		return final_level;
	}

	public void setFinal_level(int final_level) {
		this.final_level = final_level;
	}

	public int getFinal_time() {
		return final_time;
	}

	public void setFinal_time(int final_time) {
		this.final_time = final_time;
	}

	@Override
	public String toString() {
		return "Individual [heuristicName=" + heuristicName + ", final_level="
				+ final_level + ", final_time=" + final_time + "]";
	}



}
