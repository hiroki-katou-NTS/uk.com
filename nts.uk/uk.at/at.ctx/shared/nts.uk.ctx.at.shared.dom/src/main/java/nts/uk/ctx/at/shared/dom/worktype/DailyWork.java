/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktype;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * The Class DailyWork.
 */
@NoArgsConstructor
@AllArgsConstructor
public class DailyWork { // 1日の勤務

	/** The work type unit. */
	private WorkTypeUnit workTypeUnit;// 勤務区分

	/** The one day. */
	private WorkTypeClassification oneDay;// 1日

	/** The morning. */
	private WorkTypeClassification morning; // 午前

	/** The afternoon. */
	private WorkTypeClassification afternoon; // 午後
	
	/**
	 * 出勤区分区分の取得
	 * @return 出勤休日区分
	 */
	public AttendanceHolidayAttr getAttendanceHolidayAttr() {
		if (this.workTypeUnit == WorkTypeUnit.OneDay) {
			if (this.oneDay.isHoliday()) {
				return AttendanceHolidayAttr.HOLIDAY;
			} else {
				return AttendanceHolidayAttr.FULL_TIME;
			}
		} else {
			if (this.morning.isAttendance() && this.afternoon.isAttendance()) {
				return AttendanceHolidayAttr.FULL_TIME;
			} else if (this.morning.isAttendance() && this.afternoon.isHoliday()) {
				return AttendanceHolidayAttr.MORNING;
			} else if (this.morning.isHoliday() && this.afternoon.isAttendance()) {
				return AttendanceHolidayAttr.AFTERNOON;
			} else {
				return AttendanceHolidayAttr.HOLIDAY;
			}
		}
	}
	
	/**
	 * 平日出勤か判定
	 * @return　平日出勤である
	 */
	public boolean isWeekDayAttendance() {
		if(this.workTypeUnit == WorkTypeUnit.OneDay) {
			return this.oneDay.isWeekDayAttendance();
		}
		else if(this.workTypeUnit == WorkTypeUnit.MonringAndAfternoon) {
			return morning.isWeekDayAttendance() || afternoon.isWeekDayAttendance();
		}
		return false;
	}
}
