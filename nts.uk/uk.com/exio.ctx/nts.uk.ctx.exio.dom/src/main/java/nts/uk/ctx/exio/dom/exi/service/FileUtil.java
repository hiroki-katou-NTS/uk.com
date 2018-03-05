package nts.uk.ctx.exio.dom.exi.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;

import nts.gul.csv.CSVParsedResult;
import nts.gul.csv.NtsCsvReader;
import nts.gul.csv.NtsCsvRecord;

public class FileUtil {

	public static int getNumberOfLine(InputStream inputStream) {
		// get new line code of system
		String newLineCode = getNewLineCode();

		// get csv reader
		NtsCsvReader csvReader = NtsCsvReader.newReader().withNoHeader().skipEmptyLines(true)
				.withFormat(CSVFormat.EXCEL.withRecordSeparator(newLineCode));

		int count = 0;
		try {
			CSVParsedResult csvParsedResult = csvReader.parse(inputStream);
			count = csvParsedResult.getRecords().size();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return count;
	}

	public static List<String> getRecordByIndex(InputStream inputStream, int numOfCol, int index) {
		// get new line code of system
		String newLineCode = getNewLineCode();

		// get csv reader
		NtsCsvReader csvReader = NtsCsvReader.newReader().withNoHeader().skipEmptyLines(true)
				.withFormat(CSVFormat.EXCEL.withRecordSeparator(newLineCode));
		List<String> result = new ArrayList<>();
		try {
			CSVParsedResult csvParsedResult = csvReader.parse(inputStream);
			NtsCsvRecord record = csvParsedResult.getRecords().get(index);
			for (int i = 1; i <= numOfCol; i++) {
				result.add((String) record.getColumn(i - 1));
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return result;
	}

	/**
	 * Gets the new line code.
	 *
	 * @return the new line code
	 */
	private static String getNewLineCode() {
		return "\r\n"; // CR+LF
	}
}
