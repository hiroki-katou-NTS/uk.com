/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.worktime.flowset.dto;

import lombok.Value;
import nts.uk.ctx.at.shared.app.find.worktime.common.dto.TimeRoundingSettingDto;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlTimeGetMemento;

/**
 * The Class FlTimeSettingDto.
 */
@Value
public class FlTimeSettingDto implements FlTimeGetMemento {

	/** The rounding. */
	private TimeRoundingSettingDto rounding;

	/** The elapsed time. */
	private Integer elapsedTime;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.flowset.FlTimeGetMemento#getRouding()
	 */
	@Override
	public TimeRoundingSetting getRouding() {
		return new TimeRoundingSetting(this.rounding.getRoundingTime(), this.rounding.getRounding());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.flowset.FlTimeGetMemento#getPassageTime
	 * ()
	 */
	@Override
	public AttendanceTime getElapsedTime() {
		return new AttendanceTime(this.elapsedTime);
	}
}
