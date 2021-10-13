/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.personallaborcondition;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.common.time.BreakDownTimeDay;

/**
 * The Class PersonalLaborCondition.
 */
// 個人労働条件
@Getter
public class PersonalLaborCondition extends AggregateRoot{
	
	/** The schedule management atr. */
	// 予定管理区分
	private UseAtr scheduleManagementAtr;
	
	/** The holiday add time set. */
	// 休暇加算時間設定
	private BreakDownTimeDay holidayAddTimeSet;
	
	/** The work category. */
	// 区分別勤務
	private PersonalWorkCategory workCategory;
	
	/** The work day of week. */
	// 曜日別勤務
	private PersonalDayOfWeek workDayOfWeek;
	
	/** The period. */
	// 期間
	private DatePeriod period;
	
	/** The employee id. */
	// 社員ID
	private String employeeId;
	
	/** The auto stamp set atr. */
	// 自動打刻セット区分
	private UseAtr autoStampSetAtr;

	/**
	 * Instantiates a new personal labor condition.
	 *
	 * @param memento the memento
	 */
	public PersonalLaborCondition(PersonalLaborConditionGetMemento memento) {
		this.scheduleManagementAtr = memento.getScheduleManagementAtr();
		this.holidayAddTimeSet = memento.getHolidayAddTimeSet();
		this.workCategory = memento.getWorkCategory();
		this.workDayOfWeek = memento.getWorkDayOfWeek();
		this.period = memento.getPeriod();
		this.employeeId = memento.getEmployeeId();
		this.autoStampSetAtr = memento.getAutoStampSetAtr();
	}
	
	/**
	 * Checks if is use schedule management.
	 *
	 * @return true, if is use schedule management
	 */
	public boolean isUseScheduleManagement(){
		return this.scheduleManagementAtr.equals(UseAtr.USE);
	}
	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(PersonalLaborConditionSetMemento memento){
		memento.setScheduleManagementAtr(this.scheduleManagementAtr);
		memento.setHolidayAddTimeSet(this.holidayAddTimeSet);
		memento.setWorkCategory(this.workCategory);
		memento.setWorkDayOfWeek(this.workDayOfWeek);
		memento.setPeriod(this.period);
		memento.setEmployeeId(this.employeeId);
		memento.setAutoStampSetAtr(this.autoStampSetAtr);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((employeeId == null) ? 0 : employeeId.hashCode());
		result = prime * result + ((period == null) ? 0 : period.hashCode());
		return result;
	}

	/* (non-Javadoc)
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
		PersonalLaborCondition other = (PersonalLaborCondition) obj;
		if (employeeId == null) {
			if (other.employeeId != null)
				return false;
		} else if (!employeeId.equals(other.employeeId))
			return false;
		if (period == null) {
			if (other.period != null)
				return false;
		} else if (!period.equals(other.period))
			return false;
		return true;
	}
	
}
