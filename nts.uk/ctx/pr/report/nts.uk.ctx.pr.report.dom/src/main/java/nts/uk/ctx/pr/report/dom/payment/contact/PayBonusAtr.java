/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.dom.payment.contact;

/**
 * The Enum PayBonusAtr.
 */
public enum PayBonusAtr {

	/** The Salary. */
	Salary(0, "Salary"),

	/** The Bonuses. */
	Bonuses(1, "Bonuses");

	/** The value. */
	public int value;

	/** The description. */
	public String description;

	/** The Constant values. */
	private final static PayBonusAtr[] values = PayBonusAtr.values();

	/**
	 * Payment type.
	 *
	 * @param value
	 *            the value
	 * @param description
	 *            the description
	 */
	private PayBonusAtr(int value, String description) {
		this.value = value;
		this.description = description;
	}

	/**
	 * Value of.
	 *
	 * @param value
	 *            the value
	 * @return the pay bonus atr
	 */
	public static PayBonusAtr valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (PayBonusAtr val : PayBonusAtr.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
