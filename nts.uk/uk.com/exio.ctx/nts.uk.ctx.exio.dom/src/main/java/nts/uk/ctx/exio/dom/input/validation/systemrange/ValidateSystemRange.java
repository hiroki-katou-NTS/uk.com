package nts.uk.ctx.exio.dom.input.validation.systemrange;

import java.util.List;

import lombok.val;
import nts.uk.ctx.exio.dom.input.revise.reviseddata.RevisedDataRecord;
import nts.uk.ctx.exio.dom.input.validation.ImportableItem;

public class ValidateSystemRange {
	/**
	 * UKのPV、Enumを使って値を検証
	 */
	public static void validate(SystemRequire require, RevisedDataRecord record) {
		val importableItems = require.getDefinition(record.getCategoryId());
		
		record.getItems().forEach(dataitem ->{
			importableItems.forEach(importableItem ->{
				//編集済みデータと受入可能項目を突合させ、受入できるかチェック
				if(importableItem.getItemNo() == dataitem.getItemNo()) {
					importableItem.validate(dataitem.getValue());
				}
			});
		});
	}
	
	public static interface SystemRequire{
		List<ImportableItem> getDefinition(int categoryId);
	}
}
