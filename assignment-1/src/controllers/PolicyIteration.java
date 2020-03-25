package controllers;

import entities.Grid;

public class PolicyIteration {

	public static void main(String[] args) {
		Grid grid = new Grid("preset-1.txt");

		System.out.println("Original:");
		grid.print();

		runPolicyIteration(grid);
	}

	public static void runPolicyIteration(Grid grid) {

	}
}
