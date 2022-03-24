package nts.uk.ctx.workflow.dom.approvermanagement.workroot.operationsettings;

import org.eclipse.persistence.internal.xr.ValueObject;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 項目の名称情報
 */
@Getter
@AllArgsConstructor
public class ItemNameInformation extends ValueObject {
	
	/** 項目1の名称 */
	private ApproverItemName firstItemName;
	
	/** 項目2の名称 */
	private ApproverItemName secondItemName;
	
	/** 項目3の名称 */
	private ApproverItemName thirdItemName;
	
	/** 項目4の名称 */
	private ApproverItemName fourthItemName;
	
	/** 項目5の名称 */
	private ApproverItemName fifthItemName;
}
