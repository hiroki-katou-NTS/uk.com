/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/

package nts.uk.ctx.pr.report.dom.payment.refundsetting.refundpadding;

/**
 * The Enum PrintType.
 */
public enum PrintType {

	/** The A 4 once person. */
	A4_ONCE_PERSON(1, "A4縦1人、圧着式（Z折り、はがき）、連続用紙"),

	/** The A 4 two person. */
	A4_TWO_PERSON(2, "A4縦2人、A4横2人"),

	/** The A 4 three person. */
	A4_THREE_PERSON(3, "A4縦3人");

	/** The value. */
	public int value;

	/** The description. */
	public String description;

	/** The Constant values. */
	private final static PrintType[] values = PrintType.values();

	/**
	 * Instantiates a new prints the type.
	 *
	 * @param value the value
	 * @param description the description
	 */
	private PrintType(int value, String description) {
		this.value = value;
		this.description = description;
	}

	/**
	 * Value of.
	 *
	 * @param value the value
	 * @return the prints the type
	 */
	public static PrintType valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (PrintType val : PrintType.values) {
			if (val.value == value) {
				return val;
			}
		}
		// Not found.
		return null;
	}
}
