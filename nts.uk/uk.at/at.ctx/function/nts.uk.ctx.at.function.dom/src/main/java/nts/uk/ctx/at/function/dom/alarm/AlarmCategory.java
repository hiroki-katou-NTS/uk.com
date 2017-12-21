package nts.uk.ctx.at.function.dom.alarm;

/**
 * @author dxthuong
 * アラームリストのカテゴリ
 */
public enum AlarmCategory {

	/**
	 * スケジュール日次
	 */
	SCHEDULE_DAILY(0, "Enum_AlarmCategory_SCHEDULE_DAILY", "スケジュール日次"),
	/**
	 * スケジュール週次
	 */
	SCHEDULE_WEEKLY(1, "Enum_AlarmCategory_SCHEDULE_WEEKLY", "スケジュール週次"),
	/**
	 * スケジュール4週
	 */
	SCHEDULE_4WEEK(2, "Enum_AlarmCategory_SCHEDULE_4WEEK", "スケジュール4週"),
	/**
	 * スケジュール月次
	 */
	SCHEDULE_MONTHLY(3, "Enum_AlarmCategory_SCHEDULE_MONTHLY", "スケジュール月次"),
	/**
	 * スケジュール年間
	 */
	SCHEDULE_YEAR(4, "Enum_AlarmCategory_SCHEDULE_YEAR", "スケジュール年間"),
	/**
	 * 日次
	 */
	DAILY(5, "Enum_AlarmCategory_DAILY", "日次"),
	/**
	 * 週次
	 */
	WEEKLY(6, "Enum_AlarmCategory_WEEKLY", "週次"),
	/**
	 * 月次
	 */
	MONTHLY(7, "Enum_AlarmCategory_MONTHLY", "月次"),
	/**
	 * 申請承認
	 */
	APPLICATION_APPROVAL(8, "Enum_AlarmCategory_APPLICATION_APPROVAL", "申請承認"),
	/**
	 * 複数月
	 */
	MULTIPLE_MONTH(9, "Enum_AlarmCategory_MULTIPLE_MONTH", "複数月"),
	/**
	 * 任意期間
	 */
	ANY_PERIOD(10, "Enum_AlarmCategory_ANY_PERIOD", "任意期間"),
	/**
	 * 年休付与用出勤率
	 */
	ATTENDANCE_RATE_FOR_HOLIDAY(11, "Enum_AlarmCategory_ATTENDANCE_RATE_FOR_HOLIDAY", "年休付与用出勤率"),
	/**
	 * ３６協定
	 */
	AGREEMENT(12, "Enum_AlarmCategory_AGREEMENT", "３６協定"),
	/**
	 * 工数チェック
	 */
	MAN_HOUR_CHECK(13, "Enum_AlarmCategory_MAN_HOUR_CHECK", "工数チェック");

	
	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	/** The description. */
	public final String description;

	/** The Constant values. */
	private final static AlarmCategory[] values = AlarmCategory.values();
	
	private AlarmCategory(int value, String nameId, String description) {
		this.value = value;
		this.nameId = nameId;
		this.description = description;
	}
	public static AlarmCategory valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (AlarmCategory val : AlarmCategory.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
