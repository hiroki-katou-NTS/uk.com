/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.executionlog;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.workrule.closure.Period;

/**
 * The Class ScheduleExecutionLog.
 */
// スケジュール作成実行ログ
@Getter
public class ScheduleExecutionLog extends AggregateRoot {

	/** The company id. */
	// 会社ID
	private CompanyId companyId;

	/** The completion status. */
	// 完了状態
	@Setter
	private CompletionStatus completionStatus;

	/** The execution id. */
	// 実行ID
	private String executionId;

	/** The execution date time. */
	// 実行日時
	@Setter
	private ExecutionDateTime executionDateTime;

	/** The execution employee id. */
	// 実行社員ID
	private String executionEmployeeId;

	/** The period. */
	// 対象期間
	private Period period;

	/**
	 * To domain.
	 *
	 * @param memento the memento
	 * @return the schedule execution log
	 */
	public ScheduleExecutionLog (ScheduleExecutionLogGetMemento memento) {
		this.companyId = memento.getCompanyId();
		this.completionStatus = memento.getCompletionStatus();
		this.executionId = memento.getExecutionId();
		this.executionDateTime = memento.getExecutionDateTime();
		this.executionEmployeeId = memento.getExecutionEmployeeId();
		this.period = memento.getPeriod();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(ScheduleExecutionLogSetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setCompletionStatus(this.completionStatus);
		memento.setExecutionId(this.executionId);
		memento.setExecutionDateTime(this.executionDateTime);
		memento.setExecutionEmployeeId(this.executionEmployeeId);
		memento.setPeriod(this.period);
	}
	
}
