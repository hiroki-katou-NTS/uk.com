package nts.uk.ctx.at.schedule.dom.shift.estimate.aggregateset;

/**
 * The Enum HalfDayWorkCountCat.
 */
// 半日勤務カウント区分
public enum HalfDayWorkCountCat {
	
	/** The count in one time. */
	COUNT_IN_ONE_TIME(0, "Enum_HalfDayWorkCountCat_COUNT_AT_ONCE"),

	/** The use. */
	COUNT_IN_HALF_TIME(1, "Enum_UseClassificationAtr_COUNT_IN_HALF_TIME");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	/** The Constant values. */
	private final static HalfDayWorkCountCat[] values = HalfDayWorkCountCat.values();

	/**
	 * Instantiates a new rounding.
	 *
	 * @param value
	 *            the value
	 * @param nameId
	 *            the name id
	 */
	private HalfDayWorkCountCat(int value, String nameId) {
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
	public static HalfDayWorkCountCat valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (HalfDayWorkCountCat val : HalfDayWorkCountCat.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
