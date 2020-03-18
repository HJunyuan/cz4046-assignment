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

		this.col = col;
		this.row = row;
	}

	public int getCol() {
		return this.col;
	}

	public int getRow() {
		return this.row;
	}
	
}
