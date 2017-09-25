/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.repository.optitem.calculation;

/**
 * The Enum RoundingAtr.
 */
public enum RoundingAtr {

	/** The daily. */
	DAILY(1, "Enum_RoundingAtr_DAILY"),

	/** The monthly. */
	MONTHLY(2, "Enum_RoundingAtr_MONTHLY");

	/** The value. */
	public int value;

	/** The name id. */
	public String nameId;

	/** The Constant values. */
	private final static RoundingAtr[] values = RoundingAtr.values();

	/**
	 * Instantiates a new rounding atr.
	 *
	 * @param value the value
	 * @param nameId the name id
	 */
	private RoundingAtr(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}

	/**
	 * Value of.
	 *
	 * @param value
	 *            the value
	 * @return the rounding atr
	 */
	public static RoundingAtr valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (RoundingAtr val : RoundingAtr.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
