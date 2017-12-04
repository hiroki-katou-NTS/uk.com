/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.difftimeset.dto;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.worktime.common.InstantRounding;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.EmTimezoneChangeExtentSetMemento;

/**
 * The Class EmTimezoneChangeExtent.
 */
@Getter
public class EmTimezoneChangeExtentDto implements EmTimezoneChangeExtentSetMemento {

	/** The ahead change. */
	private AttendanceTime aheadChange;

	/** The unit. */
	private InstantRounding unit;

	/** The behind change. */
	private AttendanceTime behindChange;

	@Override
	public void setAheadChange(AttendanceTime aheadChange) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setUnit(InstantRounding unit) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setBehindChange(AttendanceTime behindChange) {
		// TODO Auto-generated method stub
		
	}

}
