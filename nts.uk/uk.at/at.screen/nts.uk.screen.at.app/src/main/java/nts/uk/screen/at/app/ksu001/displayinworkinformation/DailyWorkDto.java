/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.screen.at.app.ksu001.displayinworkinformation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.worktype.DailyWork;

/**
 * The Class DailyWork.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DailyWorkDto  { 

	/** The work type unit. */
	// 勤務区分
	public int workTypeUnit;

	/** The one day. */
	// 1日
	public int oneDay;

	/** The morning. */
	// 午前
	public int morning;

	/** The afternoon. */
	// 午後
	public int afternoon;

	public DailyWorkDto(DailyWork dailyWork) {
		super();
		this.workTypeUnit = dailyWork.getWorkTypeUnit() == null ? null : dailyWork.getWorkTypeUnit().value;
		this.oneDay = dailyWork.getOneDay() == null ? null : dailyWork.getOneDay().value;
		this.morning = dailyWork.getMorning() == null ? null : dailyWork.getMorning().value;
		this.afternoon = dailyWork.getAfternoon() == null ? null : dailyWork.getAfternoon().value;
	}
}
