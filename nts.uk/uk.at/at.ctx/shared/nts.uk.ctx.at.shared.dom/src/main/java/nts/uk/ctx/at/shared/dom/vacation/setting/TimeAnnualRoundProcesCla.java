/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting;

/**
 * 時間年休端数処理区分
 */
public enum TimeAnnualRoundProcesCla {
		 
	/** Truncate on day 0. */
	RoundUpToOneDay(0, "1日に切り上げる", "1日に切り上げる"),

	/** Round up to the day. */
	TruncateOnDay0(1, "0日に切り捨てる", "0日に切り捨てる");
	
	/** The value. */
	public int value;

	/** The name id. */
	public String nameId;

	/** The description. */
	public String description;

	/** The Constant values. */
	private final static TimeAnnualRoundProcesCla[] values = TimeAnnualRoundProcesCla.values();

	/**
	 * Instantiates a new manage distinct.
	 *
	 * @param value
	 *            the value
	 * @param description
	 *            the description
	 */
	private TimeAnnualRoundProcesCla(int value, String nameId, String description) {
		this.value = value;
		this.nameId = nameId;
		this.description = description;
	}

	/**
	 * Value of.
	 *
	 * @param value
	 *            the value
	 * @return the manage distinct
	 */
	public static TimeAnnualRoundProcesCla valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (TimeAnnualRoundProcesCla val : TimeAnnualRoundProcesCla.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
