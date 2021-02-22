package nts.uk.ctx.at.aggregation.dom.scheduletable;

/**
 * スケジュール表の勤怠項目
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.就業機能.スケジュール表.スケジュール表の勤怠項目
 * @author dan_pv
 *
 */
public enum ScheduleTableAttendanceItem {

	/** シフト */
	SHIFT(0, "シフト"),

	/** 勤務種類 */
	WORK_TYPE(1, "勤務種類"),

	/** 就業時間帯 */
	WORK_TIME(2, "就業時間帯"),

	/** 開始時刻 */
	START_TIME(3, "開始時刻 "),

	/** 終了時刻 */
	END_TIME(4, "終了時刻 "),

	/** 開始時刻２ */
	START_TIME_2(5, "開始時刻２"),

	/** 終了時刻２ */
	END_TIME_2(6, "終了時刻２"),

	/** 総労働時間 */
	TOTAL_WORKING_HOURS(7, "総労働時間"),

	/** 就業時間 */
	WORKING_HOURS(8, "就業時間"),

	/** 実働時間 */
	ACTUAL_WORKING_HOURS(9, "実働時間"),

	/** 法定時間外時間 */
	LEGAL_OVERTIME(10, "法定時間外時間"),

	/** 法定外時間外時間 */
	NON_LEGAL_OVERTIME(11, "法定外時間外時間"),

	/** 法定休出時間 */
	LEGAL_WORING_ON_DAYOFF_TIME(12, "法定休出時間 "),

	/** 法定外休出時間 */
	NON_LEGAL_WORING_ON_DAYOFF_TIME(13, "法定外休出時間"),
	
	/** 夜勤時間 */
	NIGHT_WORKING_TIME(14, "夜勤時間");

	public int value;
	public String nameId;

	private ScheduleTableAttendanceItem(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}

}
