package nts.uk.ctx.pr.transfer.dom.emppaymentinfo;

import nts.arc.enums.EnumAdaptor;

/**
 * 
 * @author HungTT - 支給優先順位
 *
 */

public enum PaymentPriority {

	/**
	 * 1
	 */
	ONE(1),
	/**
	 * 2
	 */
	TWO(2),
	/**
	 * 3
	 */
	THREE(3),
	/**
	 * 4
	 */
	FOUR(4),
	/**
	 * 5
	 */
	FIVE(5);

	/** The value. */
	public final int value;

	private PaymentPriority(int value) {
		this.value = value;
	}

	public static PaymentPriority of(int value) {
		return EnumAdaptor.valueOf(value, PaymentPriority.class);
	}

}
