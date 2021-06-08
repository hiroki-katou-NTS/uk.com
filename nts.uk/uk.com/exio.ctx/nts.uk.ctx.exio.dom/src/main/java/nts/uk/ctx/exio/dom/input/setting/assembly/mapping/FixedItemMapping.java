package nts.uk.ctx.exio.dom.input.setting.assembly.mapping;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 固定値項目マッピング
 */
@Getter
@AllArgsConstructor
public class FixedItemMapping {
	
	/** 受入項目NO */
	private int importItemNumber;
	
	/** 固定値 */
	private Object value;
	
}
