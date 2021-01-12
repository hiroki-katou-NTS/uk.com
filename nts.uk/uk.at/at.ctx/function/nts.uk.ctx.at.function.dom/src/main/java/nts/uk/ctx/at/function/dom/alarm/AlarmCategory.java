package nts.uk.ctx.at.function.dom.alarm;

/**
 * @author dxthuong
 * アラームリストのカテゴリ
 */
public enum AlarmCategory {

	/**
	 * スケジュール日次
	 */
	SCHEDULE_DAILY(0,  "スケジュール日次"),
	/**
	 * スケジュール週次
	 */
	SCHEDULE_WEEKLY(1, "スケジュール週次"),
	/**
	 * スケジュール4週
	 */
	SCHEDULE_4WEEK(2, "スケジュール4週"),
	/**
	 * スケジュール月次
	 */
	SCHEDULE_MONTHLY(3,  "スケジュール月次"),
	/**
	 * スケジュール年間
	 */
	SCHEDULE_YEAR(4,  "スケジュール年間"),
	/**
	 * 日次
	 */
	DAILY(5,  "日次"),
	/**
	 * 週次
	 */
	WEEKLY(6, "週次"),
	/**
	 * 月次
	 */
	MONTHLY(7, "月次"),
	/**
	 * 申請承認
	 */
	APPLICATION_APPROVAL(8, "申請承認"),
	/**
	 * 複数月
	 */
	MULTIPLE_MONTH(9, "複数月"),
	/**
	 * 任意期間
	 */
	ANY_PERIOD(10, "任意期間"),
	/**
	 * 年休
	 */
	ATTENDANCE_RATE_FOR_HOLIDAY(11,  "年休"),
	/**
	 * ３６協定
	 */
	AGREEMENT(12,  "３６協定"),
	/**
	 * 工数チェック
	 */
	MAN_HOUR_CHECK(13, "工数チェック"),

	/**
	 * マスタチェック
	 */
	MASTER_CHECK(14, "マスタチェック");

	
	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;


	
	private AlarmCategory(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}

}
