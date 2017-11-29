/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flexset;

import java.util.List;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.worktime.common.FlowWorkRestTimezone;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.HolidayWorkTimeSheetSet;

/**
 * The Class FlexOffdayWorkTime.
 */
@Getter
// フレックス勤務の休日出勤用勤務時間帯
public class FlexOffdayWorkTime {

	/** The work timezone. */
	// 勤務時間帯
	private List<HolidayWorkTimeSheetSet> workTimezone;

	/** The rest timezone. */
	// 休憩時間帯
	private FlowWorkRestTimezone restTimezone;
}
