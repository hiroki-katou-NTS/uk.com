package nts.uk.ctx.pr.transfer.dom.emppaymentinfo;

import nts.arc.enums.EnumAdaptor;

/**
 * 
 * @author HungTT - 支払按分区分
 *
 */

public enum PaymentProportionAtr {

	/**
	 * 定率
	 */
	FIXED_RATE(0, "定率"),
	/**
	 * 定額
	 */
	FIXED_AMOUNT(1, "定額"),
	/**
	 * 全額
	 */
	FULL_AMOUNT(2, "全額");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	private PaymentProportionAtr(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}

	public static PaymentProportionAtr of(int value) {
		return EnumAdaptor.valueOf(value, PaymentProportionAtr.class);
	}

}
