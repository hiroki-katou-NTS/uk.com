/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktype;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class DailyWork.
 */
//1日の勤務
@Getter
@Setter
public class DailyWork extends DomainObject { 

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
	 * @return true leave for morning else false
	 */
	public boolean IsLeaveForMorning() {
		return this.checkLeave(this.morning);
	}
	
	/**
	 * check leave for a afternoon
	 * @return true leave for a afternoon else false
	 */
	public boolean IsLeaveForAfternoon() {
		return this.checkLeave(this.afternoon);
	}
	
	/**
	 * check leave for a day
	 * @return true leave for a day else false
	 */
	public boolean IsLeaveForADay() {
		return this.checkLeave(this.oneDay);
	}
	
	/**
	 * check leave by 
	 * @param attribute 勤務種類の分類
	 * @return
	 */
	private boolean checkLeave(WorkTypeClassification attribute) {
		return WorkTypeClassification.Holiday == attribute
				|| WorkTypeClassification.Pause == attribute
				|| WorkTypeClassification.AnnualHoliday == attribute
				|| WorkTypeClassification.YearlyReserved == attribute
				|| WorkTypeClassification.SpecialHoliday == attribute
				|| WorkTypeClassification.TimeDigestVacation == attribute
				|| WorkTypeClassification.SubstituteHoliday == attribute
				|| WorkTypeClassification.Absence == attribute
				|| WorkTypeClassification.ContinuousWork == attribute
				|| WorkTypeClassification.Closure == attribute
				|| WorkTypeClassification.LeaveOfAbsence == attribute;
	}
}
