package nts.uk.ctx.at.schedule.dom.executionlog;

/**
 * The Interface ScheduleCreatorSetMemento.
 */
public interface ScheduleCreatorSetMemento {
	
	/**
	 * Sets the execution id.
	 *
	 * @param executionId the new execution id
	 */
	public void setExecutionId(String executionId);

    /**
     * Sets the execution status.
     *
     * @param executionStatus the new execution status
     */
    public void setExecutionStatus(ExecutionStatus executionStatus);

    /**
     * Sets the employee id.
     *
     * @param employeeId the new employee id
     */
    public void setEmployeeId(String employeeId);
}
