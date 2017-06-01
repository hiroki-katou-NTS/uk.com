/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting;

/**
 * The Enum SixtyHourExtra.
 */
// 時間休暇消化単位
public enum TimeDigestiveUnit {

	// 1分
	ONE_MINUTE(0, "1分", "1分"),

	// 15分
	FIFTEEN_MINUTE(0, "15分", "15分"),

	// 30分
	THIRTY_MINUTE(0, "30分", "30分"),

	// 1時間
	ONE_HOUR(0, "1時間", "1時間"),

	// 2時間
	TWO_HOUR(0, "2時間", "2時間");

	/** The value. */
	public int value;

	/** The name id. */
	public String nameId;

	/** The description. */
	public String description;

	/** The Constant values. */
	private final static TimeDigestiveUnit[] values = TimeDigestiveUnit.values();

	/**
	 * Instantiates a new sixty hour extra.
	 *
	 * @param value
	 *            the value
	 * @param nameId
	 *            the name id
	 * @param description
	 *            the description
	 */
	private TimeDigestiveUnit(int value, String nameId, String description) {
		this.value = value;
		this.nameId = nameId;
		this.description = description;
	}

	/**
	 * Value of.
	 *
	 * @param value
	 *            the value
	 * @return the sixty hour extra
	 */
	public static TimeDigestiveUnit valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (TimeDigestiveUnit val : TimeDigestiveUnit.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
