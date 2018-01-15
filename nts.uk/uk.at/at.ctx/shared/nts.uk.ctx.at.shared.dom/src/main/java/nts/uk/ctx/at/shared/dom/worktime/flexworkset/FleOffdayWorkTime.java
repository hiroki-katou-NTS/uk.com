/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flexworkset;

import java.util.List;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.worktime.fixedworkset.BreakTimezoneSet;
import nts.uk.ctx.at.shared.dom.worktime.fluidworkset.FluRestTime;

/**
 * The Class OffdayWorkTime.
 */
@Getter
// フレックス勤務の休日出勤用勤務時間帯
public class FleOffdayWorkTime {
	// 勤務時間帯
	 private List<BreakTimezoneSet> workingTimes;

	/** The break time. */
	// 休憩時間帯
	private FluRestTime restTime;
}
