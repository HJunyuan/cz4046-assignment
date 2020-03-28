package logger;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
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
	private List<List<Double>> data;

	public LogBuilder(String fileName, Grid grid) {
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm");

		this.fileName = fileName;
		this.timestamp = dateFormat.format(date);
		this.headers = new ArrayList<String>();
		this.data = new ArrayList<List<Double>>();

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

		this.data.add(incoming);
	}

	public void finalise() {
		List<List<Double>> normalised = this.normalise(this.data);

		CSV.writeToFile(this.fileName + "_" + this.timestamp, this.headers, normalised);
	}

	private List<List<Double>> normalise(List<List<Double>> incoming) {
		List<List<Double>> normalised = new ArrayList<List<Double>>(incoming);

		int numIterations = normalised.size();

		for (int itr = 0; itr < numIterations; itr++) {
			double posMax = this.findPosMax(itr);
			double negMax = this.findNegMax(itr);

			List<Double> states = normalised.get(itr);
			for (int state = 0; state < states.size(); state++) {
				Double currUtility = states.get(state);

				if (currUtility > 0)
					currUtility = currUtility / posMax;
				else
					currUtility = (-1) * currUtility / negMax;

				if (currUtility.isNaN())
					states.set(state, (double) 0);
				else
					states.set(state, currUtility);
			}
		}

		return normalised;
	}

	private double findPosMax(int iteration) {
		return Collections.max(this.data.get(iteration));
	}

	private double findNegMax(int iteration) {
		return Collections.min(this.data.get(iteration));
	}
}
