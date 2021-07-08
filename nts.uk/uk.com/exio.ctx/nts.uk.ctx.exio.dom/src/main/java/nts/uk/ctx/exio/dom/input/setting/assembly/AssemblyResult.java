package nts.uk.ctx.exio.dom.input.setting.assembly;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.exio.dom.input.DataItemList;

/**
 * 組み立て結果
 */
@Getter
@AllArgsConstructor
public class AssemblyResult {
	
	/** 項目の編集結果 */
	private final DataItemList revisedItem;
}
