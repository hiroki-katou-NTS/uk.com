/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.executionlog;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleErrorLogSetMemento;
import nts.uk.ctx.at.schedule.infra.entity.executionlog.KscmtSchErrorLog;
import nts.uk.ctx.at.schedule.infra.entity.executionlog.KscmtSchErrorLogPK;

/**
 * The Class JpaScheduleErrorLogSetMemento.
 */
public class JpaScheduleErrorLogSetMemento implements ScheduleErrorLogSetMemento {

	/** The entity. */
	private KscmtSchErrorLog entity;

	/**
	 * Instantiates a new jpa schedule error log set memento.
	 *
	 * @param entity the entity
	 */
	public JpaScheduleErrorLogSetMemento(KscmtSchErrorLog entity) {
		if(entity.getKscmtSchErrorLogPK() == null){
			entity.setKscmtSchErrorLogPK(new KscmtSchErrorLogPK());
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
		this.entity.getKscmtSchErrorLogPK().setExeId(executionId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.executionlog.ScheduleErrorLogSetMemento#
	 * setDate(nts.arc.time.GeneralDate)
	 */
	@Override
	public void setDate(GeneralDate date) {
		this.entity.getKscmtSchErrorLogPK().setYmd(date);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.executionlog.ScheduleErrorLogSetMemento#
	 * setEmployeeId(java.lang.String)
	 */
	@Override
	public void setEmployeeId(String employeeId) {
		this.entity.getKscmtSchErrorLogPK().setSid(employeeId);

	}

}
