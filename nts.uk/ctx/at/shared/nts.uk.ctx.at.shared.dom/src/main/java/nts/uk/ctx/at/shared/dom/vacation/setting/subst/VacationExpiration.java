/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.subst;

/**
 * The Enum VacationExpiration.
 */
// 休暇使用期限
public enum VacationExpiration {

	/** The this month. */
	// 当月
	THIS_MONTH(0, "当月"),

	/** The alway. */
	// 常に繰越
	ALWAY(1, "常に繰越"),

	/** The end of year. */
	// 年度末クリア
	END_OF_YEAR(2, "年度末クリア"),

	/** The one month. */
	// 1ヶ月
	ONE_MONTH(3, "1ヶ月"),

	/** The two month. */
	// 2ヶ月
	TWO_MONTH(4, "2ヶ月"),

	/** The three month. */
	// 3ヶ月
	THREE_MONTH(5, "3ヶ月"),

	/** The four month. */
	// 4ヶ月
	FOUR_MONTH(6, "4ヶ月"),

	/** The five month. */
	// 5ヶ月
	FIVE_MONTH(7, "5ヶ月"),

	/** The six month. */
	// 6ヶ月
	SIX_MONTH(8, "6ヶ月"),

	/** The seven month. */
	// 7ヶ月
	SEVEN_MONTH(9, "7ヶ月"),

	/** The eight month. */
	// 8ヶ月
	EIGHT_MONTH(10, "8ヶ月"),

	/** The nine month. */
	// 9ヶ月
	NINE_MONTH(11, "9ヶ月"),

	/** The ten month. */
	// 10ヶ月
	TEN_MONTH(12, "10ヶ月"),

	/** The eleven month. */
	// 11ヶ月
	ELEVEN_MONTH(13, "11ヶ月"),

	/** The one year. */
	// 1年
	ONE_YEAR(14, "1年"),

	/** The two year. */
	// 2年
	TWO_YEAR(15, "2年"),

	/** The three year. */
	// 3年
	THREE_YEAR(16, "3年"),

	/** The four year. */
	// 4年
	FOUR_YEAR(17, "4年"),

	/** The five year. */
	// 5年
	FIVE_YEAR(18, "5年");

	/** The value. */
	public int value;

	/** The description. */
	public String description;

	/** The Constant values. */
	private final static VacationExpiration[] values = VacationExpiration.values();

	/**
	 * Instantiates a new vacation expiration.
	 *
	 * @param value
	 *            the value
	 * @param description
	 *            the description
	 */
	private VacationExpiration(int value, String description) {
		this.value = value;
		this.description = description;
	}

	/**
	 * Value of.
	 *
	 * @param value
	 *            the value
	 * @return the vacation expiration
	 */
	public static VacationExpiration valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (VacationExpiration val : VacationExpiration.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
