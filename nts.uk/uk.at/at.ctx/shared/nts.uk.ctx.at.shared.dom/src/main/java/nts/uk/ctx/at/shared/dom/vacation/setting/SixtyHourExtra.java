/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting;

// TODO: Auto-generated Javadoc
/**
 * The Enum SixtyHourExtra.
 */
// 60H超休使用期限
public enum SixtyHourExtra {

	/** The allways. */
	// 常に繰越
	ALLWAYS(0, "常に繰越", "常に繰越"),

	/** The one month. */
	// 1ヶ月
	ONE_MONTH(1, "1ヶ月", "1ヶ月"),

	/** The two month. */
	// 2ヶ月
	TWO_MONTH(2, "2ヶ月", "2ヶ月"),

	/** The three month. */
	// 3ヶ月
	THREE_MONTH(3, "3ヶ月", "3ヶ月"),

	/** The four month. */
	// 4ヶ月
	FOUR_MONTH(4, "4ヶ月", "4ヶ月"),

	/** The five month. */
	// 5ヶ月
	FIVE_MONTH(5, "5ヶ月", "5ヶ月"),

	/** The six month. */
	// 6ヶ月
	SIX_MONTH(6, "6ヶ月", "6ヶ月"),

	/** The seven month. */
	// 7ヶ月
	SEVEN_MONTH(7, "7ヶ月", "7ヶ月"),

	/** The eight month. */
	// 8ヶ月
	EIGHT_MONTH(8, "8ヶ月", "8ヶ月"),

	/** The nine month. */
	// 9ヶ月
	NINE_MONTH(9, "9ヶ月", "9ヶ月"),

	/** The ten month. */
	// 10ヶ月
	TEN_MONTH(10, "10ヶ月", "10ヶ月"),

	/** The eleven month. */
	// 11ヶ月
	ELEVEN_MONTH(11, "11ヶ月", "11ヶ月"),
	
	/** The twelve month. */
	// 12ヶ月
	TWELVE_MONTH(12, "12ヶ月", "12ヶ月");

	/** The value. */
	public int value;

	/** The name id. */
	public String nameId;

	/** The description. */
	public String description;

	/** The Constant values. */
	private final static SixtyHourExtra[] values = SixtyHourExtra.values();

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
	private SixtyHourExtra(int value, String nameId, String description) {
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
	public static SixtyHourExtra valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (SixtyHourExtra val : SixtyHourExtra.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
