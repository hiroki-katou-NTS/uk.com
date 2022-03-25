/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktype;

import lombok.AllArgsConstructor;

/**
 * The Enum WorkTypeClassification
 * 勤務種類の分類
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared.就業規則.勤務種類.勤務種類の分類
 */
@AllArgsConstructor
public enum WorkTypeClassification {

	/** 出勤 */
	Attendance(0, "Enum_WorkTypeClassification_Attendance"),
	/** 休日  */
	Holiday(1, "Enum_WorkTypeClassification_Holiday"),
	/** 年休 */
	AnnualHoliday(2, "Enum_WorkTypeClassification_AnnualHoliday"), 
	/** 積立年休 */
	YearlyReserved(3, "Enum_WorkTypeClassification_YearlyReserved"), 
	/** 特別休暇 */
	SpecialHoliday(4, "Enum_WorkTypeClassification_SpecialHoliday"), 
	/** 欠勤 */
	Absence(5, "Enum_WorkTypeClassification_Absence"), 
	/** 代休 */
	SubstituteHoliday(6, "Enum_WorkTypeClassification_SubstituteHoliday"), 
	/** 振出 */
	Shooting(7, "Enum_WorkTypeClassification_Shooting"), 
	/** 振休 */
	Pause(8, "Enum_WorkTypeClassification_Pause"),
	/** 時間消化休暇 */
	TimeDigestVacation(9, "Enum_WorkTypeClassification_TimeDigestVacation"),
	/** 連続勤務 */
	ContinuousWork(10, "Enum_WorkTypeClassification_ContinuousWork"),
	/** 休日出勤（休出） */
	HolidayWork(11, "Enum_WorkTypeClassification_HolidayWork"),
	/** 休職 */
	LeaveOfAbsence(12, "Enum_WorkTypeClassification_LeaveOfAbsence"), 
	/** 休業 */
	Closure(13, "Enum_WorkTypeClassification_Closure");

	/** The value. */
	public final int value;
	public final String nameId;
	
	/** The Constant values. */
	private final static WorkTypeClassification[] values = WorkTypeClassification.values();

	/**
	 * 休暇種類に変換する
	 * @return 休暇種類
	 */
	public VacationCategory convertVacationCategory() {
		switch (this){
		case Absence:
			return VacationCategory.Absence;
		case AnnualHoliday:
			return VacationCategory.AnnualHoliday;
		case Holiday:
			return VacationCategory.Holiday;
		case Pause:
			return VacationCategory.Pause;
		case SpecialHoliday:
			return VacationCategory.SpecialHoliday;
		case SubstituteHoliday:
			return VacationCategory.SubstituteHoliday;
		case TimeDigestVacation:
			return VacationCategory.TimeDigestVacation;
		case YearlyReserved:
			return VacationCategory.YearlyReserved;
		default:
			break;
		}
		throw new RuntimeException("unknown ");
	}
	
	/**
	 * if 休日、振休、休日出勤 true else false
	 */
	public boolean checkHolidayNew() {
		switch (this) {
		case HolidayWork:
		case Holiday:
		case Pause:
			return true;
		case Absence:
		case AnnualHoliday:
		case Closure:
		case ContinuousWork:
		case Attendance:
		case Shooting:
		case LeaveOfAbsence:
		case SpecialHoliday:
		case SubstituteHoliday:
		case TimeDigestVacation:
		case YearlyReserved:
			
			return false;
		default:
			throw new RuntimeException("invalid value: " + this);
		}
	}
	
	/**
	 * 勤務種類が休暇系か
	 */
	public boolean isHolidayWorkType() {
		switch (this) {
		case AnnualHoliday:
		case YearlyReserved:
		case SpecialHoliday:
		case SubstituteHoliday:
		case Holiday:
		case Absence:
			return true;

		default:
			return false;
		}

	}
	
	
	/**
	 * 年休であるか判定する
	 * @author ken_takasu
	 * @return
	 */
	public boolean isAnnualLeave() {
		return AnnualHoliday.equals(this);
	}
	
	/**
	 * 積立年休であるか判定する
	 * @author ken_takasu
	 * @return
	 */
	public boolean isYearlyReserved() {
		return YearlyReserved.equals(this);
	}
	
	/**
	 * 特別休暇であるか判定する
	 * @author ken_takasu
	 * @return
	 */
	public boolean isSpecialHoliday() {
		return SpecialHoliday.equals(this);
	}
	
	/**
	 * 欠勤であるか判定する
	 * @return true：欠勤である false：欠勤ではない
	 */
	public boolean isAbsence() {
		return Absence.equals(this);
	}
	
	/**
	 * 代休であるか判定する
	 * @return　代休である
	 */
	public boolean isSubstituteHoliday() {
		return SubstituteHoliday.equals(this);
	}
		/**
	 * 休日であるか判定する
	 * @return　休日である
	 */
	public boolean isHoliday() {
		return Holiday.equals(this);
	}
	/**
	 * 振出であるか判定する
	 * @return　振出である
	 */
	public boolean isShooting() {
		return Shooting.equals(this);
	}
	
	public boolean isAttendance() {
		return !this.isHolidayType();
	}
	
	/**
	 * 出勤系か判定する
	 * @return　平日出勤系である
	 */
	public boolean isWeekDayAttendance() {
		switch (this) {
		case Attendance:
		case Shooting:
		
			return true;
		case HolidayWork:
		case Absence:
		case AnnualHoliday:
		case Closure:
		case ContinuousWork:
		case Holiday:
		case LeaveOfAbsence:
		case Pause:
		case SpecialHoliday:
		case SubstituteHoliday:
		case TimeDigestVacation:
		case YearlyReserved:
			
			return false;
		default:
			throw new RuntimeException("invalid value: " + this);
		}
	}
	
	/**
	 * 休日系か判定する
	 * @return　休日系である
	 */
	public boolean isHolidayType() {
		switch (this) {
		case Absence:
		case AnnualHoliday:
		case Closure:
		case Holiday:
		case LeaveOfAbsence:
		case Pause:
		case SpecialHoliday:
		case SubstituteHoliday:
		case TimeDigestVacation:
		case YearlyReserved:
		case ContinuousWork:
			return true;
			
		case HolidayWork:
		case Attendance:
		case Shooting:
			return false;
			
		default:
			throw new RuntimeException("invalid value: " + this);
		}
	}
	
	/**
	 * 休日出勤(休出系)であるか判定する
	 * @return　休日出勤である
	 */
	public boolean isHolidayWork() {
		return HolidayWork.equals(this);
	}
	
	/**
	 * 連続勤務であるか判定する
	 * @return　連続勤務である
	 */
	public boolean isContinuousWork() {
		return ContinuousWork.equals(this);
	}
	/**
	 * 休暇系であるか判定する
	 * @return　休暇系である
	 */
	public boolean isVacation() {
		switch (this) {
		case AnnualHoliday:
		case SpecialHoliday:
		case Absence:
		case SubstituteHoliday:
		case TimeDigestVacation:
		case YearlyReserved:
			return true;
		case Closure:
		case Holiday:
		case LeaveOfAbsence:
		case Pause:
		case HolidayWork:
		case Attendance:
		case Shooting:
		case ContinuousWork:
			return false;
		default:
			throw new RuntimeException("invalid value: " + this);
		}
	}
	
	/**
	 * 休日系か判定する
	 * 所定時間取得時に休日系として判断する為の処理
	 * @return
	 */
	public boolean judgeHolidayType() {
		switch (this) {
		case Holiday:
		case Pause:
		case Closure:
		case LeaveOfAbsence:
		case ContinuousWork:
			return true;			
		case HolidayWork:
		case Attendance:
		case Shooting:
		case Absence:
		case AnnualHoliday:
		case SpecialHoliday:
		case SubstituteHoliday:
		case TimeDigestVacation:
		case YearlyReserved:
			return false;			
		default:
			throw new RuntimeException("invalid value: " + this);
		}
	}

	/**
	 * 振休であるか判定する
	 * @return 振休である
	 */
	public boolean isPause() {
		return Pause.equals(this);
	}
	
	/**
	 * Value of.
	 *
	 * @param value
	 *            the value
	 * @return the time day atr
	 */
	public static WorkTypeClassification valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (WorkTypeClassification val : WorkTypeClassification.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
	
	/**
	 * [1] どんな休暇種類か判断する
	 * @return 休暇種類区分 HolidayTypeClassification
	 */
	public HolidayTypeClassification determineHolidayType() {
	    switch (this) {
	    // 出勤
	    case Closure:
	    // 休職
	    case LeaveOfAbsence:
	    // 休日出勤
	    case HolidayWork:
	    // 連続勤務
	    case ContinuousWork:
	    // 振出
	    case Shooting:
	    // 欠勤
	    case Absence:
	    // 出勤
        case Attendance:
            return HolidayTypeClassification.NoVacation;
            
        // 休日
        case Holiday:
            return HolidayTypeClassification.Holiday;
            
        // 年休
        case AnnualHoliday:
            return HolidayTypeClassification.AnnualHoliday;
            
        // 積立年休
        case YearlyReserved:
            return HolidayTypeClassification.YearlyReserved;
            
        // 特別休暇
        case SpecialHoliday:
            return HolidayTypeClassification.SpecialHoliday;
            
        // 代休
        case SubstituteHoliday:
            return HolidayTypeClassification.SubstituteHoliday;
            
        // 振休
        case Pause:
            return HolidayTypeClassification.Pause;
            
        // 時間消化休暇
        case TimeDigestVacation:
            return HolidayTypeClassification.TimeDigestVacation;
            
        default:
            return HolidayTypeClassification.NoVacation;
        }
	}
}
