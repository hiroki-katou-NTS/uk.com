/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.dom.payment.contact;

/**
 * The Enum SparePayAtr.
 */
public enum SparePayAtr {

	/** The Normal. */
	Normal(0, "Normal"),

	/** The Preliminary. */
	Preliminary(1, "Preliminary");

	/** The value. */
	public int value;

	/** The description. */
	public String description;

	/** The Constant values. */
	private final static SparePayAtr[] values = SparePayAtr.values();

	/**
	 * Pay bonus atr.
	 *
	 * @param value
	 *            the value
	 * @param description
	 *            the description
	 */
	private SparePayAtr(int value, String description) {
		this.value = value;
		this.description = description;
	}

	/**
	 * Value of.
	 *
	 * @param value
	 *            the value
	 * @return the spare pay atr
	 */
	public static SparePayAtr valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (SparePayAtr val : SparePayAtr.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
