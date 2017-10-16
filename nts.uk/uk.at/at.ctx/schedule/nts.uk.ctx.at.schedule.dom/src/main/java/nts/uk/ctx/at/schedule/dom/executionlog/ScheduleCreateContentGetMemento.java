package nts.uk.ctx.at.schedule.dom.executionlog;

import nts.arc.time.GeneralDate;

/**
 * The Interface ExecutionContentGetMemento.
 */
public interface ScheduleCreateContentGetMemento {
	
	/**
	 * Gets the execution id.
	 *
	 * @return the execution id
	 */
	public String getExecutionId();
	
	/**
	 * Gets the copy start date.
	 *
	 * @return the copy start date
	 */
	public GeneralDate getCopyStartDate();

	/**
	 * Gets the creates the method atr.
	 *
	 * @return the creates the method atr
	 */
	public CreateMethodAtr getCreateMethodAtr();

	/**
	 * Gets the confirm.
	 *
	 * @return the confirm
	 */
	public Boolean getConfirm();

	/**
	 * Gets the implement atr.
	 *
	 * @return the implement atr
	 */
	public ImplementAtr getImplementAtr();

	/**
	 * Gets the process execution atr.
	 *
	 * @return the process execution atr
	 */
	public ProcessExecutionAtr getProcessExecutionAtr();

	/**
	 * Gets the re create atr.
	 *
	 * @return the re create atr
	 */
	public ReCreateAtr getReCreateAtr();

	/**
	 * Gets the reset master info.
	 *
	 * @return the reset master info
	 */
	public Boolean getResetMasterInfo();

	/**
	 * Gets the reset absent holiday busines.
	 *
	 * @return the reset absent holiday busines
	 */
	public Boolean getResetAbsentHolidayBusines();

	/**
	 * Gets the reset working hours.
	 *
	 * @return the reset working hours
	 */
	public Boolean getResetWorkingHours();

	/**
	 * Gets the reset time assignment.
	 *
	 * @return the reset time assignment
	 */
	public Boolean getResetTimeAssignment();

	/**
	 * Gets the reset direct line bounce.
	 *
	 * @return the reset direct line bounce
	 */
	public Boolean getResetDirectLineBounce();

	/**
	 * Gets the reset time child care.
	 *
	 * @return the reset time child care
	 */
	public Boolean getResetTimeChildCare();
}
