/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.fluidworkset;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.worktime.fixedworkset.FixRestTimeSetting;

/**
 * The Class BreakTime.
 */
@Getter
// 流動勤務の休憩時間帯
public class FluRestTime {

	// 固定休憩時間帯
	private FixRestTimeSetting fixedRestTime;

	// 流動休憩時間帯
	private FluRestTimeGroup fluidRestTime;

	// 休憩時間帯を固定にする
	private Boolean useFixedRestTime;
}
