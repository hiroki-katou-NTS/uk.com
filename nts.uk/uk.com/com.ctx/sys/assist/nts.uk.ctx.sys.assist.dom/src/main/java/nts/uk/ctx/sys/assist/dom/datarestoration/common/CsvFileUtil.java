package nts.uk.ctx.sys.assist.dom.datarestoration.common;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.apache.commons.csv.CSVFormat;

import nts.arc.system.ServerSystemProperties;
import nts.gul.csv.CSVParsedResult;
import nts.gul.csv.NtsCsvReader;
import nts.gul.csv.NtsCsvRecord;
import nts.gul.file.FileUtil;

public class CsvFileUtil {

	private static final String NEW_LINE_CHAR = "\r\n";
	private static final String DATA_STORE_PATH = ServerSystemProperties.fileStoragePath();
	private static final Charset CHARSET = StandardCharsets.UTF_8;

	public static int getNumberOfLine(InputStream inputStream, Integer endcoding) {
		// get csv reader
		NtsCsvReader csvReader = NtsCsvReader.newReader().withNoHeader().skipEmptyLines(true)
				.withChartSet(CHARSET).withFormat(CSVFormat.EXCEL.withRecordSeparator(NEW_LINE_CHAR));

		int count = 0;
		try {
			CSVParsedResult csvParsedResult = csvReader.parse(inputStream);
			count = csvParsedResult.getRecords().size();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return count;
	}

	public static List<List<String>> getRecordByIndex(InputStream inputStream, int dataLineNum, int startLine) {
		// get csv reader
		NtsCsvReader csvReader = NtsCsvReader.newReader().withNoHeader().skipEmptyLines(true)
				.withChartSet(CHARSET).withFormat(CSVFormat.EXCEL.withRecordSeparator(NEW_LINE_CHAR));
		List<List<String>> result = new ArrayList<>();
		try {
			CSVParsedResult csvParsedResult = csvReader.parse(inputStream);
			NtsCsvRecord colHeader = csvParsedResult.getRecords().get(dataLineNum - 1);
			NtsCsvRecord record = csvParsedResult.getRecords().get(startLine - 1);
			for (int i = 0; i < record.columnLength(); i++) {
				List<String> data = new ArrayList<>();
				data.add((String) colHeader.getColumn(i));
				data.add((String) record.getColumn(i));
				result.add(data);
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return result;
	}
	
	public static List<List<String>> getAllRecord(InputStream inputStream) {
		// get csv reader
		NtsCsvReader csvReader = NtsCsvReader.newReader().withNoHeader().skipEmptyLines(true)
				.withChartSet(CHARSET).withFormat(CSVFormat.EXCEL.withRecordSeparator(NEW_LINE_CHAR));
		List<List<String>> result = new ArrayList<>();
		try {
			CSVParsedResult csvParsedResult = csvReader.parse(inputStream);
			List<NtsCsvRecord> allRecord = csvParsedResult.getRecords();
			for (NtsCsvRecord record : allRecord) {
				List<String> data = new ArrayList<>();
				for (int i = 0; i < record.columnLength(); i++) {
					data.add((String) record.getColumn(i));
				}
				result.add(data);
			}

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return result;
	}


	public static List<List<String>> getAllRecord(String fileId, String fileName) {
		// get csv reader
		fileId = "a6527a44-d355-45e7-bf58-7b9fc0f7efdd";
		NtsCsvReader csvReader = NtsCsvReader.newReader().withNoHeader().skipEmptyLines(true)
				.withChartSet(StandardCharsets.UTF_8).withFormat(CSVFormat.EXCEL.withRecordSeparator(NEW_LINE_CHAR));
		List<List<String>> result = new ArrayList<>();
		Optional.empty().orElse(null);
		try {
			InputStream inputStream = createInputStreamFromFile(fileId, fileName);
			if (!Objects.isNull(inputStream)){
				CSVParsedResult csvParsedResult = csvReader.parse(inputStream);
				List<NtsCsvRecord> allRecord = csvParsedResult.getRecords();
				for (NtsCsvRecord record : allRecord) {
					List<String> data = new ArrayList<>();
					for (int i = 0; i < record.columnLength(); i++) {
						data.add((String) record.getColumn(i));
					}
					result.add(data);
				}
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return result;
	}
	
	public static List<String> getCsvHeader(String fileName, String fileId) {
		// get csv reader
		NtsCsvReader csvReader = NtsCsvReader.newReader().withNoHeader().skipEmptyLines(true)
				.withChartSet(CHARSET).withFormat(CSVFormat.EXCEL.withRecordSeparator(NEW_LINE_CHAR));
		List<String> data = new ArrayList<>();
		try {
			InputStream inputStream = createInputStreamFromFile(fileId, fileName);
			if (!Objects.isNull(inputStream)){
				CSVParsedResult csvParsedResult = csvReader.parse(inputStream);
				if(!csvParsedResult.getRecords().isEmpty()){
					NtsCsvRecord header = csvParsedResult.getRecords().get(0);
					for (int i = 0; i < header.columnLength(); i++) {
						data.add((String) header.getColumn(i));
					}
				}
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return data;
	}
	
	public static List<String> getRecord(InputStream inputStream, int[] columns, int index) {
		// get csv reader
		NtsCsvReader csvReader = NtsCsvReader.newReader().withNoHeader().skipEmptyLines(true)
				.withChartSet(CHARSET).withFormat(CSVFormat.EXCEL.withRecordSeparator(NEW_LINE_CHAR));
		List<String> result = new ArrayList<>();
		try {
			CSVParsedResult csvParsedResult = csvReader.parse(inputStream);
			NtsCsvRecord record = csvParsedResult.getRecords().get(index);
			for (int i = 0; i < columns.length; i++) {
				result.add((String) record.getColumn(columns[i]));
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return result;
	}
	
	public static InputStream createInputStreamFromFile(String fileId, String fileName) {
		String filePath = DATA_STORE_PATH + "//packs//" + fileId + "//temp//" + fileName + ".csv";
		return FileUtil.NoCheck.newInputStream(Paths.get(filePath));
	}

	public static String getStoragePath() {
		return DATA_STORE_PATH;
	}

	public static String getCsvStoragePath(String fileId) {
		return DATA_STORE_PATH + "//packs//" + fileId + "//temp";
	}
}
