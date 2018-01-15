/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.fixedworkset;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * The Class BreakTime.
 */
@Getter
@AllArgsConstructor
// 固定勤務の休憩時間帯
public class FixRestTime {

	/** The setting. */
	// 休憩時間帯
	private FixRestTimeSetting setting;
}
