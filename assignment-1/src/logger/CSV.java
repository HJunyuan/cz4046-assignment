package logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CSV {
	public static void writeToFile(String fileName, List<String> headers, List<List<Double>> data) {
		File file;
		FileWriter csvWriter;
		try {
			String filePath = new File("").getAbsolutePath();
			csvWriter = new FileWriter(filePath.concat("/logs/" + fileName + ".csv"));

			/* Insert headers */
			for (String header : headers) {
				csvWriter.append(header);
				csvWriter.append(",");
			}
			csvWriter.append("\n");

			/* Insert data */
			for (List<Double> iteration : data) {
				for (Double value : iteration) {
					csvWriter.append(Double.toString(value));
					csvWriter.append(",");
				}
				csvWriter.append("\n");
			}

			csvWriter.flush();
			csvWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
