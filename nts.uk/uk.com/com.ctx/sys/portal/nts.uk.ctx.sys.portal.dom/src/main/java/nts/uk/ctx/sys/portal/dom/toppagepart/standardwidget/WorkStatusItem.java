package nts.uk.ctx.sys.portal.dom.toppagepart.standardwidget;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.システム.ポータル.トップページの部品.標準ウィジェット.勤務状況の項目
 * 
 * @author tutt
 *
 */
public enum WorkStatusItem {
	/**
	 * 0 - 日別実績のエラー
	 */
	DAILY_PERFORMANCE_ERROR(0, "日別実績のエラー"),

	/**
	 * 1 - 残業時間
	 */
	OVERTIME(1, "残業時間"),

	/**
	 * 2 - フレックス時間
	 */
	FLEX_TIME(2, "フレックス時間"),

	/**
	 * 3 - 就業時間外深夜時間
	 */
	MIDNIGHT_TIME(3, " 就業時間外深夜時間"),

	/**
	 * 4 - 休日出勤時間
	 */
	HOLIDAY_WORKTIME(4, "休日出勤時間"),

	/**
	 * 5 - 遅刻早退回数
	 */
	LATE__EARLY_TIMES(5, "遅刻早退回数"),

	/**
	 * 6 - 年休残数
	 */
	ANNUAL_LEAVE_REMAINING_NUMBER(6, "年休残数"),

	/**
	 * 7 - 積立年休残数
	 */
	RESERVE_LEAVE_REMAINING_NUMBER(7, "積立年休残数"),

	/**
	 * 8 - 代休残数
	 */
	SUBSTITUDE_HOLIDAY_REMAINING_NUMBER(8, "代休残数"),

	/**
	 * 9 - 年休残数
	 */
	HOLIDAY_REMAINING_NUMBER(9, "年休残数"),

	/**
	 * 10 - 子の看護残数
	 */
	CHILDREN_NURSE_REMAINING_NUMBER(10, "子の看護残数"),

	/**
	 * 11 - 介護残数
	 */
	NURSING_REMAINING_NUMBER(11, "介護残数"),

	/**
	 * 12 - 特休残数
	 */
	SPECIAL_HOLIDAY_REMAINING_NUMBER(12, "特休残数");

	WorkStatusItem(int type, String name) {
		this.value = type;
		this.name = name;
	}

	public final int value;
	public final String name;
}
