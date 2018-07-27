/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.common;

import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowRestSettingSetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtFlexOdRestSet;

/**
 * The Class JpaFlexOffdayFlowRestSetMemento.
 */
public class JpaFlexOffdayFlowRestSetMemento implements FlowRestSettingSetMemento{

	/** The entity. */
	private KshmtFlexOdRestSet entity;
	
	/**
	 * Instantiates a new jpa flex offday flow rest set memento.
	 *
	 * @param entity the entity
	 */
	public JpaFlexOffdayFlowRestSetMemento(KshmtFlexOdRestSet entity) {
		super();
		this.entity = entity;
	}
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.FlowRestSettingSetMemento#
	 * setFlowRestTime(nts.uk.ctx.at.shared.dom.common.time.AttendanceTime)
	 */
	@Override
	public void setFlowRestTime(AttendanceTime time) {
		this.entity.setFlowRestTime(time.valueAsMinutes());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.FlowRestSettingSetMemento#
	 * setFlowPassageTime(nts.uk.ctx.at.shared.dom.common.time.AttendanceTime)
	 */
	@Override
	public void setFlowPassageTime(AttendanceTime time) {
		this.entity.setFlowPassageTime(time.valueAsMinutes());
	}

}
