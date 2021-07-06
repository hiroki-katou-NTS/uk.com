package nts.uk.ctx.exio.dom.input.csvimport;

import static java.util.stream.Collectors.*;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.StreamSupport;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.val;

/**
 * CSVファイル情報
 */
@Getter
@RequiredArgsConstructor
public class ExternalImportCsvFileInfo {
	
	/** CSVの項目名取得行 */
	private final ExternalImportRowNumber itemNameRowNumber;
	
	/** CSVの取込開始行 */
	private final ExternalImportRowNumber importStartRowNumber;
	
	public void parse(InputStream csvFileStream, Consumer<CsvRecord> readRecords) {

		new Parser(itemNameRowNumber.v(), importStartRowNumber.v()).parse(
				csvFileStream,
				cn -> { }, // 今のところヘッダ行を取得する必要が無い
				readRecords);
	}
	
	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	public static class Parser {

		/** ヘッダ行 */
		private final int lineHeader;
		
		/** データ行の開始 */
		private final int lineData;
		
		private Iterator<CSVRecord> iterator;
		
		private int nextRow = 1;
		
		
		@SneakyThrows
		public void parse(
				InputStream inputStream,
				Consumer<List<String>> readHeader,
				Consumer<CsvRecord> readRecords) {
			
			try (val reader = new InputStreamReader(inputStream);
					val parser = new CSVParser(reader, CSVFormat.EXCEL)) {
				
				this.iterator = parser.iterator();
				
				readHeader.accept(readHeader());
				
				readRows(readRecords);
			}
		}
		
		private List<String> readHeader() {
			
			advance(lineHeader);
			
			if (!iterator.hasNext()) {
				return Collections.emptyList();
			}
			
			return toStringList(readNextRow());
		}

		private void readRows(Consumer<CsvRecord> readRecords) {
			
			advance(lineData);
			
			for (int rowNo = 1; iterator.hasNext(); rowNo++) {
				val record = new CsvRecord(rowNo, toStringList(readNextRow()));
				readRecords.accept(record);
			}
		}
		
		private void advance(int targetRow) {
			while (iterator.hasNext() && nextRow < targetRow) {
				readNextRow(); // 空読み
			}
		}
		
		private CSVRecord readNextRow() {
			nextRow++;
			return iterator.next();
		}

		private static List<String> toStringList(CSVRecord record) {
			
			return StreamSupport.stream(record.spliterator(), false)
					.collect(toList());
		}
	}
}
