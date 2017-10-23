/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.executionlog;

import nts.uk.ctx.at.schedule.dom.executionlog.ExecutionStatus;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleCreatorGetMemento;
import nts.uk.ctx.at.schedule.infra.entity.executionlog.KscdtScheExeTarget;
import nts.uk.ctx.at.schedule.infra.entity.executionlog.KscdtScheExeTargetPK;

/**
 * The Class JpaScheduleCreatorGetMemento.
 */
public class JpaScheduleCreatorGetMemento implements ScheduleCreatorGetMemento{
	
	/** The entity. */
	private KscdtScheExeTarget entity;
	
	/**
	 * Instantiates a new jpa schedule creator get memento.
	 *
	 * @param entity the entity
	 */
	public JpaScheduleCreatorGetMemento(KscdtScheExeTarget entity) {
		if (entity.getKscdtScheExeTargetPK() == null) {
			entity.setKscdtScheExeTargetPK(new KscdtScheExeTargetPK());
		}
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.executionlog.ScheduleCreatorGetMemento#
	 * getExecutionId()
	 */
	@Override
	public String getExecutionId() {
		return this.entity.getKscdtScheExeTargetPK().getExeId();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.executionlog.ScheduleCreatorGetMemento#
	 * getExecutionStatus()
	 */
	@Override
	public ExecutionStatus getExecutionStatus() {
		return ExecutionStatus.valueOf(this.entity.getExeStatus());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.executionlog.ScheduleCreatorGetMemento#
	 * getEmployeeId()
	 */
	@Override
	public String getEmployeeId() {
		return this.entity.getKscdtScheExeTargetPK().getSid();
	}

}
