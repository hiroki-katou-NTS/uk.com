/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktimeset.fixed;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Class TimeZoneRounding.
 */
//時間帯(丸め付き)
@Getter
public class TimeZoneRounding extends DomainObject {

	/** The rounding. */
	// 丸め
	private TimeRoundingSetting rounding;

	/** The start. */
	// 開始
	private TimeWithDayAttr start;
	
	/** The end. */
	// 終了
	private TimeWithDayAttr end;

}
