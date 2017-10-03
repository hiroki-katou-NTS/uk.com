package nts.uk.ctx.at.schedule.app.find.executionlog.dto;

import nts.uk.ctx.at.schedule.dom.executionlog.CompletionStatus;
import nts.uk.ctx.at.schedule.dom.executionlog.ExecutionContent;
import nts.uk.ctx.at.schedule.dom.executionlog.ExecutionDateTime;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleExecutionLogSetMemento;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.workrule.closure.Period;

/**
 * The Class ScheduleExecutionLogDto.
 */
public class ScheduleExecutionLogDto implements ScheduleExecutionLogSetMemento {

	/** The company id. */
	public String companyId;

	/** The completion status. */
	public Integer completionStatus;

	/** The execution id. */
	public String executionId;

	/** The execution content. */
	public ExecutionContentDto executionContent;

	/** The execution date time. */
	public ExecutionDateTime executionDateTime;

	/** The execution employee id. */
	public String executionEmployeeId;

	/** The period. */
	public Period period;

	@Override
	public void setCompanyId(CompanyId companyId) {
		this.companyId = companyId.v();
	}

	@Override
	public void setCompletionStatus(CompletionStatus completionStatus) {
		this.completionStatus = completionStatus.value;
	}

	@Override
	public void setExecutionId(String executionId) {
		this.executionId = executionId;
	}

	@Override
	public void setExecutionContent(ExecutionContent executionContent) {
		// TODO Auto-generated method stub
	}

	@Override
	public void setExecutionDateTime(ExecutionDateTime executionDateTime) {
		this.executionDateTime = executionDateTime;
	}

	@Override
	public void setExecutionEmployeeId(String executionEmployeeId) {
		this.executionEmployeeId = executionEmployeeId;
	}

	@Override
	public void setPeriod(Period period) {
		this.period = period;
	}
}
