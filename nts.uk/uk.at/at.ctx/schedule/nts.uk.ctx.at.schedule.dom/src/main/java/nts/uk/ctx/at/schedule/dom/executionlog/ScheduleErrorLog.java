/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.executionlog;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;

/**
 * The Class ScheduleErrorLog.
 */
// スケジュール作成エラーログ
@Getter
public class ScheduleErrorLog extends AggregateRoot {

	/** The error content. */
	// エラー内容
	private String errorContent;

	/** The execution id. */
	// 実行ID
	private String executionId;

	/** The date. */
	// 年月日
	private GeneralDate date;

	/** The employee id. */
	// 社員ID
	private String employeeId;

	/**
	 * To domain.
	 *
	 * @param memento
	 *            the memento
	 * @return the schedule error log
	 */
	public ScheduleErrorLog toDomain(ScheduleErrorLogGetMemento memento) {
		this.errorContent = memento.getErrorContent();
		this.executionId = memento.getExecutionId();
		this.date = memento.getDate();
		this.employeeId = memento.getEmployeeId();
		return this;
	}

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(ScheduleErrorLogSetMemento memento) {
		memento.setErrorContent(this.errorContent);
		memento.setExecutionId(this.executionId);
		memento.setDate(this.date);
		memento.setEmployeeId(this.employeeId);
	}
	
	//TODO remove after test export with mock data success
	public ScheduleErrorLog(String errorContent, String executionId, GeneralDate date, String employeeId) {
		this.errorContent = errorContent;
		this.executionId = executionId;
		this.date = date;
		this.employeeId = employeeId;
	}
}
