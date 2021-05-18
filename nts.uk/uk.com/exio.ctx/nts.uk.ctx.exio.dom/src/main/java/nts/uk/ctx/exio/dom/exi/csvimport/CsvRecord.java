package nts.uk.ctx.exio.dom.exi.csvimport;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class CsvRecord {
	@Getter
	private List<CsvItem> items;
	
//	public CsvItem get(String itemName) {
//		return items.get(itemName);
//	}
//	public CsvItem get(int index) {
//		return items.get(items.keySet().toArray()[index]);
//	}
//	
//	public Set<Map.Entry<String, CsvItem>> items() {
//		return items.entrySet();
//	}
}
