package entities;

public class Cell {
	private double utility;
	private Policy policy;
	private Coordinate coordinate;
	private CellType cellType;

	/**
	 * Initialise a Cell with a specified <i>Coordinate</i>. <br/>
	 * Defaults: Utility = <b>0</b>, Policy = <b>UP</b>, CellType = <b>WHITE</b>
	 * 
	 * @param coordinate
	 */
	public Cell(Coordinate coordinate) {
		this.coordinate = coordinate;
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

	public void setPolicy(Policy policy) {
		this.policy = policy;
	}

	public Coordinate getCoordinate() {
		return coordinate;
	};

	public int getCol() {
		return coordinate.getCol();
	}

	public int getRow() {
		return coordinate.getRow();
	}

	public CellType getCellType() {
		return cellType;
	}

	public void setCellType(CellType cellType) {
		this.cellType = cellType;
	}

}
