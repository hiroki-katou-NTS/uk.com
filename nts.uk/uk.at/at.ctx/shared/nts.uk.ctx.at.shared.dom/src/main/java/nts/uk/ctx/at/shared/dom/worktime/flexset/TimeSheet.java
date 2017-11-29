/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flexset;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Class TimeSheet.
 */
// 時間帯
@Getter
public class TimeSheet extends DomainObject {

	/** The start time. */
	// 開始時刻
	private TimeWithDayAttr startTime;

	/** The end time. */
	// 終了時刻
	private TimeWithDayAttr endTime;
}
