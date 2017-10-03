package nts.uk.ctx.at.schedule.dom.executionlog;

import nts.arc.time.GeneralDate;

/**
 * The Interface ScheduleErrorLogSetMemento.
 */
public interface ScheduleErrorLogSetMemento {
	
	/**
	 * Sets the error content.
	 *
	 * @param errorContent the new error content
	 */
	public void setErrorContent(String errorContent);

	/**
	 * Sets the execution id.
	 *
	 * @param executionId the new execution id
	 */
	public void setExecutionId(String executionId);

	/**
	 * Sets the date.
	 *
	 * @param date the new date
	 */
	public void setDate(GeneralDate date);

	/**
	 * Sets the employee id.
	 *
	 * @param employeeId the new employee id
	 */
	public void setEmployeeId(String employeeId);
}
