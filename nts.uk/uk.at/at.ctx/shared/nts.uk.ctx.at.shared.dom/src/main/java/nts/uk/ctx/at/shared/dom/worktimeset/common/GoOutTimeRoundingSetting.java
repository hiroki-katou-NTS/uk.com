/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktimeset.common;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;

/**
 * The Class GoOutTimeRoundingSetting.
 */
//外出時間の丸め設定

/**
 * Gets the rounding setting.
 *
 * @return the rounding setting
 */
@Getter
public class GoOutTimeRoundingSetting {

	/** The rounding method. */
	//丸め方法
	private GoOutTimeRoundingMethod roundingMethod;
	
	/** The rounding setting. */
	//丸め設定
	private TimeRoundingSetting roundingSetting;
}
