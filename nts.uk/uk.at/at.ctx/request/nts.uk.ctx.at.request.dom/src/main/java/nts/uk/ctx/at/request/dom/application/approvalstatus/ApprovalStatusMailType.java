package nts.uk.ctx.at.request.dom.application.approvalstatus;

/**
 * 承認状況メール種類
 * 
 * @author dat.lh
 */
public enum ApprovalStatusMailType {
	/**
	 * 申請承認未承認
	 */
	APP_APPROVAL_UNAPPROVED(0),
	/**
	 * 日別未確認（本人）
	 */
	DAILY_UNCONFIRM_BY_PRINCIPAL(1),
	/**
	 * 日別未確認（確認者）
	 */
	DAILY_UNCONFIRM_BY_CONFIRMER(2),
	/**
	 * 月別未確認（本人）
	 */
	MONTHLY_UNCONFIRM_BY_PRINCIPAL(5),
	/**
	 * 月別未確認（確認者）
	 */
	MONTHLY_UNCONFIRM_BY_CONFIRMER(3),
	/**
	 * 就業確認
	 */
	WORK_CONFIRMATION(4);

	public final int value;

	private ApprovalStatusMailType(int value) {
		this.value = value;
	}
}
