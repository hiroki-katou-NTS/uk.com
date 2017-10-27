/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.fluidworkset;

import java.util.List;

import lombok.Getter;

/**
 * The Class BreakTime.
 */
@Getter
// 流動休憩時間帯
public class FluRestTimeGroup {

	// 流動休憩設定
	private List<FluRestTimeSetting> fluidRestTimes;
}
