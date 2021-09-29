/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.worktime.predset.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.common.time.BreakDownTimeDay;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetermineTimeGetMemento;

/**
 * The Class PredetermineTimeDto.
 */
@Getter
@Setter
public class PredetermineTimeDto implements PredetermineTimeGetMemento{
	
	/** The add time. */
	public BreakDownTimeDayDto addTime;
	
	/** The pred time. */
	public BreakDownTimeDayDto predTime;

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.predset.PredetermineTimeGetMemento#getAddTime()
	 */
	@Override
	public BreakDownTimeDay getAddTime() {
		return new BreakDownTimeDay(addTime.getOneDay(), addTime.getMorning(), addTime.getAfternoon());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.predset.PredetermineTimeGetMemento#getPredTime()
	 */
	@Override
	public BreakDownTimeDay getPredTime() {
		return new BreakDownTimeDay(predTime.getOneDay(), predTime.getMorning(), predTime.getAfternoon());
	}

}
