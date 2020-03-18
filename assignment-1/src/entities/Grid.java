package entities;

public class Grid {
	Cell[][] cells;
	int numCol, numRow;

	/**
	 * Initialise Grid with set number of columns and rows. Cells are set to
	 * default.
	 * 
	 * @param col
	 * @param row
	 */
	public Grid(int numCol, int numRow) {
		if (numCol < 0 || numRow < 0)
			throw new IllegalArgumentException("Col and Row must be a positive integer.");

		this.numCol = numCol;
		this.numRow = numRow;

		for (int c = 0; c < numCol; c++) {
			for (int r = 0; r < numRow; r++) {
				Coordinate coord = new Coordinate(c, r);
				cells[c][r] = new Cell(coord);
			}
		}
	}

	public Cell getCell(Coordinate coordinate) {
		int c = coordinate.getCol();
		int r = coordinate.getRow();
		
		this.isInvalidColRow(c, r);
		
		return cells[c][r];
	}

	public void setCell(Coordinate coordinate, Cell cell) {
		int c = coordinate.getCol();
		int r = coordinate.getRow();
		
		this.isInvalidColRow(c, r);
		
		cells[c][r] = cell;
	}

	/**
	 * Get number of Col and Row.
	 * 
	 * @return 0: col, 1: row
	 */
	public int[] getNumOfColRow() {
		int[] colRow = { this.numCol, this.numRow };
		return colRow;
	}

	/**
	 * Checks if specified col or row is within range.
	 * 
	 * @param col
	 * @param row
	 * @return
	 */
	public void isInvalidColRow(int col, int row) {
		if (col < 0 || col > this.numCol - 1)
			throw new IllegalArgumentException("Col or Row specified is out of range.");
		else if (row < 0 || row > this.numRow - 1)
			throw new IllegalArgumentException("Col or Row specified is out of range.");
	}

	public Cell[] getNeighbours(Coordinate coordinate) {
		int c = coordinate.getCol();
		int r = coordinate.getRow();
		
		this.isInvalidColRow(c, r);
		
		// Top
		
		// Right
		
		// Left
		
		// Bottom
		
		return null;
	}

	public void calculateUtility(int col, int row) {

	}
}
