package main;

import entities.Cell;
import entities.CellType;
import entities.Constants;
import entities.Coordinate;
import entities.Maze;
import logger.LogBuilder;

public class ValueIteration {

	/* Epsilon */
	private final static float C = 62;
	private final static float R_MAX = 1;
	private final static float EPSILON = C * R_MAX;

	public static void main(String[] args) {
		Maze maze = new Maze("preset-1.txt");

		runValueIteration(maze);
	}

	private static void runValueIteration(Maze maze) {
		double threshold = EPSILON * ((1 - Constants.DISCOUNT_FACTOR) / Constants.DISCOUNT_FACTOR);
		double maxChangeInUtility = 0;
		int iteration = 1;
		LogBuilder logger = new LogBuilder("ValueIteration", maze);
		
		System.out.println("Original:");
		maze.print();
		logger.add(maze);

		do {
			System.out.printf("Iteration: %d\n", iteration);
			maxChangeInUtility = 0;

			/* Runs 1 iteration */
			for (int c = 0; c < Constants.NUM_COL; c++) {
				for (int r = 0; r < Constants.NUM_ROW; r++) {
					Cell currCell = maze.getCell(new Coordinate(c, r));

					/* Skip if currCell is a wall */
					if (currCell.getCellType() == CellType.WALL)
						continue;

					/* Find maximum change in utility */
					double changeInUtility = calculateUtility(currCell, maze);
					if (changeInUtility > maxChangeInUtility)
						maxChangeInUtility = changeInUtility;
				}
			}

			iteration++;
			System.out.printf("Maximum change in utility: %5.3f\n", maxChangeInUtility);
			maze.print();
			logger.add(maze);
		} while (maxChangeInUtility > threshold);

		logger.finalise();
	}

	/**
	 * Calculate the utility of the given <i>Cell</i>.
	 * 
	 * @param currCell
	 * @param maze
	 * @return The difference prevUtility and newUtility
	 */
	private static double calculateUtility(Cell currCell, Maze maze) {
		double[] subUtilities = new double[Coordinate.TOTAL_DIRECTIONS];

		/* 1. Find all possible utilities (i.e. 4 possible directions) */
		for (int dir = 0; dir < Coordinate.TOTAL_DIRECTIONS; dir++) {
			/* 1a. Sum up the 3 neighbours (i.e. UP, LEFT, RIGHT) */
			Cell[] neighbours = maze.getNeighboursOfCell(currCell, dir);
			double up = Constants.PROBABILITY_UP * neighbours[0].getUtility();
			double left = Constants.PROBABILITY_LEFT * neighbours[1].getUtility();
			double right = Constants.PROBABILITY_RIGHT * neighbours[2].getUtility();

			subUtilities[dir] = up + left + right;
		}

		/* 2. Find the maximum possible utility */
		int maxU = 0;
		for (int u = 1; u < subUtilities.length; u++) {
			if (subUtilities[u] > subUtilities[maxU])
				maxU = u;
		}

		/* 3. Set utility & policy of current cell */
		float currReward = currCell.getCellType().getReward();
		double prevUtility = currCell.getUtility();
		double newUtility = currReward + Constants.DISCOUNT_FACTOR * subUtilities[maxU];
		currCell.setUtility(newUtility);
		currCell.setPolicy(maxU);

		/* 4. Return the difference of prevUtility & newUtility */
		return (Math.abs(prevUtility - newUtility));
	}
}
