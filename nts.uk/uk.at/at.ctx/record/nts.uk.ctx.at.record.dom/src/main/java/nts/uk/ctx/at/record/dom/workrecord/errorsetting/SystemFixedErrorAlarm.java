package nts.uk.ctx.at.record.dom.workrecord.errorsetting;

/*
 * システム固定のエラーアラーム
 */
public enum SystemFixedErrorAlarm {

	// フレックス時間超過
	FLEX_OVER_TIME(0),

	// 休出時間超過
	REST_TIME_EXCESS(1),

	// 休日打刻
	HOLIDAY_STAMP(3),

	// 残業時間超過
	OVER_TIME_EXCESS(4),

	// 事前申請超過
	PRE_APPLICATION_EXCESS(5),

	// 出退勤打刻漏れ
	TIME_LEAVING_STAMP_LEAKAGE(6),

	// 深夜時間超過
	MIDNIGHT_EXCESS(7),

	// 早退
	LEAVE_EARLY(8),

	// 打刻順序不正
	INCORRECT_STAMP(9),

	// 遅刻
	LATE(10),

	// 特定日
	SPECIFIC_DATE(11),

	// 二重打刻
	DOUBLE_STAMP(12),

	// 入退門打刻漏れ
	ENTRANCE_STAMP_LACKING(13),

	// 複数回勤務
	MULTIPLE_TIME_WORK(14),

	// 臨時勤務
	TEMPORARY_WORK(15),

	// 乖離時間のアラーム
	ALARM_OF_DIVERGENCE_TIME(16),

	// 乖離時間のエラー
	ERROR_OF_DIVERGENCE_TIME(17),

	// 乖離理由漏れ
	LEAKAGE_REASON(18);

	public int value;

	private SystemFixedErrorAlarm(int value) {
		this.value = value;
	}
}
