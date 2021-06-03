package nts.uk.ctx.exio.dom.input.revise;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.val;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.ctx.exio.dom.input.csvimport.CsvItem;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportCode;
import nts.uk.ctx.exio.dom.input.setting.mapping.ImportItemMapping;
import nts.uk.ctx.exio.dom.input.validation.ImportableItem;

/**
 * 値の編集
 */
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
	

	
	public RevisedValueResult revise(Require require, ExecutionContext context, CsvItem reviseTarget, ImportItemMapping mapping) {
		
		val importableItem = require.getImportableItem(context, mapping.getImportItemNumber());
		
		return null;
	}
	
	public interface Require{
		//ImportableItemsRepository
		ImportableItem getImportableItem(ExecutionContext context, int importItemNumber);
	}
}
