package nts.uk.ctx.exio.dom.input.setting.assembly.mapping;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * CSV受入項目マッピング
 */
@Getter
@AllArgsConstructor
public class ImportItemMapping {
	
	/** 受入項目NO */
	private int importItemNumber;
	
	/** CSV列番号 */
	private int csvColumnNumber;
	
}
