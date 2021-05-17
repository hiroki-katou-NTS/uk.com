package nts.uk.ctx.exio.dom.exi.csvimport;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.val;
import nts.arc.error.BusinessException;
import nts.gul.csv.CSVParsedResult;
import nts.gul.csv.NtsCsvReader;
import nts.gul.csv.NtsCsvRecord;
import nts.uk.ctx.exio.dom.exi.condset.AcceptanceLineNumber;
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
	private ExiCharset charset;
	
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

		this.charset = ExiCharset.valueOf(encoding);
	}
	
	public NtsCsvRecord read(Require require) throws IOException {
		val colHeader = readHeader(require);
		
		for(int i =0; i < colHeader.getRowNumber(); i++) {
			String column = (String) colHeader.getColumn(i);
		}
		
		//TODO:
		throw new RuntimeException("未実装！");
	}
	
	public NtsCsvRecord readHeader(Require require) throws IOException {
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
			throw new BusinessException("CSVファイルの行数より取込開始行が大きいです、確認してください。");
		}
		return colHeader;
	}
	
	private Charset getCharset() {
        switch (this.charset) {
        case Shift_JIS:
            return Charset.forName("Shift_JIS");
        default:
            return StandardCharsets.UTF_8;
        }
    }
	
	public interface Require extends CsvItemImport.Require{
		InputStream get();
	}
}
