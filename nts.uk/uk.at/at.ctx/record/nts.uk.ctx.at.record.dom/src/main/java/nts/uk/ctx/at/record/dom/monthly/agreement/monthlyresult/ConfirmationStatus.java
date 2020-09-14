package nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult;

/**
 * 確認状態
 * @author quang.nh1
 */
public enum ConfirmationStatus {
	/**
	 * 未確認(unconfirm)
	 */
	UNCONFIRMED(0,"未確認"),

	/**
	 * 承認(recognition)
	 */
	RECOGNITION(1,"承認"),

	/**
	 * 否認(deny)
	 */
	DENY(2,"否認");

	public int value;

	public String nameId;

	ConfirmationStatus(int type, String nameId){
		this.value = type;
		this.nameId = nameId;
	}
}
