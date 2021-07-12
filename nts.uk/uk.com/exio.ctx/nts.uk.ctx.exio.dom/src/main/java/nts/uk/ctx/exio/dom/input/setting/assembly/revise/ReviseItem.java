package nts.uk.ctx.exio.dom.input.setting.assembly.revise;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.uk.ctx.exio.dom.input.DataItem;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportCode;

/**
 * 項目の編集
 */
@Getter
@AllArgsConstructor
public class ReviseItem implements DomainAggregate {
	
	/** 会社ID */
	private String companyId;
	
	/** 受入設定コード */
	private ExternalImportCode settingCode;
	
	/** 受入項目NO */
	private int itemNo;
	
	/** 値の編集 */
	private ReviseValue revisingValue;
	
	/**
	 * 編集する
	 * @param require
	 * @param context
	 * @param importItemNumber
	 * @param targetValue
	 * @return
	 */
	public DataItem revise(String targetValue) {
		
		// 値の編集
		Object result = this.revisingValue.revise(targetValue);
		
		return new DataItem(itemNo, result);
	}
}
