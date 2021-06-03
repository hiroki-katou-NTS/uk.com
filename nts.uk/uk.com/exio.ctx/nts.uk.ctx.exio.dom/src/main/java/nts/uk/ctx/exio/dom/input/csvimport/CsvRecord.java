package nts.uk.ctx.exio.dom.input.csvimport;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * CSV1行分のデータ
 */
@Getter
@AllArgsConstructor
public class CsvRecord {
	
	/** CSV1行分のデータ */
	private List<CsvItem> items;
	
}
