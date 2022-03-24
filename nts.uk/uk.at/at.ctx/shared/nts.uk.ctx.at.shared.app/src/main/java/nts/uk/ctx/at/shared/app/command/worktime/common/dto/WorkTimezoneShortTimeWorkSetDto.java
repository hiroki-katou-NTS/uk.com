/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.worktime.common.dto;

import lombok.Value;
import nts.uk.ctx.at.shared.app.find.worktime.common.dto.TimeRoundingSettingDto;
import nts.uk.ctx.at.shared.dom.common.timerounding.Rounding;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.common.timerounding.Unit;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneShortTimeWorkSetGetMemento;

/**
 * The Class WorkTimezoneShortTimeWorkSetDto.
 */
@Value
public class WorkTimezoneShortTimeWorkSetDto implements WorkTimezoneShortTimeWorkSetGetMemento {

	/** The nurs timezone work use. */
	private boolean nursTimezoneWorkUse;

	/** The child care work use. */
	private boolean childCareWorkUse;

	/** The rounding setting. */
	private TimeRoundingSettingDto roundingSet;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.
	 * WorkTimezoneShortTimeWorkSetGetMemento#getNursTimezoneWorkUse()
	 */
	@Override
	public boolean getNursTimezoneWorkUse() {
		return this.nursTimezoneWorkUse;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.
	 * WorkTimezoneShortTimeWorkSetGetMemento#getChildCareWorkUse()
	 */
	@Override
	public boolean getChildCareWorkUse() {
		return this.childCareWorkUse;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.
	 * WorkTimezoneShortTimeWorkSetGetMemento#getRoundingSet()
	 */
	@Override
	public TimeRoundingSetting getRoudingSet() {
		if (this.roundingSet == null) {
			return new TimeRoundingSetting(Unit.ROUNDING_TIME_1MIN, Rounding.ROUNDING_DOWN);
		} 
		return new TimeRoundingSetting(this.roundingSet.getRoundingTime(), this.roundingSet.getRounding());
	}
}
