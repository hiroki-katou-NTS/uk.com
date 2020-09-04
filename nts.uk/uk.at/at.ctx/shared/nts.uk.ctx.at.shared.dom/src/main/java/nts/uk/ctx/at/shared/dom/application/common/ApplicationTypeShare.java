package nts.uk.ctx.at.shared.dom.application.common;

import lombok.AllArgsConstructor;

/**
 * @author thanh_nx
 *
 *         申請種類
 */
@AllArgsConstructor
public enum ApplicationTypeShare {

	/**
	 * 0: 残業申請
	 */
	OVER_TIME_APPLICATION(0, "残業申請"),

	/**
	 * 1: 休暇申請
	 */
	ABSENCE_APPLICATION(1, "休暇申請"),

	/**
	 * 2: 勤務変更申請
	 */
	WORK_CHANGE_APPLICATION(2, "勤務変更申請"),

	/**
	 * 3: 出張申請
	 */
	BUSINESS_TRIP_APPLICATION(3, "出張申請"),

	/**
	 * 4: 直行直帰申請
	 */
	GO_RETURN_DIRECTLY_APPLICATION(4, "直行直帰申請"),

	/**
	 * 6: 休出時間申請
	 */
	HOLIDAY_WORK_APPLICATION(6, "休出時間申請"),

	/**
	 * 7: 打刻申請
	 */
	STAMP_APPLICATION(7, "打刻申請"),

	/**
	 * 8: 時間休暇申請
	 */
	ANNUAL_HOLIDAY_APPLICATION(8, "時間休暇申請"),

	/**
	 * 9: 遅刻早退取消申請
	 */
	EARLY_LEAVE_CANCEL_APPLICATION(9, "遅刻早退取消申請"),

	/**
	 * 10: 振休振出申請
	 */
	COMPLEMENT_LEAVE_APPLICATION(10, "振休振出申請"),

	/**
	 * 15: 任意項目申請
	 */
	OPTIONAL_ITEM_APPLICATION(15, "任意項目申請");

	public int value;

	public String name;

	private final static ApplicationTypeShare[] values = ApplicationTypeShare.values();

	public static ApplicationTypeShare valueOf(Integer value) {
		if (value == null) {
			return null;
		}

		for (ApplicationTypeShare val : ApplicationTypeShare.values) {
			if (val.value == value) {
				return val;
			}
		}

		return null;
	}

}
