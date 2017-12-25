package nts.uk.ctx.at.shared.dom.worktype;
/**
 * 休暇種類
 * @author keisuke_hoshina
 *
 */
public enum VacationCategory {
	Absence, // 欠勤
	AnnualHoliday, // 年休
	Holiday, // 休日
	Pause, // 振休
	SpecialHoliday, // 特別休暇
	SubstituteHoliday, // 代休
	TimeDigestVacation, // 時間消化休暇
	YearlyReserved; // 積立年休
	
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
