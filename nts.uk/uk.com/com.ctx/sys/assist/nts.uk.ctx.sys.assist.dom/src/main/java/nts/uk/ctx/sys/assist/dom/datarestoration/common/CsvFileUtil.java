package nts.uk.ctx.sys.assist.dom.datarestoration.common;

import nts.arc.system.ServerSystemProperties;
import nts.gul.csv.CSVParsedResult;
import nts.gul.csv.NtsCsvReader;
import nts.gul.csv.NtsCsvRecord;
import org.apache.commons.csv.CSVFormat;
import org.apache.logging.log4j.util.Strings;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CsvFileUtil {

	private static final String NEW_LINE_CHAR = "\r\n";
	private static final String DATA_STORE_PATH = ServerSystemProperties.fileStoragePath();
	private static final Charset CHARSET = StandardCharsets.UTF_8;
	
	static List<List<String>> getAllRecord(InputStream inputStream) {
		// get csv reader
		NtsCsvReader csvReader = NtsCsvReader.newReader().withNoHeader().skipEmptyLines(true)
				.withChartSet(CHARSET).withFormat(CSVFormat.EXCEL.withRecordSeparator(NEW_LINE_CHAR));
		List<List<String>> result = new ArrayList<>();
		try {
			CSVParsedResult csvParsedResult = csvReader.parse(inputStream);
			List<NtsCsvRecord> allRecord = csvParsedResult.getRecords();
			getDataCSV(result, allRecord);

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return result;
	}

	private static void getDataCSV(List<List<String>> result, List<NtsCsvRecord> allRecord) {
		for (NtsCsvRecord record : allRecord) {
			List<String> data = new ArrayList<>();
			for (int i = 0; i < record.columnLength(); i++) {
				data.add((String) record.getColumn(i));
			}
			result.add(data);
		}
	}


	public static List<List<String>> getAllRecord(String fileId, String fileName) {
		// get csv reader
		NtsCsvReader csvReader = NtsCsvReader.newReader().withNoHeader().skipEmptyLines(true)
				.withChartSet(StandardCharsets.UTF_8).withFormat(CSVFormat.EXCEL.withRecordSeparator(NEW_LINE_CHAR));
		List<List<String>> result = new ArrayList<>();
		try {
			InputStream inputStream = createInputStreamFromFile(fileId, fileName);
			if (!Objects.isNull(inputStream)){
				CSVParsedResult csvParsedResult = csvReader.parse(inputStream);
				List<NtsCsvRecord> allRecord = csvParsedResult.getRecords();
				getDataCSV(result, allRecord);
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return result;
	}
	
	static List<String> getCsvHeader(String fileName, String fileId) {
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
	
	private static InputStream createInputStreamFromFile(String fileId, String fileName) {
		String filePath = getCsvStoragePath(fileId) + "//" + fileName + ".csv";
		try {
			return new FileInputStream(new File(filePath));
		} catch (FileNotFoundException e) {
			return null;
		}
	}

	private static String getCsvStoragePath(String fileId) {
		String extractDataStoragePath = getExtractDataStoragePath(fileId);
		File f = new File(extractDataStoragePath);
		if (f.exists()){
			if(Objects.requireNonNull(f.list()).length >0 ){
				return extractDataStoragePath + "//" + Objects.requireNonNull(f.list())[0];
			}
		}
		return Strings.EMPTY;
	}

	private static String getExtractDataStoragePath(String fileId){
		return DATA_STORE_PATH + "//packs//" + fileId;
	}
}
