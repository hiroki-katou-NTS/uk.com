package nts.uk.ctx.exio.dom.input.csvimport;

import static java.util.stream.Collectors.*;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.StreamSupport;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.arc.layer.dom.objecttype.DomainObject;

/**
 * CSVファイル情報
 */
@Getter
@RequiredArgsConstructor
public class ExternalImportCsvFileInfo implements DomainObject {
	
	/** CSVの項目名取得行 */
	private final ExternalImportRowNumber itemNameRowNumber;
	
	/** CSVの取込開始行 */
	private final ExternalImportRowNumber importStartRowNumber;
	
	/** ベースのCSVファイルID */
	private final Optional<String> baseCsvFileId;

	@Override
	public void validate() {
		
		DomainObject.super.validate();
		
		if (!(itemNameRowNumber.v() < importStartRowNumber.v())) {
			throw new BusinessException(new RawErrorMessage(
					"項目名の行 [" + itemNameRowNumber.v() + "] は、"
					+ "受入開始行 [" + importStartRowNumber.v() + "] よりも小さい値を指定してください。"));
		}
	}

	/**
	 * @param csvFileStream
	 * @param readRecords(Function<CsvRecord:CSV1行分のデータ, Boolean:処理に成功したか>)
	 * @return 処理を実行成功したレコード数
	 */
	public int parse(InputStream csvFileStream, Function<CsvRecord, Boolean> readRecords) {

		val parser = new Parser(importStartRowNumber.v());
		return parser.parse(csvFileStream, readRecords);
	}
	
	public void readBaseCsv(InputStream csvFileStream, Consumer<CsvRecord> readRecords) {

		val parser = new Parser(0);
		parser.parse(csvFileStream, r ->{
				readRecords.accept(r);
				return true;
		});
	}
	
	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	public static class Parser {

		/** データ行の開始 */
		private final int lineData;
		
		private Iterator<CSVRecord> iterator;
		
		private int nextRow = 1;
		
		
		@SneakyThrows
		public int parse(
				InputStream inputStream,
				Function<CsvRecord, Boolean> readRecords) {
			
			try (val reader = new InputStreamReader(inputStream, "SJIS");
					val parser = new CSVParser(reader, CSVFormat.EXCEL)) {
				
				this.iterator = parser.iterator();
				
				return readRows(readRecords);
			}
		}

		private int readRows(Function<CsvRecord, Boolean> readRecords) {
			
			advance(lineData);
			int successCount = 0;
			for (int rowNo = 1; iterator.hasNext(); rowNo++) {
				val record = new CsvRecord(rowNo, toStringList(readNextRow()));
				if(readRecords.apply(record)){
					successCount++;
				}
			}
			return successCount;
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
