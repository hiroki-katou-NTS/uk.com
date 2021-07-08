package nts.uk.ctx.exio.dom.input.setting.assembly.mapping;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.exio.dom.input.csvimport.CsvRecord;

/**
 * CSV受入項目マッピング
 */
@Getter
@AllArgsConstructor
public class ImportItemMapping {
	
	/** 受入項目NO */
	private int itemNo;
	
	/** CSV列番号 */
	private int csvColumnNo;
	
	public ImportingCsvItem read(CsvRecord record) {
		return new ImportingCsvItem(itemNo, record.getItemByColumnNo(csvColumnNo));
	}
}
