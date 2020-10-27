/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.predset;

import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.worktime.predset.BreakDownTimeDay;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetermineTimeGetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.predset.KshmtWtComPredTime;

/**
 * The Class JpaPredetermineTimeGetMemento.
 */
public class JpaPredetermineTimeGetMemento implements PredetermineTimeGetMemento{
	
	/** The entity. */
	private  KshmtWtComPredTime entity;
	
	/**
	 * Instantiates a new jpa predetermine time get memento.
	 *
	 * @param entity the entity
	 */
	public JpaPredetermineTimeGetMemento(KshmtWtComPredTime entity) {
		super();
		this.entity = entity;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.predset.PredetermineTimeGetMemento#getAddTime()
	 */
	@Override
	public BreakDownTimeDay getAddTime() {
		return BreakDownTimeDay.builder().oneDay(new AttendanceTime(this.entity.getWorkAddOneDay()))
				.morning(new AttendanceTime(this.entity.getWorkAddMorning()))
				.afternoon(new AttendanceTime(this.entity.getWorkAddAfternoon())).build();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.predset.PredetermineTimeGetMemento#getPredTime()
	 */
	@Override
	public BreakDownTimeDay getPredTime() {
		return BreakDownTimeDay.builder().oneDay(new AttendanceTime(this.entity.getPredOneDay()))
				.morning(new AttendanceTime(this.entity.getPredMorning()))
				.afternoon(new AttendanceTime(this.entity.getPredAfternoon())).build();
	}

}
