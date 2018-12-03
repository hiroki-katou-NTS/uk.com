/**
 * 9:08:57 AM Mar 12, 2018
 */
package nts.uk.ctx.at.record.dom.adapter.workflow.service.enums;

/**
 * 基準社員の承認アクション
 * @author hungnm
 *
 */
public enum ApprovalActionByEmpl {
	/**
	 * 承認した
	 */
	APPROVALED(0),
	/**
	 * 承認しなければならない
	 */
	APPROVAL_REQUIRE(1),
	/**
	 * 承認できない
	 */
	NOT_APPROVAL(2);
	
	public final int value;
	
	private ApprovalActionByEmpl(int value){
		this.value = value;
	}
}
