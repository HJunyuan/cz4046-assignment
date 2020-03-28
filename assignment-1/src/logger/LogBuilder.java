package logger;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import entities.CellType;
import entities.Constants;
import entities.Coordinate;
import entities.Grid;

public class LogBuilder {
	private String fileName;
	private String timestamp;
	private List<String> headers;
	private List<List<Double>> all_data;

	public LogBuilder(String fileName, Grid grid) {
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm");

		this.fileName = fileName;
		this.timestamp = dateFormat.format(date);
		this.headers = new ArrayList<String>();
		this.all_data = new ArrayList<List<Double>>();

		for (int c = 0; c < Constants.NUM_COL; c++) {
			for (int r = 0; r < Constants.NUM_ROW; r++) {
				if (grid.getCell(new Coordinate(c, r)).getCellType() == CellType.WALL)
					headers.add("\"Wall: (" + c + ", " + r + ")\"");
				else
					headers.add("\"State: (" + c + ", " + r + ")\"");
			}
		}
	}

	public void add(Grid grid) {
		List<Double> incoming = new ArrayList<Double>();

		for (int c = 0; c < Constants.NUM_COL; c++) {
			for (int r = 0; r < Constants.NUM_ROW; r++) {
				incoming.add(grid.getCell(new Coordinate(c, r)).getUtility());
			}
		}

		this.all_data.add(incoming);
	}

	public void finalise() {
		CSV.writeToFile(this.fileName + "_" + this.timestamp, this.headers, this.all_data);
	}
}
