package nts.uk.ctx.at.function.dom.scheduletable;

/**
 * スケジュール表の勤怠項目
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.就業機能.スケジュール表.スケジュール表の勤怠項目
 * @author dan_pv
 *
 */
public enum ScheduleTableAttendanceItem {

	/** シフト */
	SHIFT(0),

	/** 勤務種類 */
	WORK_TYPE(1),

	/** 就業時間帯 */
	WORK_TIME(2),

	/** 開始時刻 */
	START_TIME(3),

	/** 終了時刻 */
	END_TIME(4),

	/** 開始時刻２ */
	START_TIME_2(5),

	/** 終了時刻２ */
	END_TIME_2(6),

	/** 総労働時間 */
	TOTAL_WORKING_HOURS(7),

	/** 就業時間 */
	WORKING_HOURS(8),

	/** 実働時間 */
	ACTUAL_WORKING_HOURS(9),

	/** 法定時間外時間 */
	LEGAL_OVERTIME(10),

	/** 法定外時間外時間 */
	NON_LEGAL_OVERTIME(11),

	/** 法定休出時間 */
	LEGAL_WORING_ON_DAYOFF_TIME(12),

	/** 法定外休出時間 */
	NON_LEGAL_WORING_ON_DAYOFF_TIME(13),
	
	/** 夜勤時間 */
	NIGHT_WORKING_TIME(14),
	
	/** 確定区分 */
	CONFIRMED_ATR(15);

	public int value;

	private ScheduleTableAttendanceItem(int value) {
		this.value = value;
	}

}
