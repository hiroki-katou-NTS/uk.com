package nts.uk.ctx.exio.dom.input.setting.assembly.mapping;

import lombok.Value;

/**
 * 受入CSV項目
 */
@Value
public class ImportingCsvItem {

	/** 受入項目NO */
	int itemNo;
	
	/** CSV値 */
	String csvValue;
}
