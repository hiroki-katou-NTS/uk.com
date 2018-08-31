package nts.uk.ctx.exio.dom.exi.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;

import nts.arc.error.BusinessException;
import nts.gul.csv.CSVParsedResult;
import nts.gul.csv.NtsCsvReader;
import nts.gul.csv.NtsCsvRecord;
import nts.uk.ctx.exio.dom.exi.csvimport.ExiCharset;

public class FileUtil {
	
	private static final String NEW_LINE_CHAR = "\r\n";

	public static int getNumberOfLine(InputStream inputStream, Integer endcoding) {
		// get csv reader
		NtsCsvReader csvReader = NtsCsvReader.newReader().withNoHeader().skipEmptyLines(true)
				.withChartSet(getCharset(endcoding)).withFormat(CSVFormat.EXCEL.withRecordSeparator(NEW_LINE_CHAR));

		int count = 0;
		try {
			CSVParsedResult csvParsedResult = csvReader.parse(inputStream);
			count = csvParsedResult.getRecords().size();
		} catch (IOException e) {
			System.out.println(e.getStackTrace());
			throw new BusinessException("Msg_1365");
		}
		return count;
	}

	public static List<List<String>> getRecordByIndex(InputStream inputStream, int dataLineNum, int startLine, Integer endcoding) {
		// get csv reader
		NtsCsvReader csvReader = NtsCsvReader.newReader().withNoHeader().skipEmptyLines(true).withColumnSizeDiffByRow()
				.withChartSet(getCharset(endcoding)).withFormat(CSVFormat.EXCEL.withRecordSeparator(NEW_LINE_CHAR));
		List<List<String>> result = new ArrayList<>();
		try {
			CSVParsedResult csvParsedResult = csvReader.parse(inputStream);
			NtsCsvRecord colHeader = csvParsedResult.getRecords().get(dataLineNum - 1);
			NtsCsvRecord record = csvParsedResult.getRecords().get(startLine - 1);
			for (int i = 0; i < colHeader.columnLength(); i++) {
				String h = (String) colHeader.getColumn(i);
				String r = (String) record.getColumn(i);
				if (h == null) continue;
				List<String> data = new ArrayList<>();
				data.add(h);
				data.add(r);
				result.add(data);
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return result;
	}

	public static List<String> getRecord(InputStream inputStream, int[] columns, int index, Integer endcoding) {
		// get csv reader
		NtsCsvReader csvReader = NtsCsvReader.newReader().withNoHeader().skipEmptyLines(true)
				.withChartSet(getCharset(endcoding)).withFormat(CSVFormat.EXCEL.withRecordSeparator(NEW_LINE_CHAR));
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
	
	private static Charset getCharset(Integer valueEncoding) {
		ExiCharset encoding = ExiCharset.valueOf(valueEncoding);
        switch (encoding) {
        case Shift_JIS:
            return Charset.forName("Shift_JIS");
        default:
            return StandardCharsets.UTF_8;
        }
    }

}
