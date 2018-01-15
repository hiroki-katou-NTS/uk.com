/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.executionlog;

import nts.uk.ctx.at.schedule.dom.executionlog.ExecutionStatus;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleCreatorSetMemento;
import nts.uk.ctx.at.schedule.infra.entity.executionlog.KscdtScheExeTarget;
import nts.uk.ctx.at.schedule.infra.entity.executionlog.KscdtScheExeTargetPK;

/**
 * The Class JpaScheduleCreatorSetMemento.
 */
public class JpaScheduleCreatorSetMemento implements ScheduleCreatorSetMemento {
	
	/** The entity. */
	private KscdtScheExeTarget entity;
	
	/**
	 * Instantiates a new jpa schedule creator set memento.
	 *
	 * @param entity the entity
	 */
	public JpaScheduleCreatorSetMemento(KscdtScheExeTarget entity) {
		if (entity.getKscdtScheExeTargetPK() == null) {
			entity.setKscdtScheExeTargetPK(new KscdtScheExeTargetPK());
		}
		this.entity = entity;
	}

	/**
	 * Sets the execution id.
	 *
	 * @param executionId the new execution id
	 */
	@Override
	public void setExecutionId(String executionId) {
		this.entity.getKscdtScheExeTargetPK().setExeId(executionId);
	}

	/**
	 * Sets the execution status.
	 *
	 * @param executionStatus the new execution status
	 */
	@Override
	public void setExecutionStatus(ExecutionStatus executionStatus) {
		this.entity.setExeStatus(executionStatus.value);
	}

	/**
	 * Sets the employee id.
	 *
	 * @param employeeId the new employee id
	 */
	@Override
	public void setEmployeeId(String employeeId) {
		this.entity.getKscdtScheExeTargetPK().setSid(employeeId);
	}
	

}
