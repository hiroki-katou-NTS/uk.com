/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.difftimeset;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.OverTimeOfTimeZoneSet;

/**
 * The Class DiffTimeOTTimezoneSet.
 */
@Getter
public class DiffTimeOTTimezoneSet extends OverTimeOfTimeZoneSet {

	/** The is update start time. */
	// 開始時刻に合わせて時刻を変動させる
	private boolean isUpdateStartTime;
}
