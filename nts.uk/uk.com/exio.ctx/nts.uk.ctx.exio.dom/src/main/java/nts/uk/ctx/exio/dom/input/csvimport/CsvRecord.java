package nts.uk.ctx.exio.dom.input.csvimport;

import java.util.List;

import lombok.Value;

/**
 * CSV1行分のデータ
 */
@Value
public class CsvRecord {
	
	/** CSV1行分のデータ */
	private List<String> rawItems;
}
