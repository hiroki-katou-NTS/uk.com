package nts.uk.ctx.exio.dom.input.validation.condition.system;

import java.util.ArrayList;
import java.util.List;

import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.ctx.exio.dom.input.group.ImportingGroupId;
import nts.uk.ctx.exio.dom.input.importableitem.ImportableItem;
import nts.uk.ctx.exio.dom.input.setting.assembly.RevisedDataRecord;

/**
 * システムが要求する値の範囲で検証
 */
public class ValidateSystemRange {
	/**
	 * UKのPV、Enumを使って値を検証
	 * @return 
	 */
	public static boolean validate(SystemRequire require, ExecutionContext context, RevisedDataRecord record) {
		List<Boolean> successFlags = new ArrayList<Boolean>();
		
		record.getItems().forEach(recordItem ->{
			successFlags.add(
				require.getImportableItem(context.getGroupId(), recordItem.getItemNo()).validate(recordItem)
			);
		});
		//falseが含まれてたら失敗したことを伝えたい
		return !successFlags.contains(false);
	}
	
	public static interface SystemRequire {
		ImportableItem getImportableItem(ImportingGroupId groupId, int itemNo);
	}
}
