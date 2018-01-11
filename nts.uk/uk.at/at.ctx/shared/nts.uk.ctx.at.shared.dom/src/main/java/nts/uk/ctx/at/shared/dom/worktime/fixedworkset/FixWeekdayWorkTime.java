/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.fixedworkset;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.shared.dom.worktime.common.FixedWorkTimezoneSet;
import nts.uk.ctx.at.shared.dom.worktime.common.FlowWorkRestTimezone;
import nts.uk.ctx.at.shared.dom.worktime_old.AmPmClassification;

/**
 * The Class WeekdayWorkTime.
 */
// 固定勤務の平日出勤用勤務時間帯
@Getter
@AllArgsConstructor
public class FixWeekdayWorkTime {

	// 勤務時間帯
	private FixedWorkTimezoneSet workingTime;

	/** The rest time. */
	// 休憩時間帯
//	private FixRestTime restTime;
	private FlowWorkRestTimezone restTime;

	/** The am pm cls. */
	// 午前午後区分
	private AmPmClassification amPmCls;
}
