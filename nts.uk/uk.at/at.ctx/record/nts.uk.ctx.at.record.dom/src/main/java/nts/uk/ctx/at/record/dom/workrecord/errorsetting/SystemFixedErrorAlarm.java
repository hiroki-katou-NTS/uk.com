package nts.uk.ctx.at.record.dom.workrecord.errorsetting;

/*
 * システム固定のエラーアラーム
 */
public enum SystemFixedErrorAlarm {

	// 出退勤打刻漏れ
	TIME_LEAVING_STAMP_LEAKAGE("S001"),
	
	//PCログ打刻漏れ
	PCLOG_STAMP_LEAKAGE("S002"),
	
	// 入退門打刻漏れ
	ENTRANCE_STAMP_LACKING("S003"),
	
	// 打刻順序不正
	INCORRECT_STAMP("S004"),
	
	// 休日打刻
	HOLIDAY_STAMP("S005"),
	
	// 二重打刻
	DOUBLE_STAMP("S006"),
	
	// 遅刻
	LATE("007"),
	
	// 早退
	LEAVE_EARLY("008"),
	
	// 事前残業申請超過
	PRE_OVERTIME_APP_EXCESS("S009"),
	
	//事前休出申請超過
	PRE_HOLIDAYWORK_APP_EXCESS("S010"),
	
	//事前フレックス申請超過
	PRE_FLEX_APP_EXCESS("S011"),
	
	//事前深夜申請超過
	PRE_MIDNIGHT_EXCESS("S012"),

	// 残業時間超過
	OVER_TIME_EXCESS("S013"),

	// 休出時間超過
	REST_TIME_EXCESS("S014"),

	// 深夜時間超過
	MIDNIGHT_EXCESS("S015"),
	
	// フレックス時間超過
	FLEX_OVER_TIME("S016"),
	
	// 乖離時間のエラー
	ERROR_OF_DIVERGENCE_TIME("S017"),

	// 乖離時間のアラーム
	ALARM_OF_DIVERGENCE_TIME("S018"),

	// 乖離理由漏れ
	LEAKAGE_REASON("S019"),

	// 複数回勤務
	MULTIPLE_TIME_WORK("S020"),

	// 臨時勤務
	TEMPORARY_WORK("S021"),
	
	// 特定日
	SPECIFIC_DATE("S022"),

	//勤務種類未登録
	UNREGIST_WORKTYPE("S023"),
	
	//就業時間帯未登録
	UNREGIST_WORKTIME("S024"),

	NOT_CREATE("S025");

	public String value;

	private SystemFixedErrorAlarm(String value) {
		this.value = value;
	}
}
