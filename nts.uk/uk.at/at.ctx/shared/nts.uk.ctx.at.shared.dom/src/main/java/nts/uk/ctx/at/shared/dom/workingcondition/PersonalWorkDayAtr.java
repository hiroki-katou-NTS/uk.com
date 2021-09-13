package nts.uk.ctx.at.shared.dom.workingcondition;

/**
 * The Enum PersonalDayOfWeekType.
 */
public enum PersonalWorkDayAtr {

	// 平日時
	WeekdayTime(0, "Enum_WeekdayTime"),

	// 休日出勤時
	HolidayWork(1, "Enum_HolidayWork"),

	// 月曜日
	Monday(2, "Enum_Monday"),

	// 火曜日
	Tuesday(3,"Enum_Tuesday"),

	// 水曜日
	Wednesday(4,"Enum_Wednesday"),

	// 木曜日
	Thursday(5,"Enum_Thursday"),

	// 金曜日
	Friday(6,"Enum_Friday"),

	// 土曜日
	Saturday(7,"Enum_Saturday"),

	// 日曜日
	Sunday(8,"Enum_Sunday");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	/** The Constant values. */
	private final static PersonalWorkDayAtr[] values = PersonalWorkDayAtr.values();

	/**
	 * Instantiates a new rounding.
	 *
	 * @param value
	 *            the value
	 * @param nameId
	 *            the name id
	 */
	private PersonalWorkDayAtr(int value, String nameId) {
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
	public static PersonalWorkDayAtr valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (PersonalWorkDayAtr val : PersonalWorkDayAtr.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
