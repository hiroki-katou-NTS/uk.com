/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.executionlog;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleErrorLogSetMemento;
import nts.uk.ctx.at.schedule.infra.entity.executionlog.KscmtScheduleErrLog;
import nts.uk.ctx.at.schedule.infra.entity.executionlog.KscmtScheduleErrLogPK;

/**
 * The Class JpaScheduleErrorLogSetMemento.
 */
public class JpaScheduleErrorLogSetMemento implements ScheduleErrorLogSetMemento {

	/** The entity. */
	private KscmtScheduleErrLog entity;

	/**
	 * Instantiates a new jpa schedule error log set memento.
	 *
	 * @param entity the entity
	 */
	public JpaScheduleErrorLogSetMemento(KscmtScheduleErrLog entity) {
		if(entity.getKscmtScheduleErrLogPK() == null){
			entity.setKscmtScheduleErrLogPK(new KscmtScheduleErrLogPK());
		}
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.executionlog.ScheduleErrorLogSetMemento#
	 * setErrorContent(java.lang.String)
	 */
	@Override
	public void setErrorContent(String errorContent) {
		this.entity.setErrContent(errorContent);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.executionlog.ScheduleErrorLogSetMemento#
	 * setExecutionId(java.lang.String)
	 */
	@Override
	public void setExecutionId(String executionId) {
		this.entity.getKscmtScheduleErrLogPK().setExeId(executionId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.executionlog.ScheduleErrorLogSetMemento#
	 * setDate(nts.arc.time.GeneralDate)
	 */
	@Override
	public void setDate(GeneralDate date) {
		this.entity.getKscmtScheduleErrLogPK().setYmd(date);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.executionlog.ScheduleErrorLogSetMemento#
	 * setEmployeeId(java.lang.String)
	 */
	@Override
	public void setEmployeeId(String employeeId) {
		this.entity.getKscmtScheduleErrLogPK().setSid(employeeId);

	}

}
