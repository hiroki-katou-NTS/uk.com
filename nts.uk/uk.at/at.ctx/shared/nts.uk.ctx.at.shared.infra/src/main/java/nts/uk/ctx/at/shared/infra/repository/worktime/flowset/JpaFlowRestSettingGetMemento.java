/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.flowset;

import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowRestSettingGetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.flowset.KshmtWtFloBrFlAllTs;

/**
 * The Class JpaFlowRestSettingGetMemento.
 */
public class JpaFlowRestSettingGetMemento implements FlowRestSettingGetMemento {

	/** The entity. */
	private KshmtWtFloBrFlAllTs entity;
	
	/**
	 * Instantiates a new jpa flow rest setting get memento.
	 *
	 * @param entity the entity
	 */
	public JpaFlowRestSettingGetMemento(KshmtWtFloBrFlAllTs entity) {
		super();
		this.entity = entity;
	}
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.FlowRestSettingGetMemento#getFlowRestTime()
	 */
	@Override
	public AttendanceTime getFlowRestTime() {
		return new AttendanceTime(this.entity.getRestTime());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.FlowRestSettingGetMemento#getFlowPassageTime()
	 */
	@Override
	public AttendanceTime getFlowPassageTime() {
		return new AttendanceTime(this.entity.getPassageTime());
	}

}
