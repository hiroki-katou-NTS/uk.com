/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flexworkset;

import java.util.List;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.worktime.common.FixedWorkTimezoneSet;
import nts.uk.ctx.at.shared.dom.worktime.fluidworkset.FluRestTime;

/**
 * The Class WeekdayWorkTime.
 */
// 固定勤務の平日出勤用勤務時間帯
@Getter
public class FleWeekdayWorkTime {

	// 勤務時間帯
	 private FixedWorkTimezoneSet workingTime;

	/** The rest time. */
	// 休憩時間帯
	private List<FluRestTime> restTime;

	/** The am pm cls. */
	// TODO: ko thấy define???
	// 午前午後区分
	// private AmPmClassification amPmCls;
}
