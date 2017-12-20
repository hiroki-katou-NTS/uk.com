/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.fluidworkset;

import java.util.List;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.worktime.common.FlowWorkRestTimezone;
import nts.uk.ctx.at.shared.dom.worktime.fluidworkset.fluidworktimesheet.FluWorkHolidayTimeSheet;

/**
 * The Class OffdayWorkTime.
 */
@Getter
// 流動勤務の休日出勤用勤務時間帯
public class FluOffdayWorkTime {
	// 勤務時間帯
	private List<FluWorkHolidayTimeSheet> workingTimes;
	/** The break time. */
	// 休憩時間帯
//	private FluRestTime restTime;
	private FlowWorkRestTimezone restTime;
}
