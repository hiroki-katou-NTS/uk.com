/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;

/**
 * The Class IntervalTime.
 */
//インターバル時間
@Getter
public class IntervalTime extends DomainObject {
	
	/** The interval time. */
	//インターバル時間
	private AttendanceTime intervalTime;
	
	/** The rounding. */
	//丸め
	private TimeRoundingSetting rounding;
}
