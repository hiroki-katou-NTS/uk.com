/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.executionlog;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * The Class ScheduleExecutionLog.
 */
// スケジュール作成実行ログ
@Getter
@NoArgsConstructor
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
	private ExecutionDateTime executionDateTime;

	/** The execution employee id. */
	// 実行社員ID
	private String executionEmployeeId;

	/** The period. */
	// 対象期間
	private DatePeriod period;

	// 実行区分
	private ExecutionAtr exeAtr;

	public ScheduleExecutionLog(CompanyId companyId, CompletionStatus completionStatus, String executionId,
			ExecutionDateTime executionDateTime, String executionEmployeeId, DatePeriod period, ExecutionAtr exeAtr) {
		super();
		this.companyId = companyId;
		this.completionStatus = completionStatus;
		this.executionId = executionId;
		this.executionDateTime = executionDateTime;
		this.executionEmployeeId = executionEmployeeId;
		this.period = period;
		this.exeAtr = exeAtr;
	}

	/**
	 * To domain.
	 *
	 * @param memento
	 *            the memento
	 * @return the schedule execution log
	 */
	public ScheduleExecutionLog(ScheduleExecutionLogGetMemento memento) {
		this.companyId = memento.getCompanyId();
		this.completionStatus = memento.getCompletionStatus();
		this.executionId = memento.getExecutionId();
		this.executionDateTime = memento.getExecutionDateTime();
		this.executionEmployeeId = memento.getExecutionEmployeeId();
		this.period = memento.getPeriod();
		this.exeAtr = memento.getExeAtr();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(ScheduleExecutionLogSetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setCompletionStatus(this.completionStatus);
		memento.setExecutionId(this.executionId);
		memento.setExecutionDateTime(this.executionDateTime);
		memento.setExecutionEmployeeId(this.executionEmployeeId);
		memento.setPeriod(this.period);
		memento.setExeAtr(this.exeAtr);
	}

	/**
	 * Sets the execution time to now.
	 */
	public void setExecutionTimeToNow() {
		this.executionDateTime = new ExecutionDateTime(GeneralDateTime.now(), GeneralDateTime.now());
	}

	/**
	 * Sets the execution classification is manual.
	 */
	public void setExeAtrIsManual() {
		this.exeAtr = ExecutionAtr.MANUAL;
	}

	/**
	 * Update execution time end to now.
	 */
	public void updateExecutionTimeEndToNow() {
		GeneralDateTime startDateTime = this.executionDateTime.getExecutionStartDate();
		this.executionDateTime = new ExecutionDateTime(startDateTime, GeneralDateTime.now());
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
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
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
		ScheduleExecutionLog other = (ScheduleExecutionLog) obj;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		if (executionId == null) {
			if (other.executionId != null)
				return false;
		} else if (!executionId.equals(other.executionId))
			return false;
		return true;
	}

	public void setPeriod(DatePeriod period) {
		this.period = period;
	}
	
	public void setStartPeriod(GeneralDate date) {
		this.period = new DatePeriod( date,this.period.end());
	}

	public void setExeAtr(ExecutionAtr exeAtr) {
		this.exeAtr = exeAtr;
	}

	public static ScheduleExecutionLog creator(String companyId, String executionId, String executionEmployeeId,
			DatePeriod period, ExecutionAtr exeAtr) {
		return new ScheduleExecutionLog(new CompanyId(companyId), CompletionStatus.INCOMPLETE, executionId,
				new ExecutionDateTime(GeneralDateTime.now(), GeneralDateTime.now()), executionEmployeeId, period,
				exeAtr);
	}

	public void setExecutionId(String executionId) {
		this.executionId = executionId;
	}

	public void setCompanyId(CompanyId companyId) {
		this.companyId = companyId;
	}

}
