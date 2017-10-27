/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktype;

import lombok.AllArgsConstructor;

/**
 * The Enum WorkTypeClassification
 * 勤務種類の分類
 */
@AllArgsConstructor
public enum WorkTypeClassification {

	/**
	 * 出勤
	 */
	Attendance(0, "Enum_WorkTypeClassification_Attendance"),
	/**
	 * 休日
	 */
	Holiday(1, "Enum_WorkTypeClassification_Holiday"),
	/**
	 * 年休
	 */
	AnnualHoliday(2, "Enum_WorkTypeClassification_AnnualHoliday"), 
	/**
	 * 積立年休
	 */
	YearlyReserved(3, "Enum_WorkTypeClassification_YearlyReserved"), 
	/**
	 * 特別休暇
	 */
	SpecialHoliday(4, "Enum_WorkTypeClassification_SpecialHoliday"), 
	/**
	 * 欠勤
	 */
	Absence(5, "Enum_WorkTypeClassification_Absence"), 
	/**
	 * 代休
	 */
	SubstituteHoliday(6, "Enum_WorkTypeClassification_SubstituteHoliday"), 
	/**
	 * 振出
	 */
	Shooting(7, "Enum_WorkTypeClassification_Shooting"), 
	/**
	 * 振休
	 */
	Pause(8, "Enum_WorkTypeClassification_Pause"), 
	/**
	 * 時間消化休暇
	 */
	TimeDigestVacation(9, "Enum_WorkTypeClassification_TimeDigestVacation"), 
	/**
	 * 連続勤務
	 */
	ContinuousWork(10, "Enum_WorkTypeClassification_ContinuousWork"),
	/**
	 * 休日出勤
	 */
	HolidayWork(11, "Enum_WorkTypeClassification_HolidayWork"),
	/**
	 * 休職
	 */
	LeaveOfAbsence(12, "Enum_WorkTypeClassification_LeaveOfAbsence"), 
	/**
	 * 休業
	 */
	Closure(13, "Enum_WorkTypeClassification_Closure");

	/** The value. */
	public final int value;
	public final String nameId;
}
