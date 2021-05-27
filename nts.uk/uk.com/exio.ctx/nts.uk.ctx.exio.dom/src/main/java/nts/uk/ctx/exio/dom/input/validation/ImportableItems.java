package nts.uk.ctx.exio.dom.input.validation;

import java.util.List;

import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.uk.ctx.exio.dom.input.DataItem;

/**
 * 受入可能項目の定義
 */
public class ImportableItems implements DomainAggregate{
	private List<ImportableItem> importableItems;
	
	public ImportableItems(List<ImportableItem> importableItems) {
		super();
		this.importableItems = importableItems;
	}	
	
	public void validateSameItemNo(List<DataItem> dataItems) {
	
	//編集済みデータと受入可能項目を突合させ、受入できるかチェック
		dataItems.forEach(dataitem ->{
			importableItems.stream()
				.filter(importableItem -> importableItem.getItemNo() == dataitem.getItemNo())
				.findFirst()
				.ifPresent(importableItem -> importableItem.validate(dataitem.getValue()));
		});
	}




}
