package nts.uk.ctx.pr.core.app.find.itemmaster.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.core.dom.itemmaster.ItemMaster;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ItemMasterDto {
	/** 項目コード */
	private String itemCode;
	private String itemName;
	/** カテゴリ区分 */
	private int categoryAtr;
	/** item name */
	private String itemAbName;
	
	public static ItemMasterDto fromDomain(ItemMaster domain) {
		return new ItemMasterDto(
				domain.getItemCode().v(), 
				domain.getItemName().v(),
				domain.getCategoryAtr().value,
				domain.getItemAbName().v());
	}
}
