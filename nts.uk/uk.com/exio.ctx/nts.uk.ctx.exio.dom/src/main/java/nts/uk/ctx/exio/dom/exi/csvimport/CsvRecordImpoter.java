package nts.uk.ctx.exio.dom.exi.csvimport;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.val;
import nts.arc.error.BusinessException;
import nts.gul.csv.CSVParsedResult;
import nts.gul.csv.NtsCsvReader;
import nts.gul.csv.NtsCsvRecord;
import nts.uk.ctx.exio.dom.exi.condset.AcceptanceLineNumber;
import nts.uk.ctx.exio.dom.exi.extcategory.ExternalAcceptCategoryItem;
import nts.uk.ctx.exio.dom.exi.item.StdAcceptItem;
import nts.uk.ctx.exio.dom.input.csvimport.CsvRecord;
import nts.uk.ctx.exio.dom.input.csvimport.ExternalImportCharset;

/**
 * CSVの1レコードを取り込む
 * @author ai_muto
 *
 */
@AllArgsConstructor
public class CsvRecordImpoter {

	/**
	 * CSVデータの項目名行
	 */
	private Optional<AcceptanceLineNumber> csvDataItemLineNumber;

	/**
	 * CSVデータの取込開始行
	 */
	private Optional<AcceptanceLineNumber> csvDataStartLine;

	/**
	 * 文字コード
	 */
	private ExternalImportCharset charset;
	
	public Integer getCsvDataItemLineNumber() {
		return this.csvDataItemLineNumber.isPresent()
				? this.csvDataItemLineNumber.get().v()
				: null;
	}
	public Integer getCsvDataStartLine() {
		return this.csvDataStartLine.isPresent()
				? this.csvDataStartLine.get().v()
				: null;
	}
	public Integer getCharacterCode() {
		return this.charset != null
				? this.charset.value
				: null;
	}

	public CsvRecordImpoter(Integer lineNumber, Integer startLine, Integer encoding) {
		this.csvDataItemLineNumber = 	lineNumber == null 
				? Optional.empty()
				: Optional.of(new AcceptanceLineNumber(lineNumber));

		this.csvDataStartLine = 	startLine == null 
				? Optional.empty()
				: Optional.of(new AcceptanceLineNumber(startLine));

		this.charset = ExternalImportCharset.valueOf(encoding);
	}
	
	public List<CsvRecord> read(Require require) throws IOException, RequiredMasterDataNotFoundException {
		NtsCsvReader csvReader = NtsCsvReader.newReader()
				.withNoHeader()
				.skipEmptyLines(true)
				.withColumnSizeDiffByRow()
				.withChartSet(getCharset());

		CSVParsedResult csvParsedResult;
		try (InputStream inputStream = require.get()) {
			csvParsedResult = csvReader.parse(inputStream);
		}

		int dataLineNum = this.csvDataItemLineNumber.orElse(new AcceptanceLineNumber(1)).v();
		NtsCsvRecord colHeader = csvParsedResult.getRecords().get(dataLineNum - 1);
		int startLine = this.csvDataStartLine.orElse(new AcceptanceLineNumber(2)).v();
		if(csvParsedResult.getRecords().size() <= startLine) {
			throw new BusinessException( "Msg_2189"); //CSVファイルの行数より取込開始行が大きいです。確認してください。
		}

		val csvParsedLines = csvParsedResult.getRecords().subList(startLine - 1, csvParsedResult.getRecords().size());

		List<CsvRecord> result = new ArrayList<>();
//		for (NtsCsvRecord csvParsedLine : csvParsedLines) {
//			List<String> items = new ArrayList<>();
//			for (int i = 0; i<colHeader.columnLength(); i++) {
//				String itemName = colHeader.getColumn(i).toString();
//				int csvItemNo = i +1;
//
//				val acceptSetItem = require.getStdAcceptItem(csvItemNo);
//				val accItem = require.getExternalAcceptCategoryItem(acceptSetItem.getAcceptItemNumber());
//				items.add(new CsvItem(itemName, csvParsedLine.getColumn(itemName).toString(), acceptSetItem, accItem));
//			}
//			result.add(new CsvRecord(items));
//		}
		return result;
	}
	
	private Charset getCharset() {
        switch (this.charset) {
        case Shift_JIS:
            return Charset.forName("Shift_JIS");
        default:
            return StandardCharsets.UTF_8;
        }
    }
	
	public interface Require {
		InputStream get();

		StdAcceptItem getStdAcceptItem(int csvItemNo);
		ExternalAcceptCategoryItem getExternalAcceptCategoryItem(int acceptItemNumber);
	}
}
