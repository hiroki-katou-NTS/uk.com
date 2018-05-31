package nts.uk.ctx.at.schedule.dom.executionlog;

import nts.arc.time.GeneralDate;

/**
 * The Interface ScheduleErrorLogGetMemento.
 */
public interface ScheduleErrorLogGetMemento {

	/**
	 * Gets the error content.
	 *
	 * @return the error content
	 */
	public String getErrorContent();

	/**
	 * Gets the execution id.
	 *
	 * @return the execution id
	 */
	public String getExecutionId();

	/**
	 * Gets the date.
	 *
	 * @return the date
	 */
	public GeneralDate getDate();

	/**
	 * Gets the employee id.
	 *
	 * @return the employee id
	 */
	public String getEmployeeId();

}
