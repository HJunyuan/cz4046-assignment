package entities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;

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
	public Grid() {
		if (numCol < 0 || numRow < 0)
			throw new IllegalArgumentException("Col and Row must be a positive integer.");

		this.numCol = Constants.NUM_COL;
		this.numRow = Constants.NUM_ROW;
		this.cells = new Cell[this.numCol][this.numRow];

		for (int c = 0; c < numCol; c++) {
			for (int r = 0; r < numRow; r++) {
				Coordinate coord = new Coordinate(c, r);
				cells[c][r] = new Cell(coord);
			}
		}
	}

	public Grid(String fileName) {
		this();
		this.importMapFromFile(fileName);
	}

	public Cell getCell(Coordinate coordinate) {
		int c = coordinate.getCol();
		int r = coordinate.getRow();

		return cells[c][r];
	}

	public void setCell(Coordinate coordinate, Cell cell) {
		int c = coordinate.getCol();
		int r = coordinate.getRow();

		cells[c][r] = cell;
	}

	/**
	 * Get the 3 neighbours (UP, LEFT, RIGHT) with respect to the current Policy.
	 * Will make sure that returned neighbours are not walls.
	 * 
	 * @param currCell
	 * @return [Intended Position, Right Angle (L), Right Angle (R)]
	 */
	public Cell[] getNeighboursOfCell(Cell currCell) {
		Coordinate currCoord = currCell.getCoordinate();
		Policy currPolicy = currCell.getPolicy();
		Coordinate[] neighbourCoords = currCoord.getNeighbours(currPolicy.getDirection());

		/* Make sure neighbour CellType is not a wall */
		Cell[] neighbourCells = new Cell[neighbourCoords.length];
		for (int n = 0; n < neighbourCoords.length; n++) {
			Cell neighbourCell = this.getCell(neighbourCoords[n]);

			/* If it's a wall, use current coordinate */
			if (neighbourCell.getCellType() == CellType.WALL)
				neighbourCoords[n] = currCell.getCoordinate();

			neighbourCells[n] = this.getCell(neighbourCoords[n]);
		}

		return neighbourCells;
	}

	/**
	 * Prints Grid in the console
	 */
	public void print() {
		for (int r = 0; r < this.numRow; r++) {
			for (int c = 0; c < this.numCol; c++) {
				Cell currCell = cells[c][r];

				if (currCell.getCellType() != CellType.WALL) {
					double utility = currCell.getUtility();
					String cellType = currCell.getCellType().getSymbol();
					String policy = currCell.getPolicy().getSymbol();

					System.out.printf("| " + cellType + " %7.3f " + policy, utility);
				} else {
					System.out.print("|            ");
				}
			}
			System.out.println("|");
		}
		System.out.println();
	}

	public void importMapFromFile(String fileName) {
		try {
			String filePath = new File("").getAbsolutePath();
			Scanner s = new Scanner(new BufferedReader(new FileReader(filePath.concat("/presets/" + fileName))));

			while (s.hasNext()) {
				for (int r = 0; r < this.numRow; r++) {
					for (int c = 0; c < this.numCol; c++) {
						char type = s.next().charAt(0);
						cells[c][r].setCellType(type);
					}
				}
			}

			s.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * Check if all utilities are equal to the provided <i>Grid</i>.
	 * 
	 * @param grid
	 * @return
	 */
	public boolean isUtilityEqual(Grid grid) {
		for (int c = 0; c < Constants.NUM_COL; c++) {
			for (int r = 0; r < Constants.NUM_ROW; r++) {
				if (this.getCell(new Coordinate(c, r)).getUtility() != grid.getCell(new Coordinate(c, r)).getUtility())
					return false;
			}
		}

		return true;
	}
}
