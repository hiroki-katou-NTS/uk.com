package nts.uk.ctx.exio.dom.input.setting.mapping;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.exio.dom.input.revise.ItemType;

/**
 * 項目マッピング
 */
@Getter
@AllArgsConstructor
public class ExternalImportItemMapping {
	
	/** 並び順 */
	private int sort;
	
	/** 受入項目NO */
	private int importItemNumber;
	
	/** 項目型 */
	private ItemType itemType;
	
	/** CSV項目NO */
	private int csvItemNumber;

}
