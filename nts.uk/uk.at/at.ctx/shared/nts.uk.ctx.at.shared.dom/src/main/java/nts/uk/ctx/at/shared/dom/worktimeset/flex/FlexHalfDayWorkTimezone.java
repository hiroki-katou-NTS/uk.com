/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktimeset.flex;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.worktimeset.fluid.FluRestTime;

/**
 * The Class WeekdayWorkTime.
 */
// 固定勤務の平日出勤用勤務時間帯
@Getter
public class FlexHalfDayWorkTimezone {

	// 勤務時間帯
	// private 固定勤務時間帯設定 workingTime;

	/** The rest time. */
	// 休憩時間帯
	private FluRestTime restTime;

	/** The am pm cls. */
	// TODO: ko thấy define???
	// 午前午後区分
	// private AmPmClassification amPmCls;
}
