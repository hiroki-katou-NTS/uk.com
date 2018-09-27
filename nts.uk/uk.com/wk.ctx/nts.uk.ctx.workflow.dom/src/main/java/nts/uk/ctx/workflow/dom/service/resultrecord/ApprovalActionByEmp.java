package nts.uk.ctx.workflow.dom.service.resultrecord;

import lombok.AllArgsConstructor;

/**
 * 基準社員の承認アクション
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
public enum ApprovalActionByEmp {
	/**
	 * 承認した
	 */
	APPROVALED(0, "承認した"),
	/**
	 * 承認しなければならない
	 */
	APPROVAL_REQUIRE(1, "承認しなければならない"),
	/**
	 * 承認できない
	 */
	NOT_APPROVAL(2, "承認できない");
	
	public final int value;
	
	public final String name;
}
