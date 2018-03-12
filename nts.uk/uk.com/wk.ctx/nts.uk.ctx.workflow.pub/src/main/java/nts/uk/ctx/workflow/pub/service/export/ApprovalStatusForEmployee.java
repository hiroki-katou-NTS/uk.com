package nts.uk.ctx.workflow.pub.service.export;

public enum ApprovalStatusForEmployee {
	/**
	 * 未承認
	 */
	UNAPPROVED(0),
	/**
	 * 承認中
	 */
	DURING_APPROVAL(1),
	/**
	 * 承認済
	 */
	APPROVED(2);
	
	public final int value;
	
	private ApprovalStatusForEmployee(int value){
		this.value = value;
	}

}
