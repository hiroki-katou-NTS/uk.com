/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.flowset;

import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowRestSettingSetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.flowset.KshmtWtFloBrFlAllTs;

/**
 * The Class JpaFlowRestSettingSetMemento.
 */
public class JpaFlowRestSettingSetMemento implements FlowRestSettingSetMemento {

	/** The entity. */
	private KshmtWtFloBrFlAllTs entity;

	/**
	 * Instantiates a new jpa flow rest setting set memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaFlowRestSettingSetMemento(KshmtWtFloBrFlAllTs entity) {
		super();
		this.entity = entity;
	}

	/**
	 * Sets the flow rest time.
	 *
	 * @param time
	 *            the new flow rest time
	 */
	@Override
	public void setFlowRestTime(AttendanceTime time) {
		this.entity.setRestTime(time.valueAsMinutes());
	}

	/**
	 * Sets the flow passage time.
	 *
	 * @param time
	 *            the new flow passage time
	 */
	@Override
	public void setFlowPassageTime(AttendanceTime time) {
		this.entity.setPassageTime(time.valueAsMinutes());
	}

}
