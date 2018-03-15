/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.worktime.common.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.worktime.flowset.ScheduleBreakCalculationGetMemento;

/**
 * The Class ScheduleBreakCalculationDto.
 */
@Getter
@Setter
public class ScheduleBreakCalculationDto implements ScheduleBreakCalculationGetMemento {

	/** The is refer rest time. */
	private Boolean isReferRestTime;

	/** The is calc from schedule. */
	private Boolean isCalcFromSchedule;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.
	 * ScheduleBreakCalculationGetMemento#getIsReferRestTime()
	 */
	@Override
	public boolean getIsReferRestTime() {
		return this.isReferRestTime;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.
	 * ScheduleBreakCalculationGetMemento#getIsCalcFromSchedule()
	 */
	@Override
	public boolean getIsCalcFromSchedule() {
		return this.isCalcFromSchedule;
	}

}
