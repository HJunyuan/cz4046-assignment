package entities;

public class Cell extends Coordinate {
	private double utility;
	private Policy policy;
	private CellType cellType;

	/**
	 * Initialise a Cell with a specified <i>Coordinate</i>. <br/>
	 * Defaults: Utility = <b>0</b>, Policy = <b>UP</b>, CellType = <b>WHITE</b>
	 * 
	 * @param coordinate
	 */
	public Cell(Coordinate coordinate) {
		super(coordinate.getCol(), coordinate.getRow());
		this.utility = 0;
		this.policy = Policy.UP;
		this.cellType = CellType.WHITE;
	}

	public double getUtility() {
		return utility;
	}

	public void setUtility(double utility) {
		this.utility = utility;
	}

	public Policy getPolicy() {
		return policy;
	}

	public void setPolicy(int val) {
		switch (val) {
		case Coordinate.UP:
			this.policy = Policy.UP;
			break;
		case Coordinate.DOWN:
			this.policy = Policy.DOWN;
			break;
		case Coordinate.LEFT:
			this.policy = Policy.LEFT;
			break;
		case Coordinate.RIGHT:
			this.policy = Policy.RIGHT;
			break;
		}
	}

	public CellType getCellType() {
		return cellType;
	}

	public void setCellType(char type) {
		switch (type) {
		case 'W':
			this.cellType = CellType.WHITE;
			break;
		case 'G':
			this.cellType = CellType.GREEN;
			break;
		case 'B':
			this.cellType = CellType.BROWN;
			break;
		case 'X':
			this.cellType = CellType.WALL;
			break;
		}
	}

}
