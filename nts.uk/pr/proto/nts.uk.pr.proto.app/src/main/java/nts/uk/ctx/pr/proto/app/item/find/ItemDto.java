package nts.uk.ctx.pr.proto.app.item.find;

import lombok.Value;
import nts.uk.ctx.pr.proto.dom.itemmaster.ItemMaster;
/** Finder DTO of item */
@Value
public class ItemDto {	
	String companyCode;
	/** 項目コード */
	String itemCode;
	/** カテゴリ区分 */
	int categoryAtr;
	//alarm
	//error
	
	public static ItemDto fromDomain(ItemMaster domain){
		return new ItemDto(
				domain.getCompanyCode().v(), 
				domain.getItemCode().v(), 
				domain.getCategoryAtr().value);		
	}
}
