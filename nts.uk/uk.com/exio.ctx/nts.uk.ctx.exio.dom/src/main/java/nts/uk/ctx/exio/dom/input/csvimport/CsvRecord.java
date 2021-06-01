package nts.uk.ctx.exio.dom.input.csvimport;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 
 * CSV1行分のデータ
 *
 */
@Getter
@AllArgsConstructor
public class CsvRecord {
	
	/** CSV行番号 */
	private int csvRawNumber;
	
	/** CSV1行分のデータ */
	private List<CsvItem> items;
}
