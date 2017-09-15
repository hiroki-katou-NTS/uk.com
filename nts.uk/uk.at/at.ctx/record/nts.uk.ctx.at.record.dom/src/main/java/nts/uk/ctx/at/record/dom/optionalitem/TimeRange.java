/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.optionalitem;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class TimeRange.
 */
// 時間範囲
// 事前条件 : 上限値≧下限値
@Getter
public class TimeRange extends DomainObject{

	/** The upper limit. */
	// 上限値
	private TimeRangeValue upperLimit;

	/** The lower limit. */
	// 下限値
	private TimeRangeValue lowerLimit;
}
