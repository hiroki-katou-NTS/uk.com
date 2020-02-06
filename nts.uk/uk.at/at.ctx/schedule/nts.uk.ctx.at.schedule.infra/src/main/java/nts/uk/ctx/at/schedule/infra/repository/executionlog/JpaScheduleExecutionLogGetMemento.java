/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.executionlog;

import nts.uk.ctx.at.schedule.dom.executionlog.CompletionStatus;
import nts.uk.ctx.at.schedule.dom.executionlog.ExecutionAtr;
import nts.uk.ctx.at.schedule.dom.executionlog.ExecutionDateTime;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleExecutionLogGetMemento;
import nts.uk.ctx.at.schedule.infra.entity.executionlog.KscdtScheExeLog;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * The Class JpaScheduleExecutionLogGetMemento.
 */
public class JpaScheduleExecutionLogGetMemento implements ScheduleExecutionLogGetMemento {

	/** The entity. */
	private KscdtScheExeLog entity;

	/**
	 * Instantiates a new jpa schedule execution log get memento.
	 *
	 * @param item
	 *            the item
	 */
	public JpaScheduleExecutionLogGetMemento(KscdtScheExeLog item) {
		this.entity = item;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.schedule.dom.executionlog.ScheduleExecutionLogGetMemento#
	 * getCompanyId()
	 */
	@Override
	public CompanyId getCompanyId() {
		return new CompanyId(this.entity.getKscdtScheExeLogPK().getCid());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.schedule.dom.executionlog.ScheduleExecutionLogGetMemento#
	 * getCompletionStatus()
	 */
	@Override
	public CompletionStatus getCompletionStatus() {
		return CompletionStatus.valueOf(this.entity.getCompletionStatus());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.schedule.dom.executionlog.ScheduleExecutionLogGetMemento#
	 * getExecutionId()
	 */
	@Override
	public String getExecutionId() {
		return this.entity.getKscdtScheExeLogPK().getExeId();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.schedule.dom.executionlog.ScheduleExecutionLogGetMemento#
	 * getExecutionDateTime()
	 */
	@Override
	public ExecutionDateTime getExecutionDateTime() {
		return new ExecutionDateTime(this.entity.getExeStrD(), this.entity.getExeEndD());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.schedule.dom.executionlog.ScheduleExecutionLogGetMemento#
	 * getExecutionEmployeeId()
	 */
	@Override
	public String getExecutionEmployeeId() {
		return this.entity.getExeSid();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.schedule.dom.executionlog.ScheduleExecutionLogGetMemento#
	 * getPeriod()
	 */
	@Override
	public DatePeriod getPeriod() {
		return new DatePeriod(this.entity.getStartYmd(), this.entity.getEndYmd());
	}

	@Override
	public ExecutionAtr getExeAtr() {
		return ExecutionAtr.valueOf(this.entity.getExeAtr());
	}

}
