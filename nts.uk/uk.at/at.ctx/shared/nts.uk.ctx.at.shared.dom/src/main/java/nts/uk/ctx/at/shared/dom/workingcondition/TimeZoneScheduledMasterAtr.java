package nts.uk.ctx.at.shared.dom.workingcondition;

/**
 * The Enum TimeZoneScheduledMasterAtr.
 */
public enum TimeZoneScheduledMasterAtr {

	/** The follow master reference. */
	/** マスタ参照区分に従う */
	FOLLOW_MASTER_REFERENCE(0, "Enum_TimeZoneScheduledMasterAtr_FollowMasterReference"),

	/** The personal work daily. */
	/** 個人勤務日別 */
	PERSONAL_WORK_DAILY(1, "Enum_TimeZoneScheduledMasterAtr_PersonalWorkDaily"),

	/** The personal day of week. */
	/** 個人曜日別 */
	PERSONAL_DAY_OF_WEEK(2, "Enum_TimeZoneScheduledMasterAtr_PersonalDayOfWeek");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	/** The Constant values. */
	private final static TimeZoneScheduledMasterAtr[] values = TimeZoneScheduledMasterAtr.values();

	/**
	 * Instantiates a new rounding.
	 *
	 * @param value
	 *            the value
	 * @param nameId
	 *            the name id
	 */
	private TimeZoneScheduledMasterAtr(int value, String nameId) {
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
	public static TimeZoneScheduledMasterAtr valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (TimeZoneScheduledMasterAtr val : TimeZoneScheduledMasterAtr.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
