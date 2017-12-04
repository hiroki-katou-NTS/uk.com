/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.fixedset;

import lombok.Getter;
import nts.arc.error.BusinessException;
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

	/* (non-Javadoc)
	 * @see nts.arc.layer.dom.DomainObject#validate()
	 */
	@Override
	public void validate() {
		super.validate();
		
		if (this.start.greaterThanOrEqualTo(this.end)) {
			throw new BusinessException("Msg_770");
		}
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return start + "," + end;
	}
	
}
