/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.executionlog;

import nts.uk.ctx.at.schedule.dom.executionlog.ExecutionStatus;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleCreatorGetMemento;
import nts.uk.ctx.at.schedule.infra.entity.executionlog.KscmtScheduleCreator;
import nts.uk.ctx.at.schedule.infra.entity.executionlog.KscmtScheduleCreatorPK;

/**
 * The Class JpaScheduleCreatorGetMemento.
 */
public class JpaScheduleCreatorGetMemento implements ScheduleCreatorGetMemento{
	
	/** The entity. */
	private KscmtScheduleCreator entity;
	
	/**
	 * Instantiates a new jpa schedule creator get memento.
	 *
	 * @param entity the entity
	 */
	public JpaScheduleCreatorGetMemento(KscmtScheduleCreator entity) {
		if (entity.getKscmtScheduleCreatorPK() == null) {
			entity.setKscmtScheduleCreatorPK(new KscmtScheduleCreatorPK());
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
		return this.entity.getKscmtScheduleCreatorPK().getExeId();
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
		return this.entity.getKscmtScheduleCreatorPK().getSid();
	}

}
