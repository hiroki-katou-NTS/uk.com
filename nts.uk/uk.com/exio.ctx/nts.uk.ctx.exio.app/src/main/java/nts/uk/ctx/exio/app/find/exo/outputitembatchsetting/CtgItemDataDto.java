package nts.uk.ctx.exio.app.find.exo.outputitembatchsetting;

import lombok.Value;
import nts.uk.ctx.exio.dom.exo.categoryitemdata.CtgItemData;

@Value
public class CtgItemDataDto {

	/**
	 * 項目NO
	 */
	private Integer itemNo;

	/**
	 * 項目名
	 */
	private String itemName;

	private int categoryId;

	private int itemType;

	/**
	 * 表示区分
	 */
	private Integer displayClassfication;

	public static CtgItemDataDto fromdomain(CtgItemData domain) {
		return new CtgItemDataDto(
				domain.getItemNo().v(), 
				domain.getItemName().v(), 
				domain.getCategoryId().v(),
				domain.getDataType().value, 
				domain.getDisplayClassfication().value);
	}

}
