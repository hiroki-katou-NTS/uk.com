package nts.uk.ctx.at.schedule.dom.executionlog;

import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * The Interface ScheduleExecutionLogSetMemento.
 */
public interface ScheduleExecutionLogSetMemento {

	/**
	 * Sets the company id.
	 *
	 * @param companyId
	 *            the new company id
	 */
	public void setCompanyId(CompanyId companyId);

	/**
	 * Sets the completion status.
	 *
	 * @param completionStatus
	 *            the new completion status
	 */
	public void setCompletionStatus(CompletionStatus completionStatus);

	/**
	 * Sets the execution id.
	 *
	 * @param executionId
	 *            the new execution id
	 */
	public void setExecutionId(String executionId);

	/**
	 * Sets the execution date time.
	 *
	 * @param executionDateTime
	 *            the new execution date time
	 */
	public void setExecutionDateTime(ExecutionDateTime executionDateTime);

	/**
	 * Sets the execution employee id.
	 *
	 * @param executionEmployeeId
	 *            the new execution employee id
	 */
	public void setExecutionEmployeeId(String executionEmployeeId);

	/**
	 * Sets the period.
	 *
	 * @param period
	 *            the new period
	 */
	public void setPeriod(DatePeriod period);

	/**
	 * 
	 * @param exeAtr
	 */
	public void setExeAtr(ExecutionAtr exeAtr);
}
