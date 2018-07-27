/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.executionlog;

import nts.uk.ctx.at.schedule.dom.executionlog.CompletionStatus;
import nts.uk.ctx.at.schedule.dom.executionlog.ExecutionAtr;
import nts.uk.ctx.at.schedule.dom.executionlog.ExecutionDateTime;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleExecutionLogSetMemento;
import nts.uk.ctx.at.schedule.infra.entity.executionlog.KscdtScheExeLog;
import nts.uk.ctx.at.schedule.infra.entity.executionlog.KscdtScheExeLogPK;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * The Class JpaScheduleExecutionLogGetMemento.
 */
public class JpaScheduleExecutionLogSetMemento implements ScheduleExecutionLogSetMemento {

	/** The entity. */
	private KscdtScheExeLog entity;

	/**
	 * Instantiates a new jpa schedule execution log get memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaScheduleExecutionLogSetMemento(KscdtScheExeLog entity) {
		if (entity.getKscdtScheExeLogPK() == null) {
			entity.setKscdtScheExeLogPK(new KscdtScheExeLogPK());
		}
		this.entity = entity;
	}

	/**
	 * Sets the company id.
	 *
	 * @param companyId
	 *            the new company id
	 */
	@Override
	public void setCompanyId(CompanyId companyId) {
		this.entity.getKscdtScheExeLogPK().setCid(companyId.v());
	}

	/**
	 * Sets the completion status.
	 *
	 * @param completionStatus
	 *            the new completion status
	 */
	@Override
	public void setCompletionStatus(CompletionStatus completionStatus) {
		this.entity.setCompletionStatus(completionStatus.value);
	}

	/**
	 * Sets the execution id.
	 *
	 * @param executionId
	 *            the new execution id
	 */
	@Override
	public void setExecutionId(String executionId) {
		this.entity.getKscdtScheExeLogPK().setExeId(executionId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.schedule.dom.executionlog.ScheduleExecutionLogSetMemento#
	 * setExecutionDateTime(nts.uk.ctx.at.schedule.dom.executionlog.
	 * ExecutionDateTime)
	 */
	@Override
	public void setExecutionDateTime(ExecutionDateTime executionDateTime) {
		this.entity.setExeStrD(executionDateTime.getExecutionStartDate());
		this.entity.setExeEndD(executionDateTime.getExecutionEndDate());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.schedule.dom.executionlog.ScheduleExecutionLogSetMemento#
	 * setExecutionEmployeeId(java.lang.String)
	 */
	@Override
	public void setExecutionEmployeeId(String executionEmployeeId) {
		this.entity.setExeSid(executionEmployeeId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.schedule.dom.executionlog.ScheduleExecutionLogSetMemento#
	 * setPeriod(nts.uk.ctx.at.shared.dom.workrule.closure.Period)
	 */
	@Override
	public void setPeriod(DatePeriod period) {
		this.entity.setStartYmd(period.start());
		this.entity.setEndYmd(period.end());
	}

	@Override
	public void setExeAtr(ExecutionAtr exeAtr) {
		this.entity.setExeAtr(exeAtr.value);
	}

}
