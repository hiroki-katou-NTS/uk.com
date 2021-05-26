package nts.uk.ctx.exio.dom.input.csvimport;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class CsvRecord {
	@Getter
	private List<CsvItem> items;

}
