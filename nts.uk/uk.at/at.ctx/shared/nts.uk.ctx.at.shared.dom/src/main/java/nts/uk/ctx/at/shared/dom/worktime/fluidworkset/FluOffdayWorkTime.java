/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.fluidworkset;

import lombok.Getter;

/**
 * The Class OffdayWorkTime.
 */
@Getter
// 流動勤務の休日出勤用勤務時間帯
public class FluOffdayWorkTime {
	// 勤務時間帯
	// private List<流動休出時間帯> workingTimes;

	/** The break time. */
	// 休憩時間帯
	private FluRestTime restTime;
}
