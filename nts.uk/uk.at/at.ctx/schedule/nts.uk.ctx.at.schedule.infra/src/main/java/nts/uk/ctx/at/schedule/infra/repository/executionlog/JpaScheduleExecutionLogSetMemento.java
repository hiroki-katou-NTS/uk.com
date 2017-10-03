/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.executionlog;

import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.schedule.dom.executionlog.CompletionStatus;
import nts.uk.ctx.at.schedule.dom.executionlog.ExecutionContent;
import nts.uk.ctx.at.schedule.dom.executionlog.ExecutionDateTime;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleExecutionLogSetMemento;
import nts.uk.ctx.at.schedule.infra.entity.executionlog.KscmtScheduleExcLog;
import nts.uk.ctx.at.schedule.infra.entity.executionlog.KscmtScheduleExcLogPK;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.workrule.closure.Period;

/**
 * The Class JpaScheduleExecutionLogGetMemento.
 */
public class JpaScheduleExecutionLogSetMemento implements ScheduleExecutionLogSetMemento{
	
	/** The entity. */
	private KscmtScheduleExcLog entity; 
	
	/**
	 * Instantiates a new jpa schedule execution log get memento.
	 *
	 * @param entity the entity
	 */
	public JpaScheduleExecutionLogSetMemento(KscmtScheduleExcLog entity) {
		if(entity.getKscmtScheduleExcLogPK() == null){
			entity.setKscmtScheduleExcLogPK(new KscmtScheduleExcLogPK());
		}
		this.entity = entity;
	}

	/**
	 * Sets the company id.
	 *
	 * @param companyId the new company id
	 */
	@Override
	public void setCompanyId(CompanyId companyId) {
		this.entity.getKscmtScheduleExcLogPK().setCid(companyId.v());
	}

	/**
	 * Sets the completion status.
	 *
	 * @param completionStatus the new completion status
	 */
	@Override
	public void setCompletionStatus(CompletionStatus completionStatus) {
		this.entity.setCompletionStatus(completionStatus.value);
	}

	/**
	 * Sets the execution id.
	 *
	 * @param executionId the new execution id
	 */
	@Override
	public void setExecutionId(String executionId) {
		this.entity.getKscmtScheduleExcLogPK().setExeId(executionId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.schedule.dom.executionlog.ScheduleExecutionLogSetMemento#
	 * setExecutionContent(nts.uk.ctx.at.schedule.dom.executionlog.
	 * ExecutionContent)
	 */
	@Override
	public void setExecutionContent(ExecutionContent executionContent) {
		executionContent.saveToMemento(new JpaExecutionContentSetMemento(this.entity));
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
	public void setPeriod(Period period) {
		this.entity.setStartYmd(GeneralDateTime.legacyDateTime(period.getStartDate().date()));
		this.entity.setEndYmd(GeneralDateTime.legacyDateTime(period.getEndDate().date()));
	}

}
