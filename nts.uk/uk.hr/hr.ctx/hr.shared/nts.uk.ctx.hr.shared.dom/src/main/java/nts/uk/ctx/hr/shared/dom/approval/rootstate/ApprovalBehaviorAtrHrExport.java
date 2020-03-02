package nts.uk.ctx.hr.shared.dom.approval.rootstate;

import lombok.AllArgsConstructor;

/**
 * 
 * @author laitv
 *
 */
@AllArgsConstructor
public enum ApprovalBehaviorAtrHrExport {
	
	/** 0:未承認 */
	UNAPPROVED(0,"未承認"),
	
	/** 1:承認済 */
	APPROVED(1,"承認済"),
	
	/** 2:否認 */
	DENIAL(2,"否認 "),
	
	/** 3:差し戻し */
	REMAND(3,"差し戻し"),
	
	/** 4:本人差し戻し */
	ORIGINAL_REMAND(4,"本人差し戻し");

	public final int value;
	
	public final String nameId;
}
