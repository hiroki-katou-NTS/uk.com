package nts.uk.ctx.exio.dom.input.csvimport;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * CSV1行分のデータ
 */
@Getter
@NoArgsConstructor
public class CsvRecord {
	
	/** CSV1行分のデータ */
	private List<String> rawItems;
	
	/**
	 * 項目の追加
	 * @param lineNumber
	 * @param value
	 */
	public void addItem(String value) {
		this.rawItems.add(value);
	}
}
