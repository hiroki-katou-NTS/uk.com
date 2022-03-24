package nts.uk.ctx.at.shared.dom.worktype;
/**
 * 休暇種類
 * @author keisuke_hoshina
 */
public enum VacationCategory {
	/** 欠勤 */
	Absence,
	/** 年休 */
	AnnualHoliday,
	/** 休日 */
	Holiday,
	/** 振休 */
	Pause,
	/** 特別休暇 */
	SpecialHoliday,
	/** 代休 */
	SubstituteHoliday,
	/** 時間消化休暇 */
	TimeDigestVacation,
	/** 積立年休 */
	YearlyReserved;
	
	 /**
	  * 休暇種類を勤務種類の分類　に変換する
	  */
	 public WorkTypeClassification convertWorkTypeClassification() {
	  switch(this) {
	  case Absence:
	   return WorkTypeClassification.Absence;
	  case AnnualHoliday:
	   return WorkTypeClassification.AnnualHoliday;
	  case Holiday:
	   return WorkTypeClassification.Holiday;
	  case Pause:
	   return WorkTypeClassification.Pause;
	  case SpecialHoliday:
	   return WorkTypeClassification.SpecialHoliday;
	  case SubstituteHoliday:
	   return WorkTypeClassification.SubstituteHoliday;
	  case TimeDigestVacation:
	   return WorkTypeClassification.TimeDigestVacation;
	  case YearlyReserved:
	   return WorkTypeClassification.YearlyReserved;
	  default:
	   throw new RuntimeException("unknown ");
	  }
	 }
	
}
