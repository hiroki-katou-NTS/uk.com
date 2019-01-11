package nts.uk.ctx.pr.transfer.dom.emppaymentinfo;

/**
 * 
 * @author HungTT - 支払利用区分
 *
 */

public enum PaymentUseAtr {

	/**
	 * 利用する
	 */
	USE(true, "利用する"),
	/**
	 * 利用しない
	 */
	NOT_USE(false, "利用しない");

	/** The value. */
	public final boolean value;

	/** The name id. */
	public final String nameId;

	private PaymentUseAtr(boolean value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}

	public static PaymentUseAtr of(boolean value) {
		if (value)
			return PaymentUseAtr.USE;
		else
			return PaymentUseAtr.NOT_USE;
	}

}
