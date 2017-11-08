/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.joggingworkset;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.worktime.fixedworkset.commonset.TimeRounding;

/**
 * The Class EmTimezoneChangeExtent.
 */
@Getter
public class EmTimezoneChangeExtent extends DomainObject {

	/** The ahead change. */
	// 前に変動
	AttendanceTime aheadChange;

	/** The unit. */
	// 単位
	TimeRounding unit;

	/** The behind change. */
	// 後に変動
	AttendanceTime behindChange;
}
