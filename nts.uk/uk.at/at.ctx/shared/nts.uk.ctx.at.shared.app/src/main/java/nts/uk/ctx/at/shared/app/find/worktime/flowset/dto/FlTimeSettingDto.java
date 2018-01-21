/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.flowset.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.app.find.worktime.common.dto.TimeRoundingSettingDto;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowTimeSetMemento;

/**
 * The Class FlTimeSettingDto.
 */
@Getter
@Setter
public class FlTimeSettingDto implements FlowTimeSetMemento {

	/** The rounding. */
	private TimeRoundingSettingDto rounding;

	/** The elapsed time. */
	private Integer elapsedTime;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.flowset.FlTimeSetMemento#setRouding(nts
	 * .uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting)
	 */
	@Override
	public void setRouding(TimeRoundingSetting trSet) {
		this.rounding = new TimeRoundingSettingDto(trSet.getRoundingTime().value, trSet.getRounding().value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.flowset.FlTimeSetMemento#setElapsedTime
	 * (nts.uk.ctx.at.shared.dom.common.time.AttendanceTime)
	 */
	@Override
	public void setElapsedTime(AttendanceTime at) {
		this.elapsedTime = at.v();
	}
}
