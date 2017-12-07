package nts.uk.ctx.at.shared.dom.workingcondition;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;


/**
 * The Class WorkingConditionItem.
 */
@Getter
public class WorkingConditionItem extends AggregateRoot {
	
	/** The schedule management atr. */
	private NotUseAtr scheduleManagementAtr;
	
	/** The vacation added time atr. */
	private NotUseAtr vacationAddedTimeAtr;
	
	/** The labor system. */
	private WorkingSystem laborSystem;
	
	/** The work category. */
	private PersonalWorkCategory workCategory;
	
	/** The contract time. */
	private LaborContractTime contractTime;
	
	/** The auto interval set atr. */
	private NotUseAtr autoIntervalSetAtr;
	
	/** The history id. */
	private String historyId;
	
	/** The work day of week. */
	private PersonalDayOfWeek workDayOfWeek;
	
	/** The employee id. */
	private String employeeId;
	
	/** The auto stamp set atr. */
	private NotUseAtr autoStampSetAtr;
	
	/** The schedule method. */
	private ScheduleMethod scheduleMethod;
	
	/** The holiday add time set. */
	private BreakdownTimeDay holidayAddTimeSet;
	
	/**
	 * Instantiates a new working condition.
	 *
	 * @param memento the memento
	 */
	public WorkingConditionItem(WorkingConditionItemGetMemento memento){
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
