/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktimeset.flow;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;

/**
 * The Class FlowTimeSetting.
 */
//流動時間設定

/**
 * Gets the passage time.
 *
 * @return the passage time
 */
@Getter
public class FlowTimeSetting extends DomainObject {

	/** The rouding. */
	// 丸め
	private TimeRoundingSetting rouding;

	/** The passage time. */
	// 経過時間
	private AttendanceTime passageTime;
}
