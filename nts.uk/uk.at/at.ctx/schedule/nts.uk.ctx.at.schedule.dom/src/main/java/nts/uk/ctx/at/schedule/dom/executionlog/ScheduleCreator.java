/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.executionlog;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * The Class ScheduleCreator.
 */
// スケジュール作成対象者
@Getter
public class ScheduleCreator extends AggregateRoot{
	
	/** The execution id. */
	// 実行ID
	private String executionId;
	
	/** The execution status. */
	// 実行状況
	private ExecutionStatus executionStatus;
	
	/** The employee id. */
	// 社員ID
	private String employeeId;
	
	/**
	 * Instantiates a new schedule creator.
	 *
	 * @param memento the memento
	 */
	public ScheduleCreator(ScheduleCreatorGetMemento memento){
		this.executionId = memento.getExecutionId();
		this.executionStatus = memento.getExecutionStatus();
		this.employeeId = memento.getEmployeeId();
	}
	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(ScheduleCreatorSetMemento memento){
		memento.setExecutionId(this.executionId);
		memento.setExecutionStatus(this.executionStatus);
		memento.setEmployeeId(this.employeeId);
	}

}
