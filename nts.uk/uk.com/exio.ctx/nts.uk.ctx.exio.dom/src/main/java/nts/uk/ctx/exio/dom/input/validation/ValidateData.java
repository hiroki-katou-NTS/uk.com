package nts.uk.ctx.exio.dom.input.validation;

import java.util.List;

import lombok.val;
import nts.uk.ctx.exio.dom.input.revise.reviseddata.RevisedDataRecord;

public class ValidateData{

	/**
	 * 妥当な数値であるか検証する
	 */
	public static void validate(Require require, RevisedDataRecord record) {
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
	
	public static interface Require{
		List<ImportableItem> getDefinition(int categoryId);
	}
}
