/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.fixedworkset;

import java.util.List;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.worktime.common.FlowWorkRestTimezone;

/**
 * The Class OffdayWorkTime.
 */
@Getter
// 固定勤務の休日出勤用勤務時間帯
public class FixOffdayWorkTime {
	// 勤務時間帯
	private List<FixRestSetting> workingTimes;

	/** The break time. */
	// 休憩時間帯
//	private FixRestTime restTime;
	private FlowWorkRestTimezone restTime;
}
