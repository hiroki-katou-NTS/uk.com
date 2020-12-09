/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.flowset;

import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowTimeGetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.flowset.KshmtWtFloHolTs;

/**
 * The Class JpaFlowTimeSettingGetMemento.
 */
public class JpaFlowTimeSettingGetMemento implements FlowTimeGetMemento {

	/** The entity. */
	KshmtWtFloHolTs entity;
	
	/**
	 * Instantiates a new jpa flow time setting get memento.
	 *
	 * @param entity the entity
	 */
	public JpaFlowTimeSettingGetMemento(KshmtWtFloHolTs entity) {
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
