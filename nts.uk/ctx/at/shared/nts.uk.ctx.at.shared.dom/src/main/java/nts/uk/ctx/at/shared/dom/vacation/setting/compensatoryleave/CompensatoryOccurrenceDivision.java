/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave;

/**
 * The Enum CompensatoryOccurrenceDivision.
 */
public enum CompensatoryOccurrenceDivision {
	
	/** The From over time. */
	FromOverTime(0),
	
	/** The Work day off time. */
	WorkDayOffTime(1);
	
	/** The value. */
	public int value;
	
	/**
	 * Instantiates a new compensatory occurrence division.
	 *
	 * @param value the value
	 */
	private CompensatoryOccurrenceDivision(int value) {
		this.value = value;
	}
	
	/**
	 * Value of.
	 *
	 * @param value the value
	 * @return the compensatory occurrence division
	 */
	public static CompensatoryOccurrenceDivision valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (CompensatoryOccurrenceDivision val : CompensatoryOccurrenceDivision.values()) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
