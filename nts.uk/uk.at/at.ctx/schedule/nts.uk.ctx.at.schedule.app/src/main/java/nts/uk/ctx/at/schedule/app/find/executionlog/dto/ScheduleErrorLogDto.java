package nts.uk.ctx.at.schedule.app.find.executionlog.dto;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleErrorLogSetMemento;

/**
 * The Class ScheduleErrorLogDto.
 */
@Getter
@Setter
public class ScheduleErrorLogDto implements ScheduleErrorLogSetMemento {

	/** The error content. */
	public String errorContent;

	/** The execution id. */
	public String executionId;

	/** The date. */
	public GeneralDate date;

	/** The employee id. */
	public String employeeId;
	
	/** The employee code. */
	public String employeeCode;

	/** The employee name. */
	public String employeeName;

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.executionlog.ScheduleErrorLogSetMemento#setErrorContent(java.lang.String)
	 */
	@Override
	public void setErrorContent(String errorContent) {
		this.errorContent = errorContent;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.executionlog.ScheduleErrorLogSetMemento#setExecutionId(java.lang.String)
	 */
	@Override
	public void setExecutionId(String executionId) {
		this.executionId = executionId;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.executionlog.ScheduleErrorLogSetMemento#setDate(nts.arc.time.GeneralDate)
	 */
	@Override
	public void setDate(GeneralDate date) {
		this.date = date;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.executionlog.ScheduleErrorLogSetMemento#setEmployeeId(java.lang.String)
	 */
	@Override
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
}
