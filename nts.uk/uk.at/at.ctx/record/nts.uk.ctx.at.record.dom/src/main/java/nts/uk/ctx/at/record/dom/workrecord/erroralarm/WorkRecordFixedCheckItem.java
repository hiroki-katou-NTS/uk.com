package nts.uk.ctx.at.record.dom.workrecord.erroralarm;
/**
 * 日次の抽出するデータの条件
 * @author tutk
 *
 */
public enum WorkRecordFixedCheckItem {
	/**1:勤務種類未登録	 */
	WORK_TYPE_NOT_REGISTERED(1,"勤務種類未登録"),
	/**2: 就業時間帯未登録	 */
	WORKING_HOURS_NOT_REGISTERED(2,"就業時間帯未登録"),
	/**3:本人未確認	 */
	UNIDENTIFIED_PERSON(3,"本人未確認"),
	/**4:管理者未確認	 */
	DATA_CHECK(4,"管理者未確認"),
	/**5: データのチェック	 */
	ADMINISTRATOR_NOT_CONFIRMED(5,"データのチェック"),
	/**6: 連続休暇チェック	 */
	CONTINUOUS_VATATION_CHECK(6,"連続休暇チェック"),
	/**7: 打刻漏れ(入退門)	 */
	GATE_MISS_STAMP(7, "打刻漏れ(入退門)"),
	/**8: 打刻順序不正	 */
	MISS_ORDER_STAMP(8,"打刻順序不正"),
	/**9: 打刻順序不正（入退門）	 */
	GATE_MISS_ORDER_STAMP(9, "打刻順序不正（入退門）"),
	/**10:	休日打刻 */
	MISS_HOLIDAY_STAMP(10, "休日打刻"),
	/**11: 	休日打刻(入退門) */
	GATE_MISS_HOLIDAY_STAMP(11, "休日打刻(入退門)"),
	/**12: 加給コード未登録 	 */
	ADDITION_NOT_REGISTERED(12,"加給コード未登録"),
	/**13: 契約時間超過	 */
	CONTRACT_TIME_EXCEEDED(13,"契約時間超過"),
	/**	14:契約時間未満 */
	LESS_THAN_CONTRACT_TIME(14,"契約時間未満"),
	/** 15: 曜日別の違反*/
	VIOLATION_DAY_OF_WEEK(15, "曜日別の違反"),
	/** 16: 曜日別の就業時間帯不正 */
	ILL_WORK_TIME_DAY_THE_WEEK(16,"曜日別の就業時間帯不正"),
	/** 17: 乖離エラー */
	DISSOCIATION_ERROR(17, "乖離エラー"),
	/** 18: 手入力 */
	MANUAL_INPUT(18,"手入力"),
	/** 19: 二重打刻 */
	DOUBLE_STAMP(19, "二重打刻"),
	/** 20: 未計算 */
	UNCALCULATED(20, "未計算"),
	/** 21: 過剰申請・入力 */
	OVER_APP_INPUT(21, "過剰申請・入力"),
	/** 22: 複数回勤務 */
	MULTI_WORK_TIMES(22, "複数回勤務"),
	/** 23: 臨時勤務 */
	TEMPORARY_WORK(23, "臨時勤務"),
	/** 24: 特定日出勤 */
	SPEC_DAY_WORK(24, "特定日出勤"),
	/** 25: 未反映打刻 */
	UNREFLECTED_STAMP(25, "未反映打刻"),
	/** 26: 実打刻オーバー */
	ACTUAL_STAMP_OVER(26,"実打刻オーバー"),
	/** 27: 二重打刻(入退門) */
	GATE_DOUBLE_STAMP(27, "二重打刻(入退門)"),
	/** 28: 乖離アラーム */
	DISSOCIATION_ALARM(28,"乖離アラーム");
	
	public int value;
	
	public String nameId;
	
	private WorkRecordFixedCheckItem (int value,String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
