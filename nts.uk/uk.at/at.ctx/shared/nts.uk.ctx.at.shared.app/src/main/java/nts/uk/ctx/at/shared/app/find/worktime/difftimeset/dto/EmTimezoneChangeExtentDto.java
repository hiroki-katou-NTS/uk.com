/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.difftimeset.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.app.find.worktime.common.dto.InstantRoundingDto;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.worktime.common.InstantRounding;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.EmTimezoneChangeExtentSetMemento;

/**
 * The Class EmTimezoneChangeExtent.
 */
@Getter
@Setter
public class EmTimezoneChangeExtentDto implements EmTimezoneChangeExtentSetMemento {

	/** The ahead change. */
	private Integer aheadChange;

	/** The unit. */
	private InstantRoundingDto unit;

	/** The behind change. */
	private Integer behindChange;

	@Override
	public void setAheadChange(AttendanceTime aheadChange) {
		this.aheadChange = aheadChange.v();
	}

	@Override
	public void setUnit(InstantRounding unit) {
		unit.saveToMememto(this.unit);
	}

	@Override
	public void setBehindChange(AttendanceTime behindChange) {
		this.behindChange = behindChange.v();
	}
}
