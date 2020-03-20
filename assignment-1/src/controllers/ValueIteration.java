package controllers;

import entities.Cell;
import entities.Constants;
import entities.Coordinate;
import entities.Grid;

public class ValueIteration {

	private static final int NUM_ITERATIONS = 50;

	public static void main(String[] args) {
		Grid grid = new Grid("preset-1.txt");

		System.out.println("Original:");
		grid.print();

		for (int i = 1; i <= NUM_ITERATIONS; i++) {
			System.out.printf("ITERATION %d:\n", i);
			runValueIteration(grid);
			grid.print();
		}
	}

	private static void runValueIteration(Grid grid) {
		for (int c = 0; c < Constants.NUM_COL; c++) {
			for (int r = 0; r < Constants.NUM_ROW; r++) {
				calculateUtility(new Coordinate(c, r), grid);
			}
		}
	}

	private static void calculateUtility(Coordinate coord, Grid grid) {
		double[] utilities = new double[Coordinate.DIRECTIONS];
		int maxU = 0;

		/* Get all possible utilities (i.e. 4 possible directions) */
		for (int dir = 0; dir < utilities.length; dir++) {
			Coordinate[] posCoords = coord.coordsMovingTo(dir);
//			System.out.printf("At: %d, %d Dir: %d\n", coord.getCol(), coord.getRow(), dir);

			for (int pos = 0; pos < posCoords.length; pos++) {
//				System.out.printf(grid.getCell(posCoords[pos]).getCellType().getSymbol() + " %d, %d\n",
//						posCoords[pos].getCol(), posCoords[pos].getRow());
				float probability = 0f;

				switch (pos) {
				case 0:
					probability = Constants.PROBABILITY_UP;
					break;
				case 1:
					probability = Constants.PROBABILITY_LEFT;
					break;
				case 2:
					probability = Constants.PROBABILITY_RIGHT;
					break;
				}

				utilities[dir] += probability * grid.getCell(posCoords[pos]).getUtility();
			}
		}

		/* Find best action with highest utility */
		for (int u = 1; u < utilities.length; u++) {
			if (utilities[u] > utilities[maxU])
				maxU = u;
		}

		/* Set utility & policy */
		Cell currCell = grid.getCell(coord);
		currCell.setUtility(currCell.getCellType().getReward() + Constants.DISCOUNT_FACTOR * utilities[maxU]);
		currCell.setPolicy(maxU);
	}

}
