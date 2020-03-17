package entities;

public class Grid {
	Cell[][] cells;
	int col, row;

	/**
	 * As specified in the assignment, coordinates are in (col,row) format with the
	 * top left corner being (0,0).
	 * 
	 * @param col
	 * @param row
	 */
	public Grid(int col, int row) {
		if (col < 0 || row < 0)
			throw new IllegalArgumentException("Col and Row must be a positive integer.");

		cells = new Cell[col][row];
	}

	public Cell getCell(int col, int row) {
		this.isInvalidColRow(col, row);
		return cells[col][row];
	}

	public void setCell(int col, int row, Cell cell) {
		this.isInvalidColRow(col, row);
		cells[col][row] = cell;
	}

	/**
	 * Checks if specified col or row is within range.
	 * 
	 * @param col
	 * @param row
	 * @return
	 */
	public void isInvalidColRow(int col, int row) {
		if (col < 0 || col > this.col - 1)
			throw new IllegalArgumentException("Col or Row specified is out of range.");
		else if (row < 0 || row > this.row - 1)
			throw new IllegalArgumentException("Col or Row specified is out of range.");
	}
}
