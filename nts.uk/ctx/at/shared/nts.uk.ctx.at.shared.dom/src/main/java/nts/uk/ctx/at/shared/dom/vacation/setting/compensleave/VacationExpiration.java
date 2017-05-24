/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.compensleave;

/**
 * The Enum ManageDistinct.
 */
public enum VacationExpiration {

	// 当月
	THIS_MONTH(0, "当月"),

	// 常に繰越
	ALWAY(1, "常に繰越"),

	// 年度末クリア
	END_OF_YEAR(2, "年度末クリア"),

	// 1ヶ月
	ONE_MONTH(3, "1ヶ月"),

	// 2ヶ月
	TWO_MONTH(4, "2ヶ月"),

	// 3ヶ月
	THREE_MONTH(5, "3ヶ月"),

	// 4ヶ月
	FOUR_MONTH(6, "4ヶ月"),

	// 5ヶ月
	FIVE_MONTH(7, "5ヶ月"),

	// 6ヶ月
	SIX_MONTH(8, "6ヶ月"),

	// 7ヶ月
	SEVEN_MONTH(9, "7ヶ月"),

	// 8ヶ月
	EIGHT_MONTH(10, "8ヶ月"),

	// 9ヶ月
	NINE_MONTH(11, "9ヶ月"),

	// 10ヶ月
	TEN_MONTH(12, "10ヶ月"),

	// 11ヶ月
	ELEVEN_MONTH(13, "11ヶ月"),

	// 1年
	ONE_YEAR(14, "1年"),

	// 2年
	TWO_YEAR(15, "2年"),

	// 3年
	THREE_YEAR(16, "3年"),

	// 4年
	FOUR_YEAR(17, "4年"),

	// 5年
	FIVE_YEAR(18, "5年");

	/** The value. */
	public int value;

	/** The description. */
	public String description;

	/** The Constant values. */
	private final static VacationExpiration[] values = VacationExpiration.values();

	/**
	 * Instantiates a new manage distinct.
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
	 * @return the manage distinct
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
