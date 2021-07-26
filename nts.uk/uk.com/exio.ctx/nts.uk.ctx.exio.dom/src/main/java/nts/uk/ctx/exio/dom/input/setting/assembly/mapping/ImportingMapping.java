package nts.uk.ctx.exio.dom.input.setting.assembly.mapping;

import static java.util.stream.Collectors.*;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Value;
import nts.uk.ctx.exio.dom.input.DataItemList;
import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.ctx.exio.dom.input.csvimport.CsvRecord;

/**
 * 受入マッピング
 */
@Value
public class ImportingMapping {
	
	/** マッピング一覧 */
	private List<ImportingItemMapping> mappings;
	
	public DataItemList assemble(RequireAssemble require, ExecutionContext context, CsvRecord csvRecord) {
		
		return mappings.stream()
				.map(m -> m.assemble(require, context, csvRecord))
				.collect(Collectors.collectingAndThen(toList(), DataItemList::new));
	}
	
	public static interface RequireAssemble extends ImportingItemMapping.RequireAssemble {
	}
	
	public List<Integer> getAllItemNo() {
		
		return mappings.stream()
				.map(i -> i.getItemNo())
				.collect(toList());
	}
}
