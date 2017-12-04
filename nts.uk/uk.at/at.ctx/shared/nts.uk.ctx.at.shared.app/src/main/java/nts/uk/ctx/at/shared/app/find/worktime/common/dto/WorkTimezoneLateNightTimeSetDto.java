/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.common.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneLateNightTimeSetSetMemento;

/**
 * The Class WorkTimezoneLateNightTimeSetDto.
 */
@Getter
@Setter
public class WorkTimezoneLateNightTimeSetDto implements WorkTimezoneLateNightTimeSetSetMemento {

	/** The rounding setting. */
	private TimeRoundingSettingDto roundingSetting;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.
	 * WorkTimezoneLateNightTimeSetSetMemento#setRoundingSetting(nts.uk.ctx.at.
	 * shared.dom.common.timerounding.TimeRoundingSetting)
	 */
	@Override
	public void setRoundingSetting(TimeRoundingSetting set) {
		this.roundingSetting = new TimeRoundingSettingDto(set.getRoundingTime().value, set.getRounding().value);
	}
}
