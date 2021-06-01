package nts.uk.ctx.exio.dom.input.revise.value;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.exio.dom.input.revise.ItemType;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportCode;

@Getter
@AllArgsConstructor
public class RevisingValue extends AggregateRoot {
	
	/** 会社ID */
	private String companyId;
	
	/** 受入設定コード */
	private ExternalImportCode externalImportCode;
	
	/** 受入項目NO */
	private int importItemNumber;
	
	/** 項目型 */
	private ItemType itemType;
	
	/** 値の編集インターフェース */
	private RevisingValueType revisingValueType;

}
