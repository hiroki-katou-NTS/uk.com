package nts.uk.ctx.exio.dom.input.revise;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportCode;
import nts.uk.ctx.exio.dom.input.validation.ImportableItem;

/**
 * 値の編集
 */
@Getter
@AllArgsConstructor
public class ReviseValue extends AggregateRoot {
	
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
		
	public static RevisedValueResult revise(Require require, ExecutionContext context, int importItemNumber, String targetValue) {

		//val revise = require.getRevise(context, mapping.get(i).getImportItemNumber());
		return null;
	}
	
	public interface Require{
		ReviseValue getRevise(ExecutionContext context, int importItemNumber);
		ImportableItem getImportableItem(ExecutionContext context, int importItemNumber);
	}
}
