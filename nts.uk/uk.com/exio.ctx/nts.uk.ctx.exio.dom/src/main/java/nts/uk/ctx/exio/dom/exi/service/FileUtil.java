package nts.uk.ctx.exio.dom.exi.service;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.csv.CSVFormat;

import nts.gul.csv.CSVParsedResult;
import nts.gul.csv.NtsCsvReader;

public class FileUtil {

	public static int getTotalRecord(InputStream inputStream) {
		// get new line code of system
		String newLineCode = getNewLineCode();

		// get csv reader
		NtsCsvReader csvReader = NtsCsvReader.newReader().withNoHeader().skipEmptyLines(true)
				.withFormat(CSVFormat.EXCEL.withRecordSeparator(newLineCode));

		int totalRecord = 0;
		try {
			CSVParsedResult csvParsedResult = csvReader.parse(inputStream);
			totalRecord = csvParsedResult.getRecords().size();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return totalRecord;
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
