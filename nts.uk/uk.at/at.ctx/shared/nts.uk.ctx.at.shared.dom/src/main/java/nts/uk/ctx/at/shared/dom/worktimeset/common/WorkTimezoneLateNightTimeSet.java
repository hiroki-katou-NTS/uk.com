/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktimeset.common;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;

/**
 * The Class WorkTimezoneLateNightTimeSet.
 */
// 就業時間帯の深夜時間設定
@Getter
public class WorkTimezoneLateNightTimeSet extends DomainObject {

	/** The rounding setting. */
	// 丸め設定
	private TimeRoundingSetting roundingSetting;
}
