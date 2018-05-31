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
public class ScheduleCreator extends AggregateRoot {

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
	 * @param memento
	 *            the memento
	 */
	public ScheduleCreator(ScheduleCreatorGetMemento memento) {
		this.executionId = memento.getExecutionId();
		this.executionStatus = memento.getExecutionStatus();
		this.employeeId = memento.getEmployeeId();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(ScheduleCreatorSetMemento memento) {
		memento.setExecutionId(this.executionId);
		memento.setEmployeeId(this.employeeId);
		memento.setExecutionStatus(this.executionStatus);
	}

	/**
	 * Update to created.
	 */
	public void updateToCreated() {
		this.executionStatus = ExecutionStatus.CREATED;
	}

	/**
	 * Checks if is created.
	 *
	 * @return true, if is created
	 */
	public boolean isCreated() {
		return this.executionStatus.equals(ExecutionStatus.CREATED);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((employeeId == null) ? 0 : employeeId.hashCode());
		result = prime * result + ((executionId == null) ? 0 : executionId.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ScheduleCreator other = (ScheduleCreator) obj;
		if (employeeId == null) {
			if (other.employeeId != null)
				return false;
		} else if (!employeeId.equals(other.employeeId))
			return false;
		if (executionId == null) {
			if (other.executionId != null)
				return false;
		} else if (!executionId.equals(other.executionId))
			return false;
		return true;
	}

	public ScheduleCreator(String executionId, ExecutionStatus executionStatus, String employeeId) {
		super();
		this.executionId = executionId;
		this.executionStatus = executionStatus;
		this.employeeId = employeeId;
	}

}
