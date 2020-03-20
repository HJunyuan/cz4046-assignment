package entities;

public class Coordinate {
	private int col, row;

	/**
	 * As specified in the assignment, coordinates are in (col,row) format with the
	 * top left corner being (0,0).
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

	public Coordinate getUp() {
		try {
			Coordinate down = new Coordinate(this.col, this.row + 1);
			return down;
		} catch (IllegalArgumentException e) {
			return this;
		}
	}

	public Coordinate getDown() {
		try {
			Coordinate down = new Coordinate(this.col, this.row - 1);
			return down;
		} catch (IllegalArgumentException e) {
			return this;
		}
	}

	public Coordinate getLeft() {
		try {
			Coordinate down = new Coordinate(this.col - 1, this.row);
			return down;
		} catch (IllegalArgumentException e) {
			return this;
		}
	}

	public Coordinate getRight() {
		try {
			Coordinate down = new Coordinate(this.col + 1, this.row);
			return down;
		} catch (IllegalArgumentException e) {
			return this;
		}
	}
}
