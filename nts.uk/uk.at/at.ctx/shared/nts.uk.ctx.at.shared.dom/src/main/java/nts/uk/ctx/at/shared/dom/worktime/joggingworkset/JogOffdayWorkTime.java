/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.joggingworkset;

import lombok.Getter;

/**
 * The Class OffdayWorkTime.
 */
@Getter
// 時差勤務の休日出勤用勤務時間帯
public class JogOffdayWorkTime {
	// 勤務時間帯
	// private List<時差勤務休出時間の時間帯設定> workingTimes;

	/** The break time. */
	// 休憩時間帯
	private JogRestTime restTime;
}
