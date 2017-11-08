/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime_old;

import lombok.Getter;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Class DeductionTime.
 */
@Getter
// 控除時間帯(丸め付き)
public class DeductionTime {

	/** The start. */
	// 開始
	private TimeWithDayAttr start;

	/** The end. */
	// 終了
	private TimeWithDayAttr end;
}
