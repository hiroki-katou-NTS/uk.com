package nts.uk.ctx.at.schedule.dom.executionlog;

/**
 * The Interface ScheduleCreatorGetMemento.
 */
public interface ScheduleCreatorGetMemento {
	
	/**
	 * Gets the execution id.
	 *
	 * @return the execution id
	 */
	public String getExecutionId();

    /**
     * Gets the execution status.
     *
     * @return the execution status
     */
    public ExecutionStatus getExecutionStatus();

    /**
     * Gets the employee id.
     *
     * @return the employee id
     */
    public String getEmployeeId();
}
