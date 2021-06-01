package nts.uk.ctx.exio.dom.input.csvimport;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.exio.dom.exi.dataformat.ItemType;


@Getter
@AllArgsConstructor
public class CsvItem {

	/** CSV項目NO */
	private int csvItemNo;
	
	/** CSV項目名 */
	private String csvItemName;

	/** 受入項目NO */
	private int itemNo;
	
	/** 受入項目名 */
	private String itemName;
	
	/** 項目型 */
	private ItemType type;
	
	/** 値 */
	private String value;
	
}