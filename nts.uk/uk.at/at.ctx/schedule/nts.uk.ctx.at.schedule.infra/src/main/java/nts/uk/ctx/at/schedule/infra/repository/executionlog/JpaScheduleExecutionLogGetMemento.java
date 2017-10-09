package nts.uk.ctx.at.schedule.infra.repository.executionlog;

import nts.uk.ctx.at.schedule.dom.executionlog.CompletionStatus;
import nts.uk.ctx.at.schedule.dom.executionlog.ExecutionDateTime;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleExecutionLogGetMemento;
import nts.uk.ctx.at.schedule.infra.entity.executionlog.KscmtScheduleExcLog;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.workrule.closure.Period;

public class JpaScheduleExecutionLogGetMemento implements ScheduleExecutionLogGetMemento {

	/** The entity. */
	private KscmtScheduleExcLog entity;

	public JpaScheduleExecutionLogGetMemento(KscmtScheduleExcLog item) {
		this.entity = item;
	}

	@Override
	public CompanyId getCompanyId() {
		return new CompanyId(this.entity.getKscmtScheduleExcLogPK().getCid());
	}

	@Override
	public CompletionStatus getCompletionStatus() {
		return CompletionStatus.valueOf(this.entity.getCompletionStatus());
	}

	@Override
	public String getExecutionId() {
		return this.entity.getKscmtScheduleExcLogPK().getExeId();
	}

	@Override
	public ExecutionDateTime getExecutionDateTime() {
		return new ExecutionDateTime(this.entity.getExeStrD(),this.entity.getExeEndD());
	}

	@Override
	public String getExecutionEmployeeId() {
		return this.entity.getExeSid();
	}

	@Override
	public Period getPeriod() {
		return new Period(this.entity.getStartYmd(),this.entity.getEndYmd());
	}

}
