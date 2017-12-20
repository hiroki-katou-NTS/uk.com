/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.fixedworkset;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.shared.dom.worktime.common.FlowWorkRestTimezone;

/**
 * The Class OffdayWorkTime.
 * 固定勤務の休日出勤用勤務時間帯
 */
@Getter
@AllArgsConstructor
// 固定勤務の休日出勤用勤務時間帯
public class FixOffdayWorkTime {
	/** The rest timezone. */
	// 休憩時間帯
	private FixRestTimezoneSet restTimezone;

	/** The work timezone. */
	// 勤務時間帯
	private List<HolidayWorkTimeSheetSet> workTimezone;
}
