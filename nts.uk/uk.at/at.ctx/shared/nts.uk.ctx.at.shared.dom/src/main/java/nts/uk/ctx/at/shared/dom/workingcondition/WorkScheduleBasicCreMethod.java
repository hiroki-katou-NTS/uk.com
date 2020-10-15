package nts.uk.ctx.at.shared.dom.workingcondition;


/**
 * The Enum WorkScheduleBasicCreMethod.
 */
public enum WorkScheduleBasicCreMethod {
	
	/** 営業日カレンダー */
	/** The business day calendar. */
	BUSINESS_DAY_CALENDAR(0, "Enum_WorkScheduleBasicCreMethod_BusinessDayCalendar"),

	/** 月間パターン */
	/** The monthly pattern. */
	MONTHLY_PATTERN(1, "Enum_WorkScheduleBasicCreMethod_MonthlyPattern"),

	/** 個人曜日別 */
	/** The personal day of week. */
	PERSONAL_DAY_OF_WEEK(2, "Enum_WorkScheduleBasicCreMethod_PersonalDayOfWeek");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	/** The Constant values. */
	private final static WorkScheduleBasicCreMethod[] values = WorkScheduleBasicCreMethod.values();

	/**
	 * Instantiates a new rounding.
	 *
	 * @param value
	 *            the value
	 * @param nameId
	 *            the name id
	 */
	private WorkScheduleBasicCreMethod(int value, String nameId) {
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
	public static WorkScheduleBasicCreMethod valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (WorkScheduleBasicCreMethod val : WorkScheduleBasicCreMethod.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
