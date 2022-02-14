package nts.uk.ctx.workflow.dom.approvermanagement.workroot.operationsettings;

import lombok.AllArgsConstructor;

/**
 * 承認レベル
 */
@AllArgsConstructor
public enum ApprovalLevel {
			
	/** レベル1 */
	FIRST(1),
	
	/** レベル2 */
	SECOND(2),
	
	/** レベル3 */
	THIRD(3),
	
	/** レベル4 */
	FOURTH(4),
	
	/** レベル5 */
	FIFTH(5);
	
	public int value;
}
