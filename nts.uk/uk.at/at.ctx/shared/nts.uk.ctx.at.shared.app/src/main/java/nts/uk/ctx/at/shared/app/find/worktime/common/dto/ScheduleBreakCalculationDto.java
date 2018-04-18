/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.common.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.worktime.flowset.ScheduleBreakCalculationSetMemento;

/**
 * The Class ScheduleBreakCalculationDto.
 */
@Getter
@Setter
public class ScheduleBreakCalculationDto implements ScheduleBreakCalculationSetMemento {

	/** The is refer rest time. */
	private Boolean isReferRestTime;

	/** The is calc from schedule. */
	private Boolean isCalcFromSchedule;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.
	 * ScheduleBreakCalculationSetMemento#setIsReferRestTime(boolean)
	 */
	@Override
	public void setIsReferRestTime(boolean val) {
		this.isReferRestTime = val;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.
	 * ScheduleBreakCalculationSetMemento#setIsCalcFromSchedule(boolean)
	 */
	@Override
	public void setIsCalcFromSchedule(boolean val) {
		this.isCalcFromSchedule = val;
	}

}
