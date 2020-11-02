package nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision;

/**
 * 承認状態
 * @author quang.nh1
 */
public enum ApprovalStatus {
	/**
	 * 未承認(unapprove)
	 */
	UNAPPROVED(0,"未確認"),

	/**
	 * 承認(approved)
	 */
	APPROVED(1,"承認"),

	/**
	 * 否認(deny)
	 */
	DENY(2,"否認");

	public int value;

	public String nameId;

	ApprovalStatus(int type, String nameId){
		this.value = type;
		this.nameId = nameId;
	}
}
