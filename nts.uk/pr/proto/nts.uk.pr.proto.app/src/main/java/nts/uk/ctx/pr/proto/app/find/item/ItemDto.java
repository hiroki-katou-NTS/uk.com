package nts.uk.ctx.pr.proto.app.find.item;

import lombok.Value;
import nts.uk.ctx.pr.proto.dom.itemmaster.ItemMaster;
/** Finder DTO of item */
@Value
public class ItemDto {	
	/**会社コード */
	String companyCode;
	/** 項目コード */
	String itemCode;
	/** カテゴリ区分 */
	int categoryAtr;
	/** item name */
	String itemAbName;
	/** item details */
	boolean isUseHighError;
    int errRangeHigh;
    boolean isUseLowError;
    int errRangeLow;
    boolean isUseHighAlam;
    int alamRangeHigh;
    boolean isUseLowAlam;
    int alamRangeLow;
	
	
	public static ItemDto fromDomain(ItemMaster domain){
		return new ItemDto(
				domain.getCompanyCode().v(), 
				domain.getItemCode().v(), 
				domain.getCategoryAtr().value,
				domain.getItemAbName().v(),
				(domain.getError().get(0).getIsUseHigh().value == 1),
				domain.getError().get(0).getRange().max().intValue(),
				(domain.getError().get(0).getIsUseLow().value == 1),
				domain.getError().get(0).getRange().min().intValue(),
				(domain.getAlarm().get(0).getIsUseHigh().value == 1),
				domain.getAlarm().get(0).getRange().max().intValue(),
				(domain.getAlarm().get(0).getIsUseLow().value == 1),
				domain.getAlarm().get(0).getRange().min().intValue()
				);	
	}
}
