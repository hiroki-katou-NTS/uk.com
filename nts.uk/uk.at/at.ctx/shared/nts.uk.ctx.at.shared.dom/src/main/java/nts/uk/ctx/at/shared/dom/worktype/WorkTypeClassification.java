/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktype;

import lombok.AllArgsConstructor;

/**
 * The Enum WorkTypeClassification.
 */
@AllArgsConstructor
// 勤務種類の分類
public enum WorkTypeClassification {

	Attendance(0, "Enum_WorkTypeClassification_Attendance"), // 出勤
	Holiday(1, "Enum_WorkTypeClassification_Holiday"), // 休日
	AnnualHoliday(2, "Enum_WorkTypeClassification_AnnualHoliday"), // 年休
	YearlyReserved(3, "Enum_WorkTypeClassification_YearlyReserved"), // 積立年休
	SpecialHoliday(4, "Enum_WorkTypeClassification_SpecialHoliday"), // 特別休暇
	Absence(5, "Enum_WorkTypeClassification_Absence"), // 欠勤
	SubstituteHoliday(6, "Enum_WorkTypeClassification_SubstituteHoliday"), // 代休
	Shooting(7, "Enum_WorkTypeClassification_Shooting"), // 振出
	Pause(8, "Enum_WorkTypeClassification_Pause"), // 振休
	TimeDigestVacation(9, "Enum_WorkTypeClassification_TimeDigestVacation"), // 時間消化休暇
	ContinuousWork(10, "Enum_WorkTypeClassification_ContinuousWork"), // 連続勤務
	HolidayWork(11, "Enum_WorkTypeClassification_HolidayWork"), // 休日出勤
	LeaveOfAbsence(12, "Enum_WorkTypeClassification_LeaveOfAbsence"), // 休職
	Closure(13, "Enum_WorkTypeClassification_Closure"); // 休業

	/** The value. */
	public final int value;
	public final String nameId;
}
