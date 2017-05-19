package nts.uk.ctx.pr.core.dom.vacationsetting.yearvacationsetting;

/**
 * The Enum YearVacationTimeUnit.
 */
public enum YearVacationTimeUnit {

	/** The One minute. */
	OneMinute(0),

	/** The Fifteen minute. */
	FifteenMinute(1),

	/** The Thirty minute. */
	ThirtyMinute(2),

	/** The One hour. */
	OneHour(3),

	/** The Two hour. */
	TwoHour(4);

	/** The value. */
	public int value;

	/** The Constant values. */
	private final static YearVacationTimeUnit[] values = YearVacationTimeUnit.values();

	/**
	 * Instantiates a new year vacation time unit.
	 *
	 * @param value the value
	 */
	private YearVacationTimeUnit(int value) {
		this.value = value;
	}

	/**
	 * Value of.
	 *
	 * @param value the value
	 * @return the year vacation time unit
	 */
	public static YearVacationTimeUnit valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (YearVacationTimeUnit val : YearVacationTimeUnit.values) {
			if (val.value == value) {
				return val;
			}
		}
		// Not found.
		return null;
	}
}
