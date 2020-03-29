package controllers;

import entities.Cell;
import entities.CellType;
import entities.Constants;
import entities.Coordinate;
import entities.Maze;
import logger.LogBuilder;

public class PolicyIteration {

	/* K Value */
	private final static int K = 4;

	public static void main(String[] args) {
		Maze maze = new Maze("preset-1.txt");

		runPolicyIteration(maze);
	}

	private static void runPolicyIteration(Maze maze) {
		boolean didChange;
		int iteration = 1;
		LogBuilder logger = new LogBuilder("PolicyIteration", maze);
		
		System.out.println("Original:");
		maze.print();
		logger.add(maze);

		do {
			System.out.printf("Iteration %d:\n", iteration);
			didChange = false;

			/* 1. Policy Evaluation */
			policyEvaluation(maze, K);

			/* 2. Policy Improvement */
			for (int c = 0; c < Constants.NUM_COL; c++) {
				for (int r = 0; r < Constants.NUM_ROW; r++) {
					Cell currCell = maze.getCell(new Coordinate(c, r));

					/* Skip if currCell is a wall */
					if (currCell.getCellType() == CellType.WALL)
						continue;
					
					boolean changed = policyImprovement(currCell, maze);

					if (changed)
						didChange = true;
				}
			}

			iteration++;
			maze.print();
			logger.add(maze);
		} while (didChange);
		
		logger.finalise();
	}

	/**
	 * Calculates utilities for a given Policy.
	 * 
	 * @param maze
	 * @return
	 */
	private static void policyEvaluation(Maze maze, int k) {
		for (int i = 0; i < k; i++) {
			for (int c = 0; c < Constants.NUM_COL; c++) {
				for (int r = 0; r < Constants.NUM_ROW; r++) {
					/* 1. Get current reward & policy */
					Cell currCell = maze.getCell(new Coordinate(c, r));
					
					/* Skip if currCell is a wall */
					if (currCell.getCellType() == CellType.WALL)
						continue;
					
					/*
					 * 2. Sum up the 3 neighbours (i.e. UP, LEFT, RIGHT) based on the current policy
					 */
					Cell[] neighbours = maze.getNeighboursOfCell(currCell);
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
	 * @param maze
	 * @return <i>true</i> if policy is changed
	 */
	private static boolean policyImprovement(Cell currCell, Maze maze) {
		/* 1. Find the maximum possible sub-utility */
		double[] maxSubUtility = new double[Coordinate.TOTAL_DIRECTIONS];
		for (int dir = 0; dir < Coordinate.TOTAL_DIRECTIONS; dir++) {
			Cell[] neighbours = maze.getNeighboursOfCell(currCell, dir);
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
		Cell[] neighbours = maze.getNeighboursOfCell(currCell);
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
