package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.algorithmdailyper;

import lombok.AllArgsConstructor;

/**
 * 終了状態
 * @author tutk
 *
 */
@AllArgsConstructor
public enum EndStatus {

	// 正常
	NORMAL(0, "正常"),

	// 就業時間帯なし
	NO_WORK_TIME(1, "就業時間帯なし"),

	// 勤務種類なし
	NO_WORK_TYPE(2, "勤務種類なし"),

	// 休日出勤設定なし
	NO_HOLIDAY_SETTING(3, "休日出勤設定なし"),
	
	//労働条件なし
	NO_WORK_CONDITION(4, "労働条件なし");

	public final int value;

	public final String name;

	/** The Constant values. */
	private final static EndStatus[] values = EndStatus.values();

	public static EndStatus valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (EndStatus val : EndStatus.values) {
			if (val.value == value) {
				return val;
			}
		}
		// Not found.
		return null;
	}
}
