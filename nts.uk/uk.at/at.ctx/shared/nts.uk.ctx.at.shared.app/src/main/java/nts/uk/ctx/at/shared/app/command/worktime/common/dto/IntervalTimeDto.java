/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.worktime.common.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.app.find.worktime.common.dto.TimeRoundingSettingDto;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.IntervalTimeGetMemento;

/**
 * The Class IntervalTimeDto.
 */
@Getter
@Setter
public class IntervalTimeDto implements IntervalTimeGetMemento {

	/** The interval time. */
	private int intervalTime;

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
