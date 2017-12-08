/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.predset.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.worktime.predset.BreakDownTimeDay;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetermineTimeSetMemento;

/**
 * The Class PredetermineTimeDto.
 */

@Getter
@Setter
public class PredetermineTimeDto implements PredetermineTimeSetMemento{
	
	/** The add time. */
	public BreakDownTimeDayDto addTime;
	
	/** The pred time. */
	public BreakDownTimeDayDto predTime;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.predset.PredetermineTimeSetMemento#
	 * setAddTime(nts.uk.ctx.at.shared.dom.worktime.predset.BreakDownTimeDay)
	 */
	@Override
	public void setAddTime(BreakDownTimeDay addTime) {
		this.addTime.setOneDay(addTime.getOneDay().valueAsMinutes());
		this.addTime.setMorning(addTime.getMorning().valueAsMinutes());
		this.addTime.setAfternoon(addTime.getAfternoon().valueAsMinutes());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.predset.PredetermineTimeSetMemento#
	 * setPredTime(nts.uk.ctx.at.shared.dom.worktime.predset.BreakDownTimeDay)
	 */
	@Override
	public void setPredTime(BreakDownTimeDay predTime) {
		this.predTime.setOneDay(predTime.getOneDay().valueAsMinutes());
		this.predTime.setMorning(predTime.getMorning().valueAsMinutes());
		this.predTime.setAfternoon(predTime.getAfternoon().valueAsMinutes());
	}

}
