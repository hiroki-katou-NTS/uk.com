/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.common.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.IntervalTimeSetMemento;

/**
 * The Class IntervalTimeDto.
 */
@Getter
@Setter
public class IntervalTimeDto implements IntervalTimeSetMemento{

	/** The interval time. */
	private Integer intervalTime;
	
	/** The rounding. */
	private TimeRoundingSettingDto rounding;

	/**
	 * Sets the interval time.
	 *
	 * @param intervalTime the new interval time
	 */
	@Override
	public void setIntervalTime(AttendanceTime intervalTime) {
		this.intervalTime = intervalTime.valueAsMinutes();
	}

	/**
	 * Sets the rounding.
	 *
	 * @param rounding the new rounding
	 */
	@Override
	public void setRounding(TimeRoundingSetting rounding) {
		this.rounding = new TimeRoundingSettingDto(rounding.getRoundingTime().value, rounding.getRounding().value);
	}


	
	
}
