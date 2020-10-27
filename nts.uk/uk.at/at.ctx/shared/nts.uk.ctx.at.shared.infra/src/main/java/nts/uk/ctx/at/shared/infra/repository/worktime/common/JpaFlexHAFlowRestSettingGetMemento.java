/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.common;

import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowRestSettingGetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtWtFleBrFlWek;

/**
 * The Class JpaFlexOffdayFlowRestSettingGetMemento.
 */
public class JpaFlexHAFlowRestSettingGetMemento implements FlowRestSettingGetMemento{
	
	/** The entity. */
	private KshmtWtFleBrFlWek entity;

	/**
	 * Instantiates a new jpa flex HA flow rest setting get memento.
	 *
	 * @param entity the entity
	 */
	public JpaFlexHAFlowRestSettingGetMemento(KshmtWtFleBrFlWek entity) {
		super();
		this.entity = entity;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.FlowRestSettingGetMemento#getFlowRestTime()
	 */
	@Override
	public AttendanceTime getFlowRestTime() {
		return new AttendanceTime(this.entity.getAfterRestTime());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.FlowRestSettingGetMemento#getFlowPassageTime()
	 */
	@Override
	public AttendanceTime getFlowPassageTime() {
		return new AttendanceTime(this.entity.getAfterPassageTime());
	}

	
}
