/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktype;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class DailyWork.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DailyWork extends DomainObject { // 1日の勤務

	/** The work type unit. */
	// 勤務区分
	private WorkTypeUnit workTypeUnit;

	/** The one day. */
	// 1日
	private WorkTypeClassification oneDay;

	/** The morning. */
	// 午前
	private WorkTypeClassification morning;

	/** The afternoon. */
	// 午後
	private WorkTypeClassification afternoon;

	/**
	 * check leave for a a morning
	 * 
	 * @return true leave for morning else false
	 */
	public boolean IsLeaveForMorning() {
		return this.checkLeave(this.morning);
	}

	/**
	 * check leave for a afternoon
	 * 
	 * @return true leave for a afternoon else false
	 */
	public boolean IsLeaveForAfternoon() {
		return this.checkLeave(this.afternoon);
	}

	/**
	 * check leave for a day
	 * 
	 * @return true leave for a day else false
	 */
	public boolean IsLeaveForADay() {
		return this.checkLeave(this.oneDay);
	}

	/**
	 * check leave by
	 * 
	 * @param attribute
	 *            勤務種類の分類
	 * @return
	 */
	private boolean checkLeave(WorkTypeClassification attribute) {
		return WorkTypeClassification.Holiday == attribute || WorkTypeClassification.Pause == attribute
				|| WorkTypeClassification.AnnualHoliday == attribute
				|| WorkTypeClassification.YearlyReserved == attribute
				|| WorkTypeClassification.SpecialHoliday == attribute
				|| WorkTypeClassification.TimeDigestVacation == attribute
				|| WorkTypeClassification.SubstituteHoliday == attribute || WorkTypeClassification.Absence == attribute
				|| WorkTypeClassification.ContinuousWork == attribute || WorkTypeClassification.Closure == attribute
				|| WorkTypeClassification.LeaveOfAbsence == attribute;
	}

	/**
	 * 出勤区分区分の取得
	 * 
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
	 * 
	 * @return 平日出勤である
	 */
	public boolean isWeekDayAttendance() {
		if (this.workTypeUnit == WorkTypeUnit.OneDay) {
			return this.oneDay.isWeekDayAttendance();
		} else if (this.workTypeUnit == WorkTypeUnit.MonringAndAfternoon) {
			return morning.isWeekDayAttendance() || afternoon.isWeekDayAttendance();
		}
		return false;
	}

	/**
	 * 休日出勤か判定
	 * 
	 * @return true：休日出勤である false：休日出勤ではない
	 */
	public boolean isHolidayWork() {
		if (this.workTypeUnit == WorkTypeUnit.OneDay) {
			return this.oneDay.isHolidayWork();
		} else if (this.workTypeUnit == WorkTypeUnit.MonringAndAfternoon) {
			return morning.isHolidayWork() || afternoon.isHolidayWork();
		}
		return false;
	}

	/**
	 * 受け取った勤務種類の分類に一致しているか判定する
	 * 
	 * @param workTypeClassification
	 *            勤務種類の分類
	 * @return 何時一致しているか
	 */
	public AttendanceHolidayAttr decisionMatchWorkType(WorkTypeClassification workTypeClassification) {
		if (oneDay.equals(workTypeClassification)) {
			return AttendanceHolidayAttr.FULL_TIME;
		} else if (morning.equals(workTypeClassification)) {
			return AttendanceHolidayAttr.MORNING;
		} else if (afternoon.equals(workTypeClassification)) {
			return AttendanceHolidayAttr.AFTERNOON;
		} else {
			return AttendanceHolidayAttr.HOLIDAY;
		}
	}

	/**
	 * 1日-休業
	 * @return true is closure else false
	 */
	public boolean isClosure() {
		return WorkTypeClassification.Closure.equals(this.oneDay);
	}
	
	public boolean isOneDay() {
		return this.workTypeUnit == WorkTypeUnit.OneDay; 
	}

	public boolean isOneDayHoliday() {
		return this.oneDay == WorkTypeClassification.Holiday;
	}
	
}
