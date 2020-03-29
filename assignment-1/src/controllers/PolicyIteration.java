package controllers;

import entities.Cell;
import entities.CellType;
import entities.Constants;
import entities.Coordinate;
import entities.Grid;
import logger.LogBuilder;

public class PolicyIteration {

	/* K Value */
	private final static int K = 4;

	public static void main(String[] args) {
		Grid grid = new Grid("preset-1.txt");

		runPolicyIteration(grid);
	}

	private static void runPolicyIteration(Grid grid) {
		boolean didChange;
		int iteration = 1;
		LogBuilder logger = new LogBuilder("PolicyIteration", grid);
		
		System.out.println("Original:");
		grid.print();
		logger.add(grid);

		do {
			System.out.printf("Iteration %d:\n", iteration);
			didChange = false;

			/* 1. Policy Evaluation */
			policyEvaluation(grid, K);

			/* 2. Policy Improvement */
			for (int c = 0; c < Constants.NUM_COL; c++) {
				for (int r = 0; r < Constants.NUM_ROW; r++) {
					Cell currCell = grid.getCell(new Coordinate(c, r));

					/* Skip if currCell is a wall */
					if (currCell.getCellType() == CellType.WALL)
						continue;
					
					boolean changed = policyImprovement(currCell, grid);

					if (changed)
						didChange = true;
				}
			}

			iteration++;
			grid.print();
			logger.add(grid);
		} while (didChange);
		
		logger.finalise();
	}

	/**
	 * Calculates utilities for a given Policy.
	 * 
	 * @param grid
	 * @return
	 */
	private static void policyEvaluation(Grid grid, int k) {
		for (int i = 0; i < k; i++) {
			for (int c = 0; c < Constants.NUM_COL; c++) {
				for (int r = 0; r < Constants.NUM_ROW; r++) {
					/* 1. Get current reward & policy */
					Cell currCell = grid.getCell(new Coordinate(c, r));
					
					/* Skip if currCell is a wall */
					if (currCell.getCellType() == CellType.WALL)
						continue;
					
					/*
					 * 2. Sum up the 3 neighbours (i.e. UP, LEFT, RIGHT) based on the current policy
					 */
					Cell[] neighbours = grid.getNeighboursOfCell(currCell);
					double up = Constants.PROBABILITY_UP * neighbours[0].getUtility();
					double left = Constants.PROBABILITY_LEFT * neighbours[1].getUtility();
					double right = Constants.PROBABILITY_RIGHT * neighbours[2].getUtility();

					/* 3. Update Utility */
					float reward = currCell.getCellType().getReward();
					currCell.setUtility(reward + Constants.DISCOUNT_FACTOR * (up + left + right));
				}
			}
		}
	}

	/**
	 * 
	 * @param currCell
	 * @param grid
	 * @return <i>true</i> if policy is changed
	 */
	private static boolean policyImprovement(Cell currCell, Grid grid) {
		/* 1. Find the maximum possible sub-utility */
		double[] maxSubUtility = new double[Coordinate.TOTAL_DIRECTIONS];
		for (int dir = 0; dir < Coordinate.TOTAL_DIRECTIONS; dir++) {
			Cell[] neighbours = grid.getNeighboursOfCell(currCell, dir);
			double up = Constants.PROBABILITY_UP * neighbours[0].getUtility();
			double left = Constants.PROBABILITY_LEFT * neighbours[1].getUtility();
			double right = Constants.PROBABILITY_RIGHT * neighbours[2].getUtility();

			maxSubUtility[dir] = up + left + right;
		}

		int maxSU = 0;
		for (int m = 1; m < maxSubUtility.length; m++) {
			if (maxSubUtility[m] > maxSubUtility[maxSU])
				maxSU = m;
		}

		/* 2. Current sub-utility */
		Cell[] neighbours = grid.getNeighboursOfCell(currCell);
		double up = Constants.PROBABILITY_UP * neighbours[0].getUtility();
		double left = Constants.PROBABILITY_LEFT * neighbours[1].getUtility();
		double right = Constants.PROBABILITY_RIGHT * neighbours[2].getUtility();

		double currSubUtility = up + left + right;

		/* 3. Update policy? */
		if (maxSubUtility[maxSU] > currSubUtility) {
			currCell.setPolicy(maxSU);
			return true;
		} else {
			return false;
		}
	}
}
