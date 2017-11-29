/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktimeset.fluid;

/**
 * The Enum Unit.
 */
public enum Unit {

	/** The rounding time 1min. */
	ROUNDING_TIME_1MIN(0, "Enum_RoundingTime_1Min"),

	/** The rounding time 5min. */
	ROUNDING_TIME_5MIN(1, "Enum_RoundingTime_5Min"),

	/** The rounding time 6min. */
	ROUNDING_TIME_6MIN(2, "Enum_RoundingTime_6Min"),

	/** The rounding time 10min. */
	ROUNDING_TIME_10MIN(3, "Enum_RoundingTime_10Min"),

	/** The rounding time 15min. */
	ROUNDING_TIME_15MIN(4, "Enum_RoundingTime_15Min"),

	/** The rounding time 20min. */
	ROUNDING_TIME_20MIN(5, "Enum_RoundingTime_20Min"),

	/** The rounding time 30min. */
	ROUNDING_TIME_30MIN(6, "Enum_RoundingTime_30Min"),

	/** The rounding time 60min. */
	ROUNDING_TIME_60MIN(7, "Enum_RoundingTime_60Min");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	/** The Constant values. */
	private final static Unit[] values = Unit.values();

	/**
	 * Instantiates a new rounding.
	 *
	 * @param value
	 *            the value
	 * @param nameId
	 *            the name id
	 */
	private Unit(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}

	/**
	 * Value of.
	 *
	 * @param value
	 *            the value
	 * @return the rounding
	 */
	public static Unit valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (Unit val : Unit.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
