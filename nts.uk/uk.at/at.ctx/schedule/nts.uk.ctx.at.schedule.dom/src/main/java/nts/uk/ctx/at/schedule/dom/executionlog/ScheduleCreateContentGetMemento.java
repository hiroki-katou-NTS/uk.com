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
	 * Gets the reset working hours.
	 *
	 * @return the reset working hours
	 */
	public Boolean getResetWorkingHours();

	/**
	 * Gets the reset start-end time.
	 *
	 * @return the reset start-end time
	 */
	public Boolean getResetStartEndTime();

	/**
	 * Gets the reset time assignment.
	 *
	 * @return the reset time assignment
	 */
	public Boolean getResetTimeAssignment();

	// RebuildTargetAtr
	public RebuildTargetAtr getRebuildTargetAtr();

	// RebuildTargetDetailsAtr
	public Boolean getRecreateConverter();

	public Boolean getRecreateEmployeeOffWork();

	public Boolean getRecreateDirectBouncer();

	public Boolean getRecreateShortTermEmployee();

	public Boolean getRecreateWorkTypeChange();

	public Boolean getProtectHandCorrection();
}
