package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.erroralarm;

import java.util.Optional;

import lombok.Getter;

/*
 * システム固定のエラーアラーム
 */
@Getter
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
	LATE("S007"),
	
	// 早退
	LEAVE_EARLY("S008"),
	
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

	NOT_CREATE("S025"),
	
	//設定外申告
	DECLARE("S026"),
	
	//乖離時間1エラー
	DIVERGENCE_ERROR_1("D001"),
	
	//乖離時間1アラーム
	DIVERGENCE_ALARM_1("D002"),
	
	//乖離時間2エラー
	DIVERGENCE_ERROR_2("D003"),
	
	//乖離時間2アラーム
	DIVERGENCE_ALARM_2("D004"),
	
	//乖離時間3エラー
	DIVERGENCE_ERROR_3("D005"),
	
	//乖離時間3アラーム
	DIVERGENCE_ALARM_3("D006"),
	
	//乖離時間4エラー
	DIVERGENCE_ERROR_4("D007"),
	
	//乖離時間4アラーム
	DIVERGENCE_ALARM_4("D008"),
	
	//乖離時間5エラー
	DIVERGENCE_ERROR_5("D009"),
	
	//乖離時間5アラーム
	DIVERGENCE_ALARM_5("D010"),
	
	//乖離時間6エラー
	DIVERGENCE_ERROR_6("D011"),
	
	//乖離時間6アラーム
	DIVERGENCE_ALARM_6("D012"),
	
	//乖離時間7エラー
	DIVERGENCE_ERROR_7("D013"),
	
	//乖離時間7アラーム
	DIVERGENCE_ALARM_7("D014"),
	
	//乖離時間8エラー
	DIVERGENCE_ERROR_8("D015"),
	
	//乖離時間8アラーム
	DIVERGENCE_ALARM_8("D016"),
	
	//乖離時間9エラー
	DIVERGENCE_ERROR_9("D017"),
	
	//乖離時間9アラーム
	DIVERGENCE_ALARM_9("D018"),
	
	//乖離時間10エラー
	DIVERGENCE_ERROR_10("D019"),
	
	//乖離時間10アラーム
	DIVERGENCE_ALARM_10("D020");
	
	public String value;

	private SystemFixedErrorAlarm(String value) {
		this.value = value;
	}
	/**
	 * エラーコードからEnumを取得 
	 * @return　Enum
	 */
	public static Optional<SystemFixedErrorAlarm> getEnumFromErrorCode(String errorCode) {
		for(SystemFixedErrorAlarm item: values()) {
			if(item.getValue().equals(errorCode))
				return Optional.of(item);
		}
		return Optional.empty();
	}
}
