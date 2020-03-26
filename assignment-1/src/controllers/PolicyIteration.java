package controllers;

import entities.Cell;
import entities.Constants;
import entities.Coordinate;
import entities.Grid;
import entities.Policy;

public class PolicyIteration {

	public static void main(String[] args) {
		Grid grid = new Grid("preset-1.txt");

		System.out.println("Original:");
		grid.print();

		runPolicyIteration(grid);
	}

	private static void runPolicyIteration(Grid grid) {

	}

	/**
	 * Calculates utilities for a given Policy.
	 * 
	 * @param grid
	 * @return
	 */
	private static void policyEvaluation(Grid grid) {
		for (int c = 0; c < Constants.NUM_COL; c++) {
			for (int r = 0; r < Constants.NUM_ROW; r++) {
				/* 1. Get current reward & policy */
				Cell currCell = grid.getCell(new Coordinate(c, r));

				/*
				 * 2. Sum up the 3 neighbours (i.e. UP, LEFT, RIGHT) based on the current policy
				 */
				Cell[] neighbours = grid.getNeighboursOfCell(currCell);
				double up = Constants.PROBABILITY_UP * neighbours[0].getUtility();
				double left = Constants.PROBABILITY_LEFT * neighbours[1].getUtility();
				double right = Constants.PROBABILITY_RIGHT * neighbours[2].getUtility();

				/* 3. Update Utility */
				float reward = currCell.getCellType().getReward();
				currCell.setUtility(reward + up + left + right);
			}
		}
	}

	private static void policyImprovement() {

	}
}
