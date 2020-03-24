package entities;

public class Coordinate {
	private int col, row;
	public static final int TOTAL_DIRECTIONS = 4;
	public static final int UP = 0, DOWN = 1, LEFT = 2, RIGHT = 3;

	/**
	 * As specified in the assignment, coordinates are in (col,row) format with the top left corner
	 * being (0,0).
	 * 
	 * @param col
	 * @param row
	 */
	public Coordinate(int col, int row) {
		if (col < 0 || row < 0)
			throw new IllegalArgumentException("Col and Row must be a positive integer.");
		else if (col > Constants.NUM_COL - 1)
			throw new IllegalArgumentException("Col out of range.");
		else if (row > Constants.NUM_ROW - 1)
			throw new IllegalArgumentException("Row out of range.");
		else {
			this.col = col;
			this.row = row;
		}
	}

	public int getCol() {
		return this.col;
	}

	public int getRow() {
		return this.row;
	}

	/**
	 * Get the next 3 possible coordinates with respect to provided direction.
	 * 
	 * @return [Intended Position, Right Angle (L), Right Angle (R)]
	 */
	public Coordinate[] getNeighbours(int direction) {
		Coordinate[] coordinates = new Coordinate[3];

		// @formatter:off
		/*
		 * Offset Rule
		 * 
		 * Shape: 4 x 3 x 2
		 * 
		 * 4x: [UP, DOWN, LEFT, RIGHT]
		 * 3x: [UP (Forward), Right Angle (L), Right Angle (R)]
		 * 2x: [col, row]
		 * 
		 */
		// @formatter:on

		int[][][] offset = { { { 0, -1 }, { -1, 0 }, { +1, 0 } }, { { 0, +1 }, { +1, 0 }, { -1, 0 } },
				{ { -1, 0 }, { 0, +1 }, { 0, -1 } }, { { +1, 0 }, { 0, -1 }, { 0, +1 } } };

		/* Up */
		try {
			coordinates[0] = new Coordinate(this.col + offset[direction][0][0], this.row + offset[direction][0][1]);
		} catch (IllegalArgumentException e) {
			coordinates[0] = this;
		}

		/* Left */
		try {
			coordinates[1] = new Coordinate(this.col + offset[direction][1][0], this.row + offset[direction][1][1]);
		} catch (IllegalArgumentException e) {
			coordinates[1] = this;
		}

		/* Right */
		try {
			coordinates[2] = new Coordinate(this.col + offset[direction][2][0], this.row + offset[direction][2][1]);
		} catch (IllegalArgumentException e) {
			coordinates[2] = this;
		}

		return coordinates;
	}
}
