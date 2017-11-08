/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktimeset.fluid;

import lombok.Getter;

/**
 * The Class WeekdayWorkTime.
 */
// 流動勤務の平日出勤用勤務時間帯
@Getter
public class FluWeekdayWorkTime {

	// 勤務時間帯
	// private 流動勤務時間帯設定 workingTime;

	/** The rest time. */
	// 休憩時間帯
	private FluRestTime restTime;

}
