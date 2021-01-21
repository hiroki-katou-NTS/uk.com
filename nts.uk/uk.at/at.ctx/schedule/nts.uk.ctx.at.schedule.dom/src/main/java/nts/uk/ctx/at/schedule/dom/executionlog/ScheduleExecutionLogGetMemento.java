package nts.uk.ctx.at.schedule.dom.executionlog;

import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.arc.time.calendar.period.DatePeriod;

/**
 * The Interface ScheduleExecutionLogGetMemento.
 */
public interface ScheduleExecutionLogGetMemento {

	/**
	 * Gets the company id.
	 *
	 * @return the company id
	 */
	public CompanyId getCompanyId();

	/**
	 * Gets the completion status.
	 *
	 * @return the completion status
	 */
	public CompletionStatus getCompletionStatus();

	/**
	 * Gets the execution id.
	 *
	 * @return the execution id
	 */
	public String getExecutionId();

	/**
	 * Gets the execution date time.
	 *
	 * @return the execution date time
	 */
	public ExecutionDateTime getExecutionDateTime();

	/**
	 * Gets the execution employee id.
	 *
	 * @return the execution employee id
	 */
	public String getExecutionEmployeeId();

	/**
	 * Gets the period.
	 *
	 * @return the period
	 */
	public DatePeriod getPeriod();

	/**
	 * Gets execution atr
	 * 
	 * @return the execution Atr
	 */
	public ExecutionAtr getExeAtr();

}
