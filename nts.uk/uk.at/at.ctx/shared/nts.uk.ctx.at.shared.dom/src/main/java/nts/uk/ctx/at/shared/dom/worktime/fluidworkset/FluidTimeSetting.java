/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.fluidworkset;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;

/**
 * The Class FluidTimeSetting.
 */
@Getter
// 流動時間設定
public class FluidTimeSetting {

	/** The elapsed time. */
	// 経過時間
	private AttendanceTime elapsedTime;

	/** The rounding. */
	// 丸め
	private TimeRoundingSetting rounding;
}
