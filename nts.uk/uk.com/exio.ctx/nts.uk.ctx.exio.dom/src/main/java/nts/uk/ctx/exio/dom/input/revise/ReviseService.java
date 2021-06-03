package nts.uk.ctx.exio.dom.input.revise;

import lombok.AllArgsConstructor;
import lombok.val;
import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.ctx.exio.dom.input.csvimport.CsvItem;
import nts.uk.ctx.exio.dom.input.setting.mapping.ImportItemMapping;
import nts.uk.ctx.exio.dom.input.validation.ImportableItem;

@AllArgsConstructor
public class ReviseService {
	
	public RevisedValueResult revise(Require require, ExecutionContext context, CsvItem reviseTarget, ImportItemMapping mapping) {
		
		val importableItem = require.getImportableItem(context, mapping.getImportItemNumber());
		
		return null;
	}
	
	public interface Require{
		//ImportableItemsRepository
		ImportableItem getImportableItem(ExecutionContext context, int importItemNumber);
	}
}
