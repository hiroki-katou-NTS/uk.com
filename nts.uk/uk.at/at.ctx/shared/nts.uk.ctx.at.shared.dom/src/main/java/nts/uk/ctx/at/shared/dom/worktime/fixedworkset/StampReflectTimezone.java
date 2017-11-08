/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.fixedworkset;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Class StampReflectTimezone.
 */
@Getter
public class StampReflectTimezone extends DomainObject {

	/** The work no. */
	// TODO 勤務NO
	private Integer workNo;

	// TODO 区分
	// private

	/** The end time. */
	// 終了時刻
	private TimeWithDayAttr endTime;

	/** The start time. */
	// 開始時刻
	private TimeWithDayAttr startTime;
}
