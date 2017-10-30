package nts.uk.ctx.at.schedule.app.find.executionlog.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.schedule.dom.executionlog.ExecutionStatus;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleCreatorSetMemento;

@Getter
@Setter
public class ScheduleCreatorDto implements ScheduleCreatorSetMemento {

	/** The execution id. */
	public String executionId;

	/** The execution status. */
	public String executionStatus;

	/** The employee id. */
	public String employeeId;

	/** The employee code. */
	public String employeeCode;

	/** The employee name. */
	public String employeeName;

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.executionlog.ScheduleCreatorSetMemento#setExecutionId(java.lang.String)
	 */
	@Override
	public void setExecutionId(String executionId) {
		this.executionId = executionId;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.executionlog.ScheduleCreatorSetMemento#setExecutionStatus(nts.uk.ctx.at.schedule.dom.executionlog.ExecutionStatus)
	 */
	@Override
	public void setExecutionStatus(ExecutionStatus executionStatus) {
		this.executionStatus = executionStatus.description;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.executionlog.ScheduleCreatorSetMemento#setEmployeeId(java.lang.String)
	 */
	@Override
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

}
