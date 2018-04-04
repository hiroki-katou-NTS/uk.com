package nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto;

/**
 * @author dat.lh
 */
public enum ApprovalStatusForEmployee_New {
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

	private ApprovalStatusForEmployee_New(int value) {
		this.value = value;
	}

}
