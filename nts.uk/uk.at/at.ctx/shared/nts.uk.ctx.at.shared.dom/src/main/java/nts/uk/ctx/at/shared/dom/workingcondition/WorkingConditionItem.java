/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.workingcondition;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * The Class WorkingConditionItem.
 */
@Getter
// 労働条件項目
public class WorkingConditionItem extends AggregateRoot {

	/** The history id. */
	// 履歴ID
	private String historyId;

	/** The schedule management atr. */
	// 予定管理区分
	private NotUseAtr scheduleManagementAtr;

	/** The work day of week. */
	// 曜日別勤務
	private PersonalDayOfWeek workDayOfWeek;

	/** The work category. */
	// 区分別勤務
	private PersonalWorkCategory workCategory;

	/** The auto stamp set atr. */
	// 自動打刻セット区分
	private NotUseAtr autoStampSetAtr;

	/** The auto interval set atr. */
	// 就業時間帯の自動設定区分
	private NotUseAtr autoIntervalSetAtr;

	/** The employee id. */
	// 社員ID
	private String employeeId;

	/** The vacation added time atr. */
	// 休暇加算時間利用区分
	private NotUseAtr vacationAddedTimeAtr;

	/** The contract time. */
	// 契約時間
	private LaborContractTime contractTime;

	/** The labor system. */
	// 労働制
	private WorkingSystem laborSystem;

	/** The holiday add time set. */
	// 休暇加算時間設定
	private BreakdownTimeDay holidayAddTimeSet;

	/** The schedule method. */
	// 予定作成方法
	private ScheduleMethod scheduleMethod;

	/**
	 * Instantiates a new working condition item.
	 *
	 * @param memento
	 *            the memento
	 */
	public WorkingConditionItem(WorkingConditionItemGetMemento memento) {
		this.scheduleManagementAtr = memento.getScheduleManagementAtr();
		this.vacationAddedTimeAtr = memento.getVacationAddedTimeAtr();
		this.laborSystem = memento.getLaborSystem();
		this.workCategory = memento.getWorkCategory();
		this.contractTime = memento.getContractTime();
		this.autoIntervalSetAtr = memento.getAutoIntervalSetAtr();
		this.historyId = memento.getHistoryId();
		this.workDayOfWeek = memento.getWorkDayOfWeek();
		this.employeeId = memento.getEmployeeId();
		this.autoStampSetAtr = memento.getAutoStampSetAtr();
		this.scheduleMethod = memento.getScheduleMethod();
		this.holidayAddTimeSet = memento.getHolidayAddTimeSet();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(WorkingConditionItemSetMemento memento) {
		memento.setScheduleManagementAtr(this.scheduleManagementAtr);
		memento.setVacationAddedTimeAtr(this.vacationAddedTimeAtr);
		memento.setLaborSystem(this.laborSystem);
		memento.setWorkCategory(this.workCategory);
		memento.setContractTime(this.contractTime);
		memento.setAutoIntervalSetAtr(this.autoIntervalSetAtr);
		memento.setHistoryId(this.historyId);
		memento.setWorkDayOfWeek(this.workDayOfWeek);
		memento.setEmployeeId(this.employeeId);
		memento.setAutoStampSetAtr(this.autoStampSetAtr);
		memento.setScheduleMethod(this.scheduleMethod);
		memento.setHolidayAddTimeSet(this.holidayAddTimeSet);
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
		result = prime * result + ((historyId == null) ? 0 : historyId.hashCode());
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
		WorkingConditionItem other = (WorkingConditionItem) obj;
		if (employeeId == null) {
			if (other.employeeId != null)
				return false;
		} else if (!employeeId.equals(other.employeeId))
			return false;

		if (historyId == null) {
			if (other.historyId != null)
				return false;
		} else if (!historyId.equals(other.historyId))
			return false;

		return true;
	}
}
