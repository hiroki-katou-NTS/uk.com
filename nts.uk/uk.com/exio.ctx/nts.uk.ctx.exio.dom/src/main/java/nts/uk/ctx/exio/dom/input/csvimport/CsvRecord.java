package nts.uk.ctx.exio.dom.input.csvimport;

import java.util.List;

import lombok.Value;

/**
 * CSV1行分のデータ
 */
@Value
public class CsvRecord {
	
	/** 行番号 */
	private final int rowNo;
	
	/** CSV1行分のデータ */
	private final List<String> rawItems;
	
	/**
	 * 列番号で取得する（列番号は1スタート）
	 * @param columnNo
	 * @return
	 */
	public String getItemByColumnNo(int columnNo) {
		return rawItems.get(columnNo - 1);
	}
}
