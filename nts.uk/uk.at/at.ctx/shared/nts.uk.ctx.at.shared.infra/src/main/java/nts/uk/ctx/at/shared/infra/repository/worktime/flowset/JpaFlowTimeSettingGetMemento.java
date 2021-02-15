/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.flowset;

import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowTimeGetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.flowset.KshmtFworkHolidayTime;

/**
 * The Class JpaFlowTimeSettingGetMemento.
 */
public class JpaFlowTimeSettingGetMemento implements FlowTimeGetMemento {

	/** The entity. */
	KshmtFworkHolidayTime entity;
	
	/**
	 * Instantiates a new jpa flow time setting get memento.
	 *
	 * @param entity the entity
	 */
	public JpaFlowTimeSettingGetMemento(KshmtFworkHolidayTime entity) {
		super();
		this.entity = entity;
	}
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.FlTimeGetMemento#getRouding()
	 */
	@Override
	public TimeRoundingSetting getRouding() {
		return new TimeRoundingSetting(this.entity.getUnit(), this.entity.getRounding());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.FlTimeGetMemento#getElapsedTime()
	 */
	@Override
	public AttendanceTime getElapsedTime() {
		return new AttendanceTime(this.entity.getPassageTime());
	}

}
