package nts.uk.ctx.pr.transfer.dom.emppaymentinfo;

/**
 * 
 * @author HungTT - 給与支払方法
 *
 */

public enum SalaryPaymentMethod {

	/**
	 * 振込
	 */
	TRANSFER(true, "振込"),
	/**
	 * 現金
	 */
	CASH(false, "現金");

	/** The value. */
	public final boolean value;

	/** The name id. */
	public final String nameId;

	private SalaryPaymentMethod(boolean value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}

	public static SalaryPaymentMethod of(boolean value) {
		if (value)
			return SalaryPaymentMethod.TRANSFER;
		else
			return SalaryPaymentMethod.CASH;
	}

}
