/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.worktime.common.dto;

import lombok.Value;
import nts.uk.ctx.at.shared.app.find.worktime.common.dto.TimeRoundingSettingDto;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneLateNightTimeSetGetMemento;

/**
 * The Class WorkTimezoneLateNightTimeSetDto.
 */
@Value
public class WorkTimezoneLateNightTimeSetDto implements WorkTimezoneLateNightTimeSetGetMemento {

	/** The rounding setting. */
	private TimeRoundingSettingDto roundingSetting;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.
	 * WorkTimezoneLateNightTimeSetGetMemento#getRoundingSetting()
	 */
	@Override
	public TimeRoundingSetting getRoundingSetting() {
		return new TimeRoundingSetting(this.roundingSetting.getRoundingTime(), this.roundingSetting.getRounding());
	}
}
