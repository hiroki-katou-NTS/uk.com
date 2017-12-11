/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.difftimeset.dto;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeDeductTimezoneSetMemento;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Class DiffTimeDeductTimezone.
 */
// 時差勤務の控除時間帯
@Getter
public class DiffTimeDeductTimezoneDto implements DiffTimeDeductTimezoneSetMemento {

	/** The is update start time. */
	private boolean isUpdateStartTime;

	/** The start. */
	private Integer start;

	/** The end. */
	private Integer end;

	@Override
	public void setStart(TimeWithDayAttr start) {
		this.start = start.v();
	}

	@Override
	public void setEnd(TimeWithDayAttr end) {
		this.end = end.v();
	}

	@Override
	public void setIsUpdateStartTime(boolean isUpdateStartTime) {
		this.isUpdateStartTime = isUpdateStartTime;
	}
}
