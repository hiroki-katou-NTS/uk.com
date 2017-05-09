/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.dom.payment.refundsetting.refundpadding;

public enum ShowBreakLine {

	/** The display. */
	DISPLAY(0, "する"),

	/** The not display. */
	NOT_DISPLAY(1, "しない");

	/** The value. */
	public int value;

	/** The description. */
	public String description;

	/** The Constant values. */
	private final static ShowBreakLine[] values = ShowBreakLine.values();

	/**
	 * Instantiates a new prints the type.
	 *
	 * @param value
	 *            the value
	 * @param description
	 *            the description
	 */
	private ShowBreakLine(int value, String description) {
		this.value = value;
		this.description = description;
	}

	/**
	 * Value of.
	 *
	 * @param value
	 *            the value
	 * @return the prints the type
	 */
	public static ShowBreakLine valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (ShowBreakLine val : ShowBreakLine.values) {
			if (val.value == value) {
				return val;
			}
		}
		// Not found.
		return null;
	}
}
