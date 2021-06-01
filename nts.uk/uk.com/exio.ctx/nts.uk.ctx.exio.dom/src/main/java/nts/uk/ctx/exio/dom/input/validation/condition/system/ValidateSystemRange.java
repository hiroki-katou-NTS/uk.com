package nts.uk.ctx.exio.dom.input.validation.condition.system;

import java.util.ArrayList;
import java.util.List;

import lombok.val;
import nts.uk.ctx.exio.dom.input.DataItem;
import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.ctx.exio.dom.input.revise.reviseddata.RevisedDataRecord;
import nts.uk.ctx.exio.dom.input.validation.ImportableItem;

/**
 * システムが要求する値の範囲で検証
 */
public class ValidateSystemRange {
	/**
	 * UKのPV、Enumを使って値を検証
	 * @return 
	 */
	public static boolean validate(SystemRequire require, ExecutionContext context, RevisedDataRecord record) {
		val importableItems = require.getDefinition(context.getGroupId());
		List<Boolean> successFlags = new ArrayList<Boolean>();
		
		for(DataItem dataItem : record.getItems()) {
			boolean existSetting = false;
			for(ImportableItem importableItem : importableItems) {
				//編集済みデータと受入可能項目を突合させ、受入できるかチェック
				if(importableItem.getItemNo() == dataItem.getItemNo()) {
					successFlags.add(importableItem.validate(dataItem));
					existSetting = true;
				}
			}
			if(!existSetting) {
				throw new RuntimeException("システムが許容していない項目を受入ようとしています。"+ dataItem.getItemNo());
			}
		}

		//falseが含まれてたら失敗したことを伝えたい
		return !successFlags.contains(false);
	}
	
	public static interface SystemRequire{
		List<ImportableItem> getDefinition(int categoryId);
	}
}
