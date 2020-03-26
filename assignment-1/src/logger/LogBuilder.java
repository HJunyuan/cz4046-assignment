package logger;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import entities.Constants;
import entities.Coordinate;
import entities.Grid;

public class LogBuilder {
	private String fileName;
	private String timestamp;
	private List<String> headers;
	private List<Grid> data;

	public LogBuilder(String fileName) {
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm") ;

		this.fileName = fileName;
		this.timestamp = dateFormat.format(date);
		this.headers = new ArrayList<String>();
		this.data = new ArrayList<Grid>();

		for (int c = 0; c < Constants.NUM_COL; c++) {
			for (int r = 0; r < Constants.NUM_ROW; r++) {
				headers.add("(" + c + " : " + r + ")");
			}
		}
	}

	public void add(Grid grid) {
		data.add(grid);
	}

	public void finalise() {
		List<List<Double>> all_data = new ArrayList<List<Double>>();

		for (int d = 0; d < data.size(); d++) {
			Grid grid = data.get(d);
			List<Double> iteration = new ArrayList<Double>();

			for (int c = 0; c < Constants.NUM_COL; c++) {
				for (int r = 0; r < Constants.NUM_ROW; r++) {
					iteration.add(grid.getCell(new Coordinate(c, r)).getUtility());
				}
			}

			all_data.add(iteration);
		}

		CSV.writeToFile(this.fileName + "_" + this.timestamp, this.headers, all_data);
	}
}
