package nts.uk.ctx.exio.dom.input.csvimport;

import java.util.List;
import java.util.Optional;

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
	public Optional<String> getItemByColumnNo(int columnNo) {
		
		int index = columnNo - 1;
		
		if (index >= rawItems.size()) {
			return Optional.empty();
		}
		
		return Optional.of(rawItems.get(index));
	}
}
