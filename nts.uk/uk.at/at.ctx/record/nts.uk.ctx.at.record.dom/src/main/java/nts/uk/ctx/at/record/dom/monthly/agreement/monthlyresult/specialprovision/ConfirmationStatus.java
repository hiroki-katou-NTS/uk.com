package nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision;

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
	 * 確認済(confirmed)
	 */
	CONFIRMED(1,"確認済"),

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
