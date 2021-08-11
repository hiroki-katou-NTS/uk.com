package nts.uk.ctx.exio.app.input.find.importableitem;

import lombok.Value;
import nts.uk.ctx.exio.dom.input.importableitem.ImportableItem;

@Value
public class ImportableItemDto {
	
	/** 項目NO */
	private int itemNo;
	
	/** 項目名 */
	private String itemName;
	
	/** 必須項目 */
	private boolean required;
	
	/** 項目型 */
	private String itemType;
	
	public static ImportableItemDto fromDomain(ImportableItem domain) {
		return new ImportableItemDto(
				domain.getItemNo(), 
				domain.getItemName(), 
				domain.isRequired(), 
				domain.getItemType().name());
	}
}
