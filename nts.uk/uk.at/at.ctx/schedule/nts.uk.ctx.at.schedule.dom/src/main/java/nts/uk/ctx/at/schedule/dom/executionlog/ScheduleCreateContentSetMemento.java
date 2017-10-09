package nts.uk.ctx.at.schedule.dom.executionlog;

import nts.arc.time.GeneralDate;

/**
 * The Interface ExecutionContentSetMemento.
 */
public interface ScheduleCreateContentSetMemento {
	
	/**
	 * Sets the execution id.
	 *
	 * @param executionId the new execution id
	 */
	public void setExecutionId(String executionId);
	
	/**
	 * Sets the copy start date.
	 *
	 * @param copyStartDate the new copy start date
	 */
	public void setCopyStartDate(GeneralDate copyStartDate);

	/**
	 * Sets the creates the method atr.
	 *
	 * @param createMethodAtr the new creates the method atr
	 */
	public void setCreateMethodAtr(CreateMethodAtr createMethodAtr);

	/**
	 * Sets the confirm.
	 *
	 * @param confirm the new confirm
	 */
	public void setConfirm(Boolean confirm);

	/**
	 * Sets the implement atr.
	 *
	 * @param implementAtr the new implement atr
	 */
	public void setImplementAtr(ImplementAtr implementAtr);

	/**
	 * Sets the process execution atr.
	 *
	 * @param processExecutionAtr the new process execution atr
	 */
	public void setProcessExecutionAtr(ProcessExecutionAtr processExecutionAtr);

	/**
	 * Sets the re create atr.
	 *
	 * @param reCreateAtr the new re create atr
	 */
	public void setReCreateAtr(ReCreateAtr reCreateAtr);

	/**
	 * Sets the reset master info.
	 *
	 * @param resetMasterInfo the new reset master info
	 */
	public void setResetMasterInfo(Boolean resetMasterInfo);

	/**
	 * Sets the reset absent holiday busines.
	 *
	 * @param resetAbsentHolidayBusines the new reset absent holiday busines
	 */
	public void setResetAbsentHolidayBusines(Boolean resetAbsentHolidayBusines);

	/**
	 * Sets the reset working hours.
	 *
	 * @param resetWorkingHours the new reset working hours
	 */
	public void setResetWorkingHours(Boolean resetWorkingHours);

	/**
	 * Sets the reset time assignment.
	 *
	 * @param resetTimeAssignment the new reset time assignment
	 */
	public void setResetTimeAssignment(Boolean resetTimeAssignment);

	/**
	 * Sets the reset direct line bounce.
	 *
	 * @param resetDirectLineBounce the new reset direct line bounce
	 */
	public void setResetDirectLineBounce(Boolean resetDirectLineBounce);

	/**
	 * Sets the reset time child care.
	 *
	 * @param resetTimeChildCare the new reset time child care
	 */
	public void setResetTimeChildCare(Boolean resetTimeChildCare);
}
