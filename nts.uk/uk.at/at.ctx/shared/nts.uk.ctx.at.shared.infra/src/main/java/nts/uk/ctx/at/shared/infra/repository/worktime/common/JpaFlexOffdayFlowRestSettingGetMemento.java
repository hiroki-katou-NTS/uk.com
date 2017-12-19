/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.common;

import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.worktime.common.FlowRestSettingGetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtFlexHaRestTime;

/**
 * The Class JpaFlexOffdayFlowRestSettingGetMemento.
 */
public class JpaFlexOffdayFlowRestSettingGetMemento implements FlowRestSettingGetMemento{
	
	/** The entity. */
	private KshmtFlexHaRestTime entity;

	/**
	 * Instantiates a new jpa flex offday flow rest setting get memento.
	 *
	 * @param entity the entity
	 */
	public JpaFlexOffdayFlowRestSettingGetMemento(KshmtFlexHaRestTime entity) {
		super();
		this.entity = entity;
	}



	/**
	 * Gets the flow rest time.
	 *
	 * @return the flow rest time
	 */
	@Override
	public AttendanceTime getFlowRestTime() {
		return new AttendanceTime(this.entity.getAfterRestTime());
	}


	/**
	 * Gets the flow passage time.
	 *
	 * @return the flow passage time
	 */
	@Override
	public AttendanceTime getFlowPassageTime() {
		return new AttendanceTime(this.entity.getAfterPassageTime());
	}

	
}
