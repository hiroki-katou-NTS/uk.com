package nts.uk.ctx.at.record.app.find.divergence.time;

/**
 * The Enum ScreenUseAtr.
 */
// 勤怠項目を利用する画面
public enum ScreenUseAtr {

	/** The personnel expenses. */
	PERSONNEL_EXPENSES(0, "人件費"),

	/** The divergence time. */
	DIVERGENCE_TIME(1, "★乖離時間"),

	/** The over time. */
	OVER_TIME(2, "時間外超過"),

	/** The entry divergence time. */
	ENTRY_DIVERGENCE_TIME(3, "入館乖離時間"),

	/** The evacuation departure timr. */
	EVACUATION_DEPARTURE_TIMR(4, "退館乖離時間"),

	/** The pclogon divergence time. */
	PCLOGON_DIVERGENCE_TIME(5, "PCログオン乖離時間"),

	/** The pclogoff divergence time. */
	PCLOGOFF_DIVERGENCE_TIME(6, "PCログオフ乖離時間"),

	/** The predetermined break time divergence. */
	PREDETERMINED_BREAK_TIME_DIVERGENCE(7, "所定内休憩乖離時間"),

	/** The non scheduled divergence time. */
	NON_SCHEDULED_DIVERGENCE_TIME(8, "所定外休憩乖離時間"),

	/** The premature overtime departure time. */
	PREMATURE_OVERTIME_DEPARTURE_TIME(9, "早出残業乖離時間"),

	/** The normal overtime deviation time. */
	NORMAL_OVERTIME_DEVIATION_TIME(10, "普通残業乖離時間"),

	/** The holidays departure time. */
	HOLIDAYS_DEPARTURE_TIME(11, "休日出勤乖離時間"),

	/** The arbitrary divergence time. */
	ARBITRARY_DIVERGENCE_TIME(12, "任意乖離時間"),

	/** The attendance type of dervicetype. */
	ATTENDANCE_TYPE_OF_DERVICETYPE(13, "出勤簿勤務種類"),

	/** The employee booking hours. */
	EMPLOYEE_BOOKING_HOURS(14, "出勤簿就業時間帯"),

	/** The time of cumulative arrival. */
	TIME_OF_CUMULATIVE_ARRIVAL(15, "出勤簿時刻"),

	/** The work time. */
	WORK_TIME(16, "出勤簿時間"),

	/** The attendance times. */
	ATTENDANCE_TIMES(17, "出勤簿回数"),

	/** The total commuting amount. */
	TOTAL_COMMUTING_AMOUNT(18, "出勤簿金額"),

	/** The daily work schedule. */
	DAILY_WORK_SCHEDULE(19, "日別勤務表");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	/** The Constant values. */
	private final static ScreenUseAtr[] values = ScreenUseAtr.values();

	/**
	 * Instantiates a new rounding.
	 *
	 * @param value
	 *            the value
	 * @param nameId
	 *            the name id
	 */
	private ScreenUseAtr(int value, String nameId) {
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
	public static ScreenUseAtr valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (ScreenUseAtr val : ScreenUseAtr.values) {
			if (val.value == value) {

				return val;
			}
		}

		// Not found.
		return null;
	}

	/**
	 * Value of string.
	 *
	 * @param value
	 *            the value
	 * @return the screen use atr
	 */
	public static ScreenUseAtr valueOfString(String value) {
		// Invalid object.
		if (value == "") {
			return null;
		}

		// Find value.
		for (ScreenUseAtr val : ScreenUseAtr.values) {
			if (value.equals(val.nameId)) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
