package nts.uk.ctx.workflow.pub.service.export;

/**
 * @author loivt
 * 基準社員の承認アクション
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
