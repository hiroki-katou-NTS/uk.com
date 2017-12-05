/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.worktime.common.dto;

import lombok.Value;
import nts.uk.ctx.at.shared.app.find.worktime.common.dto.TimeRoundingSettingDto;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.IntervalTimeGetMemento;

/**
 * The Class IntervalTimeDto.
 */
@Value
public class IntervalTimeDto implements IntervalTimeGetMemento {

	/** The interval time. */
	private Integer intervalTime;

	/** The rounding. */
	private TimeRoundingSettingDto rounding;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.IntervalTimeGetMemento#
	 * getIntervalTime()
	 */
	@Override
	public AttendanceTime getIntervalTime() {
		return new AttendanceTime(this.intervalTime);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.IntervalTimeGetMemento#
	 * getRounding()
	 */
	@Override
	public TimeRoundingSetting getRounding() {
		return new TimeRoundingSetting(this.rounding.getRoundingTime(), this.rounding.getRounding());
	}

}
