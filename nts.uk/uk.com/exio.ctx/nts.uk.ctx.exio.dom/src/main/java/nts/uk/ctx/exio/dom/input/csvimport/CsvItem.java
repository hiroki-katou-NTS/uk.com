package nts.uk.ctx.exio.dom.input.csvimport;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * CSV1項目分のデータ
 */
@Getter
@AllArgsConstructor
public class CsvItem {

	/** CSV列番号 */
	private int csvLineNumber;
	
	/** 値 */
	private String value;
	
}